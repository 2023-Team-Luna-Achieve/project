package backend.backend.auth.jwt;

import backend.backend.user.entity.User;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserAdapter extends CustomUserDetails {

    private User user;
//    private Map<String, Object> attributes;

    public UserAdapter(User user){
        super(user);
        this.user = user;
    }

//    public UserAdapter(User user, Map<String, Object> attributes){
//        super(user, attributes);
//        this.user = user;
//        this.attributes = attributes;
//    }
}