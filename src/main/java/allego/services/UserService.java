package allego.services;

import allego.models.User;
import allego.security.PasswordResetToken;

/**
 * Created by ibm on 2017-08-15.
 */
public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void CreatePasswordResetTokenForUser(final User user, final String token);
}
