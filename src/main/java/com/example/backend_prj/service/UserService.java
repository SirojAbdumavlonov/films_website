package com.example.backend_prj.service;

import com.example.backend_prj.entity.ExpirationToken;
import com.example.backend_prj.entity.User;

public interface UserService {
    void saveUser(User user, ExpirationToken expirationToken);




}
