package backend.backend.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import backend.backend.common.constant.VerifiedType;
import backend.backend.common.exception.*;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;


    private MimeMessage createReservationMessage(String targetEmail, String roomName) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : " + targetEmail);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, targetEmail); //보내는 대상
        message.setSubject("예약 완료");//제목

        String msgg = "";
        msgg += "<img " +
                "src='https://miro.medium.com/v2/resize:fit:1200/1*lL3lJHoc96tbsPw8BKd27g.jpeg' " +
                "style='display: block; width: 900px; height: 300px' " +
                ">";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> Team Achieve 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p> 예약완료 되었습니다<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>예약완료 되었습니다</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "예약한 방 : <strong>";
        msgg += roomName + "</strong><div><br/> ";
        msgg += "</div>";
        msgg += "</div>";
        msgg += "</div>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<br>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("academyschool7@gmail.com", "Achieve"));//보내는 사람
        return message;
    }


    private MimeMessage createVerificationMessage(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        String ePw = createKey();
        System.out.println("보내는 대상 : " + targetEmail);
        System.out.println("인증 번호 : " + ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, targetEmail); //보내는 대상
        message.setSubject("회원가입 인증");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> Team Achieve 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("academyschool7@gmail.com", "Achieve"));//보내는 사람

        redisUtil.setDataExpire(targetEmail, ePw, 10 * 60L);
        return message;
    }

    private MimeMessage createPasswordUpdateVerificationMessage(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        String ePw = createKey();
        System.out.println("보내는 대상 : " + targetEmail);
        System.out.println("인증 번호 : " + ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, targetEmail); //보내는 대상
        message.setSubject("이메일 인증");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> Team Achieve 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("academyschool7@gmail.com", "Achieve"));//보내는 사람

        String redisDataPurposeKey = targetEmail + VerifiedType.PASSWORD_RESET;

        redisUtil.setDataExpire(redisDataPurposeKey, ePw, 10 * 60L);
        return message;
    }

    private static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    //회원 가입용 이메일 전송
    protected void sendVerificationCodeEmail(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(targetEmail)) {
            throw new AlreadyVerifiedException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        }

        if (redisUtil.existData(targetEmail)) {
            if (redisUtil.getData(targetEmail).equals("verified")) {
                throw new AlreadyVerifiedException(ErrorCode.ALREADY_VERIFIED_EMAIL);
            }

            if (!redisUtil.getData(targetEmail).equals("verified")) {
                throw new AlreadyVerifiedException(ErrorCode.ALREADY_AUTH_CODE_SENT);
            }
        }

        MimeMessage message = createVerificationMessage(targetEmail);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            log.error(es.toString());
            throw new BusinessException(ErrorCode.FAILED_EMAIL_SEND);
        }
    }

    // 비밀번호 변경 메일 인증 전송
    @Transactional//(propagation = Propagation.REQUIRES_NEW)
    public void sendPasswordUpdateAuthCodeEmail(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createPasswordUpdateVerificationMessage(targetEmail);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            log.error(es.toString());
            throw new BusinessException(ErrorCode.FAILED_EMAIL_SEND);
        }
    }


    // 에약용 메일 전송 메서드
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendReservationEmail(String targetEmail, String meetingRoomName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createReservationMessage(targetEmail, meetingRoomName);
        try {
            emailSender.send(message);
        } catch (MailException es) {
            log.error(es.toString());
            throw new BusinessException(ErrorCode.FAILED_EMAIL_SEND);
        }
    }


    @Transactional
    public VerificationResponse verifyEmail(String email, String code) {
        String verificationCode = redisUtil.getData(email);
        if (verificationCode == null) {
            throw new EmailAuthCodeSendException(ErrorCode.NEED_AUTH_CODE_SEND);
        }

        if (userRepository.existsByEmail(email)) {
            throw new AlreadyVerifiedException(ErrorCode.DUPLICATED_EMAIL);
        }

        if (verificationCode.equals("verified")) {
            throw new AlreadyVerifiedException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        }

        if (code.equals(verificationCode)) {
            redisUtil.setDataAfterVerification(email, "verified", 10 * 60L);
            return new VerificationResponse("이메일 인증이 완료되었습니다.");
        }

        throw new InvalidVerificationCodeException(ErrorCode.INVALID_CODE);
    }

    @Transactional
    public EmailSendResponse sendEmailIfNotExists(String email) throws Exception {
        if (!userRepository.existsByEmail(email)) {
            sendVerificationCodeEmail(email);
            return new EmailSendResponse("10분내로 인증번호를 입력해주세요.");
        }

        if (redisUtil.existData(email)) {
            throw new BusinessException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        }
        throw new AuthenticationException(ErrorCode.DUPLICATED_EMAIL);
    }
}
