package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.exception.AuthenticationException;
import backend.backend.exception.ErrorCode;
import backend.backend.exception.InvalidValueException;
import backend.backend.exception.UnVerifiedAccountException;
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
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final HttpSession session;

    private SignUpResponse create(User user) {
        userRepository.save(user);
        return new SignUpResponse(201, true, "회원가입이 완료 되었습니다.", UserDto.of(user));
    }

    @Transactional
    public SignUpResponse createUserIfEmailNotExists(SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        if (findUserByEmail(signUpRequest.getEmail()) == null) {
            if (redisUtil.getData(signUpRequest.getEmail()) == null) {
                throw new UnVerifiedAccountException(ErrorCode.UNAUTHORIZED_EMAIL);
            } else if (redisUtil.getData(signUpRequest.getEmail()).equals("verified")) {
                signUpRequest = signUpRequest.encryptPassword(encoder);
                User user = signUpRequest.toEntity();
                return create(user);
            }
        }
        throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL);
    }
    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public SignInResponse processSignIn (SignInRequest signInRequest) {
        User user = findUserByEmail(signInRequest.getEmail());
        if (user != null) {
            if (BCrypt.checkpw(signInRequest.getPassword(), user.getPassword())) {
                session.setAttribute("userId", user.getId());
                return new SignInResponse(200, true, "로그인이 완료 되었습니다", UserDto.of(user));
            }
            throw new InvalidValueException(ErrorCode.BAD_LOGIN);
        }

        throw new InvalidValueException(ErrorCode.BAD_LOGIN);
    }
}
