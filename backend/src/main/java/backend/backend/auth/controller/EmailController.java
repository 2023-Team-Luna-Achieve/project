package backend.backend.auth.controller;

import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/verification")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/request")
    public ResponseEntity<EmailSendResponse> emailVerificationRequest(@RequestBody EmailRequest emailRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.sendEmailIfNotExists(emailRequest.getEmail()));
    }

    @PostMapping("/confirm")
    public ResponseEntity<VerificationResponse> emailVerification(@RequestBody VerificationRequest verificationRequest) throws Exception {//        if (emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode())) {
        return ResponseEntity.ok().body(emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode()));
    }
}
