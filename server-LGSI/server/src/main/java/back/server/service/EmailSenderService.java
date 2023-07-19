package back.server.service;

import back.server.api.dto.email.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getMessage());

        mailSender.send(message);
        log.info("success");
    }
}
