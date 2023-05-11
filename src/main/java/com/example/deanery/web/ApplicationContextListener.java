package com.example.deanery.web;

import com.example.deanery.dao.DaoFactory;
import com.example.deanery.dao.impl.inmemory.InMemoryDatabase;
import com.example.deanery.dao.impl.inmemory.InMemoryTestData;
import com.example.deanery.services.GroupService;
import com.example.deanery.services.GroupServiceImpl;
import com.example.deanery.services.UserService;
import com.example.deanery.services.UserServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.function.UnaryOperator;

public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // Change to real database in real project
        InMemoryDatabase database = new InMemoryDatabase();

        // Don't use in real project
        InMemoryTestData.generateTo(database);

        DaoFactory daoFactory = database.getDaoFactory();

        UserService userService = new UserServiceImpl(daoFactory, UnaryOperator.identity());
        sce.getServletContext().setAttribute("userService", userService);

        GroupService groupService = new GroupServiceImpl(daoFactory);
        sce.getServletContext().setAttribute("groupService", groupService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
