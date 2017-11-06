package allego.services.implementations;

import allego.models.User;
import allego.models.cart.ShoppingCart;
import allego.repositories.PasswordResetTokenRepository;
import allego.repositories.RoleRepository;
import allego.repositories.UserRepository;
import allego.security.PasswordResetToken;
import allego.security.UserRole;
import allego.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken tempToken = new PasswordResetToken(token, user);

        passwordResetTokenRepository.save(tempToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User createUser (User user, Set<UserRole> userRoles) throws Exception {
        User tempUser = userRepository.findByUsername(user.getUsername());

        if(tempUser !=null){
            LOG.info("User {} already exists.", user.getUsername());
        }else{
            for(UserRole userRole : userRoles){
                roleRepository.save(userRole.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            tempUser = userRepository.save(user);
        }

        return tempUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}