package gkaram.libraflow.domain.dto;

public record LoginOutput(
        String token,
        ErrorOutput error
) {}
