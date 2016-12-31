package test.screenlocker.com.myapplication.utils;
import android.net.Uri;

import java.security.Security;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends javax.mail.Authenticator {
private String mailhost = "smtp.gmail.com";
private String user;
private String password;
private Session session;
String ContentType = "";
static {
    Security.addProvider(new JSSEProvider());
}

public GMailSender(String user, String password) {
    this.user = user;
    this.password = password;

    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.host", mailhost);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.quitwait", "false");

    session = Session.getDefaultInstance(props, this);
}

protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(user, password);
}

public synchronized void sendMail(String subject, String body,
                                  String sender, String recipients, List<Uri> uriList)
        throws Exception {

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                recipients));
        message.setSubject(subject);

        // 3) create MimeBodyPart object and set your message content
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(body);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);

        for (int i = 0; i < uriList.size(); i++) {
            // 4) create new MimeBodyPart object and set DataHandler object
            // to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = uriList
                    .get(i)
                    .getPath()
                    .substring(
                            uriList.get(i).getPath().lastIndexOf("/") + 1,
                            uriList.get(i).getPath().length());// change
                                                                // accordingly
            System.out.println("filename " + filename);
            DataSource source = new FileDataSource(uriList.get(i).getPath());
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            // 5) create Multipart object and add MimeBodyPart objects to
            // this object
            multipart.addBodyPart(messageBodyPart2);
        }

        // 6) set the multiplart object to the message object
        message.setContent(multipart);

        // 7) send message
        Transport.send(message);

        System.out.println("message sent....");
    } catch (MessagingException ex) {
        ex.printStackTrace();
    }
}
}