package backend.backend.user.service;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.exception.*;
import backend.backend.user.dto.*;
import backend.backend.user.repository.UserRepository;
import backend.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    private SignUpResponse create(User user) {
        userRepository.save(user);
        return new SignUpResponse(201, true, "회원가입이 완료 되었습니다.", UserDto.of(user));
    }

    @Transactional
    public SignUpResponse createUserIfEmailNotExists(SignUpRequest signUpRequest, BCryptPasswordEncoder encoder) {
        if (userRepository.findUserByEmail(signUpRequest.getEmail()) == null) {
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

    public SignOutResponse processSignOut(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            throw new NotLoginException(ErrorCode.NOT_LOGIN);
        }
        session.invalidate();
        return new SignOutResponse("로그아웃 완료되었습니다.");
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유저 정보 없음"));
    }
}
