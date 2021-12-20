package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;

import javax.mail.MessagingException;

public interface EmailService {
    void send(String to, String subject, String content) throws MessagingException;

    void send(String to, String subject, String content, String from) throws MessagingException;

    void sendVerificationEmail(User user, String otp) throws MessagingException;

    void sendResetPasswordEmail(User user, String resetUrl) throws MessagingException;
}
