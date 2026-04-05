package gkaram.libraflow.domain.usecases;

import gkaram.libraflow.domain.dto.ErrorOutput;
import gkaram.libraflow.domain.dto.ReturnBookInput;
import gkaram.libraflow.domain.dto.ReturnBookOutput;
import gkaram.libraflow.domain.entities.repository.BookRepository;
import gkaram.libraflow.domain.entities.repository.LoanRepository;
import gkaram.libraflow.domain.entities.repository.ReservationRepository;
import gkaram.libraflow.domain.events.BookReturnedEvent;
import gkaram.libraflow.domain.shared.EventPublisher;
import gkaram.libraflow.domain.shared.Result;

public class ReturnBook implements BaseUseCase<ReturnBookInput, ReturnBookOutput> {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final EventPublisher eventPublisher;

    public ReturnBook(LoanRepository loanRepository, BookRepository bookRepository, ReservationRepository reservationRepository, EventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ReturnBookOutput execute(ReturnBookInput input) {
        // 1. Busca o empréstimo
        var loanOpt = loanRepository.findById(input.loanId());
        if (loanOpt.isEmpty())
            return new ReturnBookOutput(null, new ErrorOutput("Empréstimo não encontrado", Result.ErrorStatus.NOT_FOUND.name()));

        var loan = loanOpt.get();

        // 2. Marca como devolvido
        Result<Void> returnResult = loan.returnLoan();
        if (returnResult.isFailure())
            return new ReturnBookOutput(null, new ErrorOutput(returnResult.errorMessage(), returnResult.errorStatus().name()));

        loanRepository.save(loan);

        // 3. Incrementa cópias disponíveis do livro
        var bookOpt = bookRepository.findById(loan.getBookId());
        if (bookOpt.isEmpty())
            return new ReturnBookOutput(null, new ErrorOutput("Livro não encontrado", Result.ErrorStatus.NOT_FOUND.name()));

        var book = bookOpt.get();
        book.returnCopy();
        bookRepository.update(book);

        // 4. Se há reservas na fila, publica evento para o consumer notificar o próximo
        if (reservationRepository.hasQueuedReservations(loan.getBookId())) {
            eventPublisher.publish(new BookReturnedEvent(loan.getBookId()));
        }

        return new ReturnBookOutput(loan.getId(), null);
    }
}
