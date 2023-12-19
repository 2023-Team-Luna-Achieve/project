package backend.backend.auth.oauth2.user;

import backend.backend.auth.oauth2.OAuth2AuthenticationProcessingException;
import backend.backend.user.entity.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("OAuth2UserInfoFactory의 인스턴스는 생성할 수 없습니다.");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        throw new OAuth2AuthenticationProcessingException(
                registrationId + " 로그인은 지원하지 않습니다.");
    }
}