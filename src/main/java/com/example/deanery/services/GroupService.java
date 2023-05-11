package com.example.deanery.services;

import com.example.deanery.model.Group;
import com.example.deanery.model.Student;

import java.util.Collection;
import java.util.List;

public interface GroupService {

    Collection<Group> getAllGroups();

    Collection<Group> getGroups(String text);

    List<Student> getStudents(Integer groupId, String search);

    List<Group> getGroupsContainingStudent(Integer studentId);

    Group getGroupById(Integer groupId);

    Student getStudentById(Integer studentId);

    void addStudent(Group Group, String name);

    void editStudent(Student student, String name);

    void addGroup(String name);

    void editGroup(Group group, String text);

    void deleteGroup(Group group);

    void deleteStudent(Group group, Student student);
}
