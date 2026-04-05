package gkaram.libraflow.domain.dto;

public record RegisterOutput(
        String token,
        ErrorOutput error
) {}
