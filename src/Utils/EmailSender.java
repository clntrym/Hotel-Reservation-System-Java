/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {

    public static void sendOTP(String toEmail, String otp) {

        final String fromEmail = "cleintraymundsalarda@gmail.com";
        final String appPassword = "rouqbcrgbcrbhfbl";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, appPassword);
                }
            });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
            );

            message.setSubject("NCST Hotel Password Reset OTP");

            message.setText(
                "Your OTP Code is: " + otp +
                "\n\nDo not share this code with anyone."
            );

            Transport.send(message);

            System.out.println("OTP Sent Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}