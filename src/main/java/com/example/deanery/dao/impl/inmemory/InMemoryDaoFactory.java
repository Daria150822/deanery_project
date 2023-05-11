package com.example.deanery.dao.impl.inmemory;

import com.example.deanery.dao.DaoFactory;
import com.example.deanery.dao.GroupDao;
import com.example.deanery.dao.StudentDao;
import com.example.deanery.dao.UserDao;

class InMemoryDaoFactory implements DaoFactory {

    InMemoryDatabase database;

    UserDao userDao;
    StudentDao studentDao;
    GroupDao groupDao;

    InMemoryDaoFactory(InMemoryDatabase database) {
        this.database = database;

        userDao = new InMemoryUserDao(database);
        groupDao = new InMemoryGroupDao(database);
        studentDao = new InMemoryStudentDao(database);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }
}
