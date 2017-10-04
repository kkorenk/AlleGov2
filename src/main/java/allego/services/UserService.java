package allego.services;

import allego.models.User;
import allego.security.PasswordResetToken;
import allego.security.UserRole;

import java.util.Set;

/**
 * Created by ibm on 2017-08-15.
 */
public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);
    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);
    User findByEmail (String email);
    User createUser (User user, Set<UserRole> userRoles) throws Exception;
}
