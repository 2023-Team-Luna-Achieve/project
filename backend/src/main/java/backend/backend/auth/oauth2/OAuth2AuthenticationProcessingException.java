package backend.backend.auth.oauth2;


import backend.backend.exception.AuthenticationException;
import backend.backend.exception.ErrorCode;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg) {
        super(ErrorCode.valueOf(msg));
    }
}
