package com.example.deanery.dao;

public interface DaoFactory {

    UserDao getUserDao();

    GroupDao getGroupDao();

    StudentDao getStudentDao();
}
