package server;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mackev
 */
public class EmailHandler {

    private final static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	
    // -- must goto to My Accounts (in Google Apps section), Sign in and Security, Apps with account access and set
    //    Allow less secure app: ON
    // -- set the gmail host URL
    final static private String HOST = "smtp.gmail.com";

    // -- You must have a valid gmail username/password pair to use
    //    gmail as a SMTP service
    final static private String USERNAME = "jmailman102";
    final static private String PASSWORD = "Javamail2@";
       
    private static boolean validEmailAddress (String emailaddress) {
        Matcher matcher = EMAIL_PATTERN.matcher(emailaddress.toLowerCase());
        return matcher.find();
    }
    
    private static String randomPassword() {
        Random rn = new Random();
        int[] Array = new int[4];
        char[] Char1 = new char[4];
        String sum = "";
        for (int i = 0; i < 2; ++i) {
            char r = (char) (rn.nextInt(26) + 'A');
            char l = (char) (rn.nextInt(26) + 'a');
            Char1[i] = r;
            Char1[i + 2] = l;
        }
        for (int i = 0; i < Array.length; ++i) {
            Array[i] = rn.nextInt(9) + 1;
            String q = "" + Array[i];
            String c = "" + Char1[i];
            sum = sum + q + c;
        }
        return sum;
    }

    
    public String sendRecoveryEmail(String toAddress) {
        if (!validEmailAddress(toAddress)) {
            System.out.println("invalid to address");
	}
        
        // -- Sender email address
        String from = "jmailman102@gmail.com";

        // -- Set system properties
        Properties properties = System.getProperties();

        // -- Setup mail server properties
        properties.setProperty("mail.smtp.host", HOST); // SMTP Host
        properties.setProperty("mail.smtp.port", "587"); // TLS Port
        properties.setProperty("mail.smtp.auth", "true"); // enable authentication
        properties.setProperty("mail.smtp.starttls.enable", "true"); // enable STARTTLS
	
	// -- Get the Session object.
	Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(USERNAME, PASSWORD);
            }
	});
	
        String password = randomPassword();
	try {
            // -- Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // -- Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // -- Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

            // -- Set Subject: header field
            message.setSubject("Message With Attachment");

            // -- Set the message
            message.setText(String.format("Here is your password:\n\t%s", password));

            // -- Send email
            Transport.send(message);
        } 
        catch (MessagingException e) {
            e.printStackTrace(System.err);
        }
        return password;
    }
}
