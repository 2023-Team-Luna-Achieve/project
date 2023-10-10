package backend.backend.auth.service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;

    Boolean verifyEmail(String email, String code)throws Exception;
}
