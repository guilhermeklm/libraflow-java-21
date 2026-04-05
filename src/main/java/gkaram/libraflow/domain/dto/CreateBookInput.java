package gkaram.libraflow.domain.dto;

import java.util.List;

public record CreateBookInput(
    String isbn,
    String title,
    List<String> authorIds,
    String publisher,
    Integer year,
    String genre,
    Integer totalCopies,
    Integer availableCopies
) {}
