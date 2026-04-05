package gkaram.libraflow.domain.shared;

public interface TokenIssuer {
    String issue(String userId, String role);
}
