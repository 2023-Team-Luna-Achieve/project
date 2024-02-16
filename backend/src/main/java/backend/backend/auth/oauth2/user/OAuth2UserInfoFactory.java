package backend.backend.auth.oauth2.user;

import backend.backend.auth.oauth2.OAuth2AuthenticationProcessingException;
import backend.backend.user.entity.AuthProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {
    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("OAuth2UserInfoFactory의 인스턴스는 생성할 수 없습니다.");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get("response");
            return new NaverOauth2UserInfo(naverAttributes);
        }

        if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
            return new KakaoOauth2UserInfo(attributes);
        }

        throw new OAuth2AuthenticationProcessingException(
                registrationId + " 로그인은 지원하지 않습니다.");
    }
}