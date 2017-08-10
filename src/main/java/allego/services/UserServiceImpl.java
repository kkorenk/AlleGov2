package allego.services;

import allego.models.User;
import allego.repositories.RoleRepository;
import allego.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }
/*
    @Override
    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepo.findAll()));
        userRepo.save(user);
    }
*/
    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
