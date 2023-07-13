package back.server.api.controller;

import back.server.api.dto.email.EmailRequestDto;
import back.server.domain.EmailMessage;
import back.server.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RequestMapping("/send-email")
@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailSenderService emailService;

    @PostMapping("/alarm")
    public ResponseEntity sendMail(@RequestBody EmailRequestDto emailRequestDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailRequestDto.getEmail())
                .subject("TEST")
                .message("TEST")
                .build();
        emailService.sendEmail(emailMessage);

        return ResponseEntity.ok(200);
    }
}
