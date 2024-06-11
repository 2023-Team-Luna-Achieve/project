package backend.backend.auth.controller;

import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.service.EmailService;
import backend.backend.common.constant.VerifiedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Email API", description = "이메일 인증")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/verification")
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "이메일 인증 번호 요청", description = "이메일 인증 번호 요청")
    @PostMapping("/request")
    public ResponseEntity<EmailSendResponse> emailVerificationRequest(@Valid @RequestBody EmailRequest emailRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.sendEmailIfNotExists(emailRequest.email()));
    }

    @Operation(summary = "이메일 인증 API", description = "전송받은 인증번호로 이메일을 인증한다.")
    @PostMapping("/confirm")
    public ResponseEntity<VerificationResponse> emailVerification(@Valid @RequestBody VerificationRequest verificationRequest) {
        return ResponseEntity.ok().body(emailService.verifyEmail(verificationRequest.email(), verificationRequest.code()));
    }

    @PostMapping("/password-update-request")
    public ResponseEntity<Void> passwordUpdateVerificationRequest(@Valid @RequestBody EmailRequest emailRequest) throws Exception {
        emailService.sendPasswordUpdateAuthCodeEmail(emailRequest.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-update-confirm")
    public ResponseEntity<Void> passwordUpdateVerification(@Valid @RequestBody VerificationRequest verificationRequest) {
        emailService.verifyEmail(verificationRequest.email() + VerifiedType.PASSWORD_RESET, verificationRequest.code());
        return ResponseEntity.ok().build();
    }
}
