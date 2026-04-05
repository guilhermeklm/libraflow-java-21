package gkaram.libraflow.application.controller;

import gkaram.libraflow.domain.dto.ReturnBookInput;
import gkaram.libraflow.domain.entities.repository.BookRepository;
import gkaram.libraflow.domain.entities.repository.LoanRepository;
import gkaram.libraflow.domain.entities.repository.ReservationRepository;
import gkaram.libraflow.domain.shared.EventPublisher;
import gkaram.libraflow.domain.usecases.ReturnBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final ReturnBook returnBook;

    public LoanController(
            @Autowired LoanRepository loanRepository,
            @Autowired BookRepository bookRepository,
            @Autowired ReservationRepository reservationRepository,
            @Autowired EventPublisher eventPublisher
    ) {
        this.returnBook = new ReturnBook(loanRepository, bookRepository, reservationRepository, eventPublisher);
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnBook(@PathVariable String loanId) {
        var output = returnBook.execute(new ReturnBookInput(loanId));

        if (output.error() != null) {
            return ResponseEntity.badRequest().body(output.error());
        }

        return ResponseEntity.ok().build();
    }
}
