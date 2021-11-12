package id.careerfund.api.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.MimeMessage;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
@Component("emailSender")
public class EmailSenderConfig {
    private final static Logger logger = LoggerFactory.getLogger(EmailSenderConfig.class);

    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.sender.name:}")
    private String senderName;

    @Value("${spring.mail.sender.mail:}")
    private String senderEmail;

    private final TaskExecutor taskExecutor;

    public EmailSenderConfig(JavaMailSenderImpl mailSender, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.mailSender = mailSender;
        this.taskExecutor = taskExecutor;
    }

    public boolean send(String email, String subject, String message) {
        return send(null, email, subject, message);
    }

    public boolean send(String from, String email, String subject, String message) {
        MimeMessage mime = mailSender.createMimeMessage();
        if (ObjectUtils.isEmpty(from)) {
            from = senderEmail;
        }
        if (ObjectUtils.isEmpty(from)) {
            from = "admin@mail.com";
        }
        boolean success = false;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setFrom(from, senderName);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mime);
            success = true;
        } catch (Exception e) {
            logger.error("error: " + e.getMessage());
        }

        return success;
    }

    public void sendAsync(final String to, final String subject, final String message) {
        taskExecutor.execute(() -> send(to, subject, message));
    }
}

