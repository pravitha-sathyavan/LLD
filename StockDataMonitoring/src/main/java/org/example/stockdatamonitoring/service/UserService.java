package org.example.stockdatamonitoring.service;

import org.example.stockdatamonitoring.domain.Users;
import org.example.stockdatamonitoring.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String name, String email) {
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        if(!userRepository.existsByEmail(email)){
            userRepository.save(user);
        }
    }
}
