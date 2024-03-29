package backend.backend.auth.controller;

import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Email API", description = "이메일 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/verification")
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
    @PostMapping("/request")
    public ResponseEntity<EmailSendResponse> emailVerificationRequest(@RequestBody EmailRequest emailRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.sendEmailIfNotExists(emailRequest.getEmail()));
    }

    @Operation(summary = "이메일 인증 API", description = "전송받은 인증번호로 이메일을 인증한다.")
    @PostMapping("/confirm")
    public ResponseEntity<VerificationResponse> emailVerification(@RequestBody VerificationRequest verificationRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode()));
    }
}
