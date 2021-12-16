package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
@Async
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final Environment environment;
    private final TemplateEngine templateEngine;

    @Override
    public void send(String to, String subject, String content) {
        send(to, subject, content, this.environment.getProperty("spring.mail.sender.mail"));
    }

    @Override
    public void send(String to, String subject, String content, String from) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setText(content, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email {}", to, e);
            throw new IllegalStateException("Failed to send email " + to);
        }
    }

    @Override
    public void sendVerificationEmail(User user, String otp) {
        final Context ctx = new Context();
        ctx.setVariable("name", user.getName());
        ctx.setVariable("otp", otp);

        final String htmlContent = this.templateEngine.process("html/email-verification.html", ctx);
        send(user.getEmail(), "Verification Email", htmlContent);
    }

}
