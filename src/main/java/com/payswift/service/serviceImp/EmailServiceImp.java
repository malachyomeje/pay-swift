package com.payswift.service.serviceImp;

import com.payswift.dtos.request.EmailDto;
import com.payswift.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {


    private final JavaMailSender javaMailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendEmail(EmailDto emailDto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            LOGGER.info("Transferring Data from EmailSenderDto to MimeMessage helper");
            message.setTo(emailDto.getTo());
            message.setSubject(emailDto.getSubject());
            message.setText(emailDto.getContent(), true);
            javaMailSender.send(mimeMessage);
            LOGGER.info("Mail has been sent");
        } catch (Exception ex) {

            LOGGER.error("An error occurred while sending an email to address : " + emailDto.getTo()
                    + "; error: " + ex.getMessage());


        }
    }
}





