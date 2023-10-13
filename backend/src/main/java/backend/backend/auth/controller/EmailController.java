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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmailSendResponse emailVerificationRequest(@RequestParam String email) throws Exception {
        return emailService.sendEmailIfNotExists(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public VerificationResponse emailVerification(@RequestBody VerificationRequest verificationRequest) throws Exception {//        if (emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode())) {
        return emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode());
    }
}
