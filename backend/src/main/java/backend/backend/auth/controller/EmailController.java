package backend.backend.auth.controller;

import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.service.EmailService;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/verification")
public class EmailController {
    private final EmailService emailService;

    private final UserRepository userRepository;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmailSendResponse emailVerificationRequest(@RequestParam String email) throws Exception {
        if (userRepository.findUserByEmail(email) == null) {
            emailService.sendSimpleMessage(email);
            return new EmailSendResponse("이메일을 확인해주세요.");
        }
        throw new IllegalStateException("이메일이 존재합니다.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public VerificationResponse emailVerification(@RequestBody VerificationRequest verificationRequest) throws Exception {
        if (emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode())) {
            return new VerificationResponse("이메일 인증이 완료되었습니다.");
        }
        throw new IllegalStateException("코드가 옳지 못합니다.");
    }
}
