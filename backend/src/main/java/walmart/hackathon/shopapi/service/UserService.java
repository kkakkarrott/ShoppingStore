package walmart.hackathon.shopapi.service;


import walmart.hackathon.shopapi.entity.User;

import java.util.Collection;

public interface UserService {
    User findOne(String email);

    Collection<User> findByRole(String role);

    User save(User user);

    User update(User user);
}
