package backend.backend.auth.oauth2.user;

import lombok.AllArgsConstructor;

import java.util.Map;

public class NaverOauth2UserInfo extends OAuth2UserInfo {

    public NaverOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}