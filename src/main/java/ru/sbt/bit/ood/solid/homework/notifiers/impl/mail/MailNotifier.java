package ru.sbt.bit.ood.solid.homework.notifiers.impl.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.sbt.bit.ood.solid.homework.notifiers.Notifier;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Ivan on 20/11/2016.
 */
public class MailNotifier implements Notifier {
    private final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private final String recipients;
    private final String subject;
    private final boolean isHtml;
    private final String host;

    public MailNotifier(String subject,
                        String recipients,
                        boolean isHtml,
                        String host) {
        this.recipients = recipients;
        this.subject = subject;
        this.isHtml = isHtml;
        this.host = host;
    }

    @Override
    public <T> void doNotify(T object) {
        mailSender.setHost(host);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            helper.setText(object.toString(), isHtml);
            helper.setSubject(subject);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
