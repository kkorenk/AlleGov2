package allego.services;

import allego.models.User;

import java.util.List;

/**
 * Created by Kamil on 21.07.2017.
 */
public interface UserService {
    List<User> findAll();
//    void save(User user);
    User findByUsername(String username);
}
