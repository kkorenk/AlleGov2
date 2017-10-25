package allego.utility;

import allego.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by ibm on 2017-10-01.
 */
@Component
public class MailConstructor {
    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password) {

        String url = contextPath +"/confirm?token=" + token;
        String message = "\nClick this link to verify your email on Allego account. Your password is: \n" + password +"\nYou can change your password in MyAccount section.";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("AlleGo - new account confirmation");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));

        return email;
    }




}
