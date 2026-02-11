package Utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;


import java.io.File;
import java.util.Properties;
import java.util.Set;

public class EmailUtils {

    // 1️⃣ Send email to a single recipient (TO)
    public void sendEmailTo(String senderEmail, String senderPassword,
                            String recipientEmail, String subject,
                            String body, String resumePath) {
        sendEmailInternal(senderEmail, senderPassword, recipientEmail, subject, body, resumePath, null);
    }

    // 2️⃣ Send email only via BCC (multiple HRs)
    public void sendEmailToBCC(String senderEmail, String senderPassword,
                               Set<String> bccEmails, String subject,
                               String body, String resumePath) {
        sendEmailInternal(senderEmail, senderPassword, null, subject, body, resumePath, bccEmails);
    }

    // 🔒 Common method for actual sending
    private void sendEmailInternal(String senderEmail, String senderPassword,
                                   String recipientEmail, String subject,
                                   String body, String resumePath,
                                   Set<String> bccEmails) {

        // SMTP server settings
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));

            // ✅ TO recipient
            if (recipientEmail != null && !recipientEmail.isEmpty()) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipientEmail));
            }

            // ✅ BCC recipients
            if (bccEmails != null && !bccEmails.isEmpty()) {
                for (String email : bccEmails) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(email));
                }
            }

            message.setSubject(subject);

            // ✅ Email body (HTML content)
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body, "text/html; charset=utf-8");

            // ✅ Attachment (optional)
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            if (resumePath != null && !resumePath.trim().isEmpty()) {
                File file = new File(resumePath);
                if (file.exists()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                } else {
                    System.err.println("⚠️ Attachment file not found: " + resumePath);
                }
            }

            // ✅ Set final content
            message.setContent(multipart);

            // ✅ Send
            Transport.send(message);

            System.out.println("✅ Email sent successfully!");
            if (recipientEmail != null) {
                System.out.println("   TO : " + recipientEmail);
            }
            if (bccEmails != null && !bccEmails.isEmpty()) {
                System.out.println("   BCC : " + bccEmails);
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to send email:");
            e.printStackTrace();
        }

    }

    public void deleteExistingPdfs(String folderPath) {

        File dir = new File(folderPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("📁 Folder not found: " + folderPath);
            return;
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

        if (files == null || files.length == 0) {
            System.out.println("🧹 No existing PDFs to delete");
            return;
        }

        for (File file : files) {
            if (file.delete()) {
                System.out.println("🗑️ Deleted: " + file.getName());
            } else {
                System.out.println("⚠️ Failed to delete: " + file.getName());
            }
        }
    }

    public void downloadPdfAttachments(String email,
                                       String password,
                                       String expectedSubject,
                                       String downloadDirPath) {

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", email, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            int start = Math.max(1, totalMessages - 30);

            Message[] messages = inbox.getMessages(start, totalMessages);

            File downloadDir = new File(downloadDirPath);
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            System.out.println("📥 Scanning last " + messages.length + " emails...");

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];

                if (message.getSubject() == null ||
                        !message.getSubject().contains(expectedSubject)) {
                    System.out.println("No matching email found");
                    continue;
                }

                if (message.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) message.getContent();

                    for (int j = 0; j < multipart.getCount(); j++) {
                        BodyPart bodyPart = multipart.getBodyPart(j);

                        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
                                || bodyPart.getFileName() != null) {

                            String fileName = bodyPart.getFileName();

                            if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {

                                File file = new File(downloadDir, fileName);
                                MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
                                mimeBodyPart.saveFile(file);

                                System.out.println("✅ PDF downloaded: " + file.getAbsolutePath());
                            }
                        }
                    }
                }
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            System.err.println("❌ Failed to download PDF attachments");
            e.printStackTrace();
        }
    }

}
