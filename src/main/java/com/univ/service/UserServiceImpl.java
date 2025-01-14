package com.univ.service;

import com.univ.model.entity.User;
import com.univ.repository.UserRepository;
import com.univ.repository.UserRepositoryImpl;
import com.univ.util.BCryptUtil;
import com.univ.validator.UserValidator;
import com.univ.validator.ValidationResult;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    public Optional<User> getUserById(UUID id) throws Exception {
        return userRepository.findById(id);
    }

    public List<User> getUserList() throws Exception {
        return userRepository.findAll();
    }

    public ValidationResult createUser(User user, String passwordConfirmation) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        UserValidator userValidator = UserValidator.of(user, optionalUser);
        ValidationResult validationResult = userValidator.validateUsername().validatePassword(passwordConfirmation).build();

        if (validationResult.isValid()) {
            String userPassword = user.getPassword();
            String hashedPassword = BCryptUtil.hashPassword(userPassword);
            User cloneUser = User.copyOf(user);
            cloneUser.setPassword(hashedPassword);
            User savedUser = userRepository.save(cloneUser);
            user.setId(savedUser.getId());
        }
        return validationResult;
    }

    public User updateUser(User user) throws Exception {
        if (user.getId() == null || userRepository.findById(user.getId()).isEmpty()) {
            throw new EntityNotFoundException("Utilisateur non trouvé");
        }
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) throws Exception {
        userRepository.deleteById(id);
    }
}