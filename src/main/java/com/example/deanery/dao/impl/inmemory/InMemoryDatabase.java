package com.example.deanery.dao.impl.inmemory;

import com.example.deanery.dao.DaoFactory;
import com.example.deanery.model.*;
import java.util.*;

public class InMemoryDatabase {

    Map<Integer, User> users;
    Map<Integer, Group> groups;
    Map<Integer, Student> students;

    public InMemoryDatabase() {
        users = new TreeMap<>();
        groups = new TreeMap<>();
        students = new TreeMap<>();
    }

    public DaoFactory getDaoFactory() {
        return new InMemoryDaoFactory(this);
    }

}
