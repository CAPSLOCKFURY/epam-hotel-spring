package com.example.epamhotelspring.service;

import com.example.epamhotelspring.forms.UserUpdateForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User registerUser(User user){
        String userPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(userPassword));
        return repository.save(user);
    }

    public User getUserByEmail(String email){
        return repository.findUserByEmail(email);
    }

    public User getUserById(Long id){
        return repository.findUserById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addBalance(BigDecimal amount, User user){
        User dbUser = repository.findUserById(user.getId());
        dbUser.setBalance(dbUser.getBalance().add(amount));
        repository.save(dbUser);
    }

    @Transactional
    public void updateProfile(UserUpdateForm form, Long userId){
        User user = repository.findUserById(userId);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        repository.save(user);
    }

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
}
