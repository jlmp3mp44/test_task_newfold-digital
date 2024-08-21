package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String to, String subject, String body, String filePath) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("julya.meleshko@gmail.com", "julya123456_12");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("julya.meleshko@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("meleshko.julya@lll.kpi.ua"));
            message.setSubject("Test Email Subject");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);
            multipart.addBodyPart(textPart);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(filePath);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendEmail("meleshko.julya@lll.kpi.ua", "Test Task Report", "Please loof at  the attached TestNG report.", "D:\\java_projects\\untitled1\\allure-report\\index.html");
    }
}

