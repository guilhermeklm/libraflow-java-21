package gkaram.libraflow.domain.shared;

public interface EmailSender {
    void send(String to, String subject, String body);
}
