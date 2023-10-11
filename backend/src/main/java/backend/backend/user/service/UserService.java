package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.user.dto.*;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final HttpSession session;

    @Transactional
    public SignUpResponse create(User user) {
        userRepository.save(user);
        return new SignUpResponse(201, true, "회원가입이 완료 되었습니다.", UserDto.of(user));
    }

    @Transactional
    public SignUpResponse createUserIfEmailNotExists(SignUpRequest signUpRequest) {
        if (findUserByEmail(signUpRequest.getEmail()) == null) {
            if (redisUtil.getData(signUpRequest.getEmail()).equals("verified")) {
                bcryptingPassword(signUpRequest);
                User user = signUpRequest.toEntity();
                return create(user);
            }
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }
        throw new IllegalStateException("이미 사용중인 이메일입니다.");
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void bcryptingPassword(SignUpRequest signUpRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encode);
    }

    public SignInResponse processSignIn (SignInRequest signInRequest) {
        User user = findUserByEmail(signInRequest.getEmail());
        if (BCrypt.checkpw(signInRequest.getPassword(), user.getPassword()) ) {
            session.setAttribute("userId", user.getId());
            return new SignInResponse(200, true,"로그인이 완료 되었습니다", UserDto.of(user));
        }
        throw new IllegalStateException("아이디나 비빌번호를 다시 확인해주세요");
    }
}
