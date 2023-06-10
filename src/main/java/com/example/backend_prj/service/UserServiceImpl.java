package com.example.backend_prj.service;

import com.example.backend_prj.entity.ExpirationToken;
import com.example.backend_prj.entity.User;
import com.example.backend_prj.entity.Userdt;
import com.example.backend_prj.exception.UserAlreadyExistException;
import com.example.backend_prj.repository.ExpirationTokenRepository;
import com.example.backend_prj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpirationTokenRepository expirationTokenRepository;



    @Override
    public void saveUser(User user, ExpirationToken expirationToken) {

        expirationTokenRepository.save(expirationToken);

        userRepository.save(user);
    }
    public ExpirationToken saveVerificationWhileLoggingIn(int id){
        ExpirationToken expirationToken =
                expirationTokenRepository.findByTokenId(id);
        return expirationTokenRepository.save(expirationToken);
    }

    public Optional<User> findByUserByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }
    public Optional<User> findById(int id){
        return userRepository.findByUserId(id);
    }

    public void deleteAndCreateNewTokenWhenLoggingIn(int id, ExpirationToken expirationToken){
        expirationTokenRepository.deleteExpirationToken(id);
        expirationTokenRepository.save(expirationToken);
    }
    public void deleteExpirationToken(int id){
        expirationTokenRepository.deleteExpirationToken(id);
    }
    public int findTokenId(int userId){
        ExpirationToken expirationToken = expirationTokenRepository.findByUserId(userId);
        return expirationToken.getTokenId();
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
