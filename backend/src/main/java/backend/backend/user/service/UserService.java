package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.user.dto.SignUpRequest;
import backend.backend.user.dto.SignUpResponse;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    @Transactional
    public SignUpResponse create(User user) {
        userRepository.save(user);
        return SignUpResponse.of(user);
    }

    @Transactional
    public SignUpResponse createUserIfEmailNotExists(SignUpRequest signUpRequest) {
        if (findUserByEmail(signUpRequest.getEmail()) == null) {
            if (redisUtil.getData(signUpRequest.getEmail()).equals("verified")) {
                bcryptingPassword(signUpRequest);
                User user = signUpRequest.toEntity();
                return create(user);
            } else {
                throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
            }
        } else {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void bcryptingPassword(SignUpRequest signUpRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encode);
    }


}
