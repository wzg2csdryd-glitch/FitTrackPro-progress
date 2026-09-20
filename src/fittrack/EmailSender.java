package fittrack;

import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends one plain-text e-mail through an SMTP server (Gmail by default) using
 * the JavaMail library (mail.jar and activation.jar in the lib folder).
 * This is backend code: it never shows anything on screen, it only returns a
 * message describing what went wrong, so EmailGUI decides how to display it.
 * The sender's password is used for the one call and is never stored or written to a file.
 */
public class EmailSender {
    public static final String GMAIL_HOST = "smtp.gmail.com";
    public static final int GMAIL_PORT = 587;

    /**
     * Sends an e-mail through Gmail. The server can be replaced for testing by
     * starting Java with -Dfittrack.smtp.host, -Dfittrack.smtp.port and
     * -Dfittrack.smtp.tls=false; without those settings Gmail is used.
     * @param from the sender's Gmail address (also the log-in name)
     * @param appPassword the 16-character Gmail app password (not the normal password)
     * @param to the recipient's address
     * @param subject the subject line
     * @param body the message text
     * @return an empty string if the e-mail was sent, otherwise a sentence explaining the failure
     */
    public static String send(String from, String appPassword, String to, String subject, String body) {
        String host = System.getProperty("fittrack.smtp.host", GMAIL_HOST);
        int port = Integer.parseInt(System.getProperty("fittrack.smtp.port", String.valueOf(GMAIL_PORT)));
        boolean startTls = Boolean.parseBoolean(System.getProperty("fittrack.smtp.tls", "true"));
        return sendVia(host, port, startTls, from, appPassword, to, subject, body);
    }

    /**
     * Sends an e-mail through the given SMTP server. Steps: describe the server
     * in a Properties object, open a Session that knows the log-in details, build
     * the message (from, to, subject, text) and hand it to Transport to send.
     * @param host the SMTP server name, e.g. smtp.gmail.com
     * @param port the SMTP port, e.g. 587
     * @param startTls true to switch the connection to encrypted (TLS) before logging in
     * @param from the sender's address, also used as the log-in name
     * @param password the password for that log-in
     * @param to the recipient's address
     * @param subject the subject line
     * @param body the message text
     * @return an empty string if the e-mail was sent, otherwise a sentence explaining the failure
     */
    public static String sendVia(String host, int port, boolean startTls, final String from,
            final String password, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "15000");
        if (startTls) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.ssl.trust", host);
        }

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            Transport.send(message);
            return "";
        } catch (AuthenticationFailedException e) {
            return "The mail server did not accept the log-in. Check the address and use a Gmail app password, not your normal password.";
        } catch (SendFailedException e) {
            return "The message was not accepted. Check the To address.";
        } catch (MessagingException e) {
            if (isConnectionProblem(e)) {
                return "Could not reach the mail server. Check your internet connection (a school network may block e-mail).";
            }
            return "Could not send the e-mail: " + e.getMessage();
        }
    }

    /**
     * Looks down the chain of causes of an exception to see whether the real
     * problem was a network one (host not found, connection refused or timed out).
     * @param e the exception thrown while sending
     * @return true if a network problem is somewhere in the cause chain
     */
    private static boolean isConnectionProblem(Throwable e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            if (t instanceof java.net.UnknownHostException || t instanceof java.net.ConnectException
                    || t instanceof java.net.SocketTimeoutException || t instanceof java.net.NoRouteToHostException) {
                return true;
            }
        }
        return false;
    }
}
