package backend.backend.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import backend.backend.auth.config.util.RedisUtil;
import backend.backend.auth.dto.EmailSendResponse;
import backend.backend.auth.dto.VerificationResponse;
import backend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    public static final String ePw = createKey();

    private MimeMessage createReservationMessage(String targetEmail, String roomName) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : "+ targetEmail);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, targetEmail); //보내는 대상
        message.setSubject("예약 완료");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Team Achieve 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p> 예약완료 되었습니다<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>예약완료 되었습니다</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "예약한 방 : <strong>";
        msgg+=  roomName +"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("academyschool7@gmail.com","Achieve"));//보내는 사람
        return message;
    }

    private MimeMessage createVerificationMessage(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : "+ targetEmail);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, targetEmail); //보내는 대상
        message.setSubject("회원가입 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Team Achieve 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("academyschool7@gmail.com","Achieve"));//보내는 사람

        redisUtil.setDataExpire(targetEmail, ePw, 10 * 60L);
        return message;
    }

    private static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

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

    private String sendVerificationCodeEmail(String targetEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createVerificationMessage(targetEmail);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }


    // 에약용 메일 전송 메서드
    public void sendReservationEmail(String targetEmail, String meetingRoomName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createReservationMessage(targetEmail, meetingRoomName);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


    @Transactional
    public VerificationResponse verifyEmail(String email, String code) {
        String verificationCode = redisUtil.getData(email);
        if (code.equals(verificationCode)) {
            redisUtil.setDataAfterVerification(email, "verified", 10 * 60L);
            return new VerificationResponse("이메일 인증이 완료되었습니다.");
        }
        throw new IllegalStateException("코드가 옳지 못합니다.");
    }

    public EmailSendResponse sendEmailIfNotExists(String email) throws Exception {
        if (userRepository.findUserByEmail(email) == null) {
            sendVerificationCodeEmail(email);
            return new EmailSendResponse("10분내로 인증번호를 입력해주세요.");
        }
        throw new IllegalStateException("이메일이 존재합니다.");
    }
}
