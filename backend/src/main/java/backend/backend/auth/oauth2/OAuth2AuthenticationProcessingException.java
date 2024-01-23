package backend.backend.auth.oauth2;


import backend.backend.common.exception.AuthenticationException;
import backend.backend.common.exception.ErrorCode;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg) {
        super(ErrorCode.valueOf(msg));
    }
}
