package com.example.deanery.services;

import com.example.deanery.model.User;

public interface UserService {

    User getByLogin(String login);

    boolean checkPassword(User user, String password);
}
