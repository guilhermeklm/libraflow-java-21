package gkaram.libraflow.domain.entities;


import java.time.LocalDate;

public class BookCopy {
    private String id;
    private Book book;
    private CopyStatus status;
    private LocalDate acquiredAt;
}
