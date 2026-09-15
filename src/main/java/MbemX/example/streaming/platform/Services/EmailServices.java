package MbemX.example.streaming.platform.Services;

import MbemX.example.streaming.platform.Entity.EmailHistory;
import MbemX.example.streaming.platform.Entity.User;
import MbemX.example.streaming.platform.Enums.EmailStatus;
import MbemX.example.streaming.platform.Enums.EmailType;
import MbemX.example.streaming.platform.Repository.EmailHistoryRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServices {

    private final JavaMailSender mailSender;
    private final EmailHistoryRepository emailHistoryRepository;

    public EmailServices(
            JavaMailSender mailSender,
            EmailHistoryRepository emailHistoryRepository) {

        this.emailHistoryRepository = emailHistoryRepository;
        this.mailSender = mailSender;
    }

    // =========================================================
    // 1. ACTIVATION EMAIL
    // =========================================================

    public void sendActivationEmail(User user, String code) {

        String subject = "Account Activation Code";

        String message = """
                Hello %s,

                Welcome to your Personal Media Space.

                Your account activation code is:

                %s

                This code will expire soon.

                If you did not create this account, please ignore this email.

                Best regards,
                Personal Media Space
                """.formatted(
                user.getName(),
                code
        );

        sendEmail(
                user.getEmail(),
                subject,
                message,
                EmailType.ACTIVATION,
                user
        );
    }


    // =========================================================
    // 2. FILE UPLOAD CONFIRMATION EMAIL
    // =========================================================

    public void sendUploadConfirmationEmail(
            User user,
            String fileName
    ) {

        String subject = "File Upload Confirmation";

        String message = """
                Hello %s,

                Your file has been uploaded successfully
                to your Personal Media Space.

                File name:
                %s

                Your file is now available in your media space.

                Best regards,
                Personal Media Space
                """.formatted(
                user.getName(),
                fileName
        );

        sendEmail(
                user.getEmail(),
                subject,
                message,
                EmailType.CONFIRMATION_SEND,
                user
        );
    }


    // =========================================================
    // 3. DAILY BACKUP EMAIL
    // =========================================================

    public void sendDailyBackupEmail(
            User user,
            String fileName
    ) {

        String subject = "Your Daily Media Backup";

        String message = """
                Hello %s,

                Your daily media backup has been created successfully.

                Backup file:
                %s

                Your personal files have been backed up.

                Best regards,
                Personal Media Space
                """.formatted(
                user.getName(),
                fileName
        );

        sendEmail(
                user.getEmail(),
                subject,
                message,
                EmailType.DAILY_SAVE,
                user
        );
    }


    // =========================================================
    // 4. COMMON EMAIL METHOD
    // =========================================================

    public void sendEmail(
            String recipient,
            String subject,
            String message,
            EmailType type,
            User user
    ) {

        EmailHistory history = new EmailHistory();

        history.setRecipient(recipient);
        history.setSubject(subject);
        history.setDateSent(LocalDateTime.now());
        history.setUser(user);

        try {

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(recipient);
            mail.setSubject(subject);
            mail.setText(message);

            mailSender.send(mail);

            history.setStatus(EmailStatus.SUCCESS);

        } catch (Exception exception) {

            history.setStatus(EmailStatus.FAILURE);

            emailHistoryRepository.save(history);

            throw new RuntimeException(
                    "Failed to send email to " + recipient,
                    exception
            );
        }

        emailHistoryRepository.save(history);
    }
}