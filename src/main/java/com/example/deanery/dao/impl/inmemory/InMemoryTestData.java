package com.example.deanery.dao.impl.inmemory;

import com.example.deanery.model.Group;
import com.example.deanery.model.Student;
import com.example.deanery.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryTestData {

    public static void generateTo(InMemoryDatabase database) {
        database.users.clear();
        database.students.clear();
        database.groups.clear();

        User alice = new User(1, "Alice", "alice@example.com", "passwordhash");
        User bob = new User(2, "Bob", "bob@example.com", "passwordhash");
        User charlie = new User(3, "Charlie", "charlie@example.com", "passwordhash");
        User diana = new User(4, "Diana", "diana@example.com", "passwordhash");
        User evil = new User(5, "Evil Emperror", "evil@example.com", "passwordhash");
        List<User> users = Arrays.asList(alice, bob, charlie, diana, evil);
        users.forEach(user -> database.users.put(user.getUserId(), user));

        List<Student> studentsOne = new ArrayList<>(Arrays.asList(
                new Student(1, "Steve"),
                new Student(2, "Jill"),
                new Student(3, "Bill"),
                new Student(4, "Andrew")
        ));
        List<Student> studentsTwo = new ArrayList<>(Arrays.asList(
                new Student(5, "Samantha"),
                new Student(6, "Steffany"),
                new Student(7, "Nate")
        ));
        studentsOne.forEach(student -> database.students.put(student.getStudentId(), student));
        studentsOne.forEach(student -> database.students.put(student.getStudentId(), student));

        Group groupOne = new Group(1, "IT-1");
        groupOne.setStudents(studentsOne);
        Group groupTwo = new Group(2, "IT-2");
        groupTwo.setStudents(studentsTwo);
        List<Group> groups = Arrays.asList(groupOne, groupTwo);
        groups.forEach(group -> database.groups.put(group.getGroupId(), group));
    }
}
