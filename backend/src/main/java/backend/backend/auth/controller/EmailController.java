package backend.backend.auth.controller;

import backend.backend.auth.dto.EmailRequest;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationRequest;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.auth.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Email API", description = "이메일 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email/verification")
public class EmailController {
    private final EmailService emailService;

    @ApiOperation(value = "이메일 인증번호 전송 요청 API", notes = "이메일로 인증번호를 보낸다.")
    @PostMapping("/request")
    public ResponseEntity<EmailSendResponse> emailVerificationRequest(@RequestBody EmailRequest emailRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.sendEmailIfNotExists(emailRequest.getEmail()));
    }

    @ApiOperation(value = "이메일 인증 API", notes = "전송받은 인증번호로 이메일을 인증한다.")
    @PostMapping("/confirm")
    public ResponseEntity<VerificationResponse> emailVerification(@RequestBody VerificationRequest verificationRequest) throws Exception {
        return ResponseEntity.ok().body(emailService.verifyEmail(verificationRequest.getEmail(), verificationRequest.getCode()));
    }
}
