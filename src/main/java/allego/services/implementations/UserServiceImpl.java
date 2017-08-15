package allego.services.implementations;

import allego.models.User;
import allego.repositories.PasswordResetTokenRepository;
import allego.repositories.UserRepository;
import allego.security.PasswordResetToken;
import allego.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void CreatePasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken tempToken = new PasswordResetToken(token, user);

        passwordResetTokenRepository.save(tempToken);
    }
}