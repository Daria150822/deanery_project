package com.example.deanery.dao;

import com.example.deanery.model.User;

public interface UserDao extends AbstractDao<User> {

    User getByLogin(String login);
}
