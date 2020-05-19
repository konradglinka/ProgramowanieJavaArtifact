package EmailActions;

import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


public class EmailToRegister implements Runnable {
    @Override
    public void run() {
        try {
            sendRegistrationEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public EmailToRegister(TextField registrationEmailTextField) {
        this.registrationEmail= registrationEmailTextField.getText();
        setRegistrationCode();
        this.registrationCode=getRegistrationCode();
    }

    String registrationEmail;


    String registrationCode="";
    // Adres email aplikacji która wysyła maila
    private static final String FROM = "weatherappkma@gmail.com";
    // Hasło do konta aplikacji która wysyła maila
    private static final String PASSWORD = "c7kj92rmz";




    public void sendRegistrationEmail() throws MessagingException {

        // Temat wiadomości
       String SubjectOfEmail = "Potwierdzenie rejestracji";
        // Treść wiadomości
        String CONTENT = "Aby potwierdzić rejestracje wpisz kod: "+getRegistrationCode();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM, PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(registrationEmail)
            );
            message.setSubject(SubjectOfEmail);
            message.setText(CONTENT);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    public void setRegistrationCode(){
        this.registrationCode="";
        Random random = new Random();
        this.registrationCode+=random.nextInt(10);
        this.registrationCode+=random.nextInt(10);
        this.registrationCode+=random.nextInt(10);
        this.registrationCode+=random.nextInt(10);

    }
public String getRegistrationCode(){
        return registrationCode;
}
}



