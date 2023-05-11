package com.example.deanery.services;

import com.example.deanery.dao.DaoFactory;
import com.example.deanery.model.Group;
import com.example.deanery.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GroupServiceImpl implements GroupService {

    DaoFactory daoFactory;

    public GroupServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Group getGroupById(Integer groupId) {
        return daoFactory.getGroupDao().get(groupId);
    }

    @Override
    public Student getStudentById(Integer studentId) {
        return daoFactory.getStudentDao().get(studentId);
    }

    @Override
    public void addStudent(Group group, String name) {
        daoFactory.getStudentDao().addStudent(group, name);
    }

    @Override
    public void editStudent(Student student, String name) {
        student.setName(name);
    }

    @Override
    public void addGroup(String name) {
        daoFactory.getGroupDao().insert(new Group(-1, name), true);
    }

    @Override
    public void editGroup(Group group, String title) {
        group.setTitle(title);
        daoFactory.getGroupDao().update(group);
    }

    @Override
    public void deleteGroup(Group group) {
        daoFactory.getGroupDao().deleteGroup(group);
    }

    @Override
    public void deleteStudent(Group group, Student student) {
        daoFactory.getStudentDao().deleteStudent(group, student);
    }

    @Override
    public Collection<Group> getAllGroups() {
        return daoFactory.getGroupDao().findAll();
    }

    @Override
    public Collection<Group> getGroups(String text) {
        Collection<Group> allGroups = new ArrayList<>(getAllGroups());
        if (text != null && !text.isEmpty()) {
            allGroups = allGroups.stream().filter(group -> group.getTitle().contains(text)).collect(Collectors.toList());
        }
        return allGroups;
    }

    @Override
    public List<Student> getStudents(Integer groupId, String search) {
        List<Student> students = getGroupById(groupId).getStudents();

        if (search != null && !search.isEmpty()) {
            students = students.stream()
                    .filter(student -> student.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return students;
    }

    @Override
    public List<Group> getGroupsContainingStudent(Integer studentId) {
        return daoFactory.getGroupDao().findAll()
                .stream()
                .filter(c -> c.getStudents() != null && c.getStudents().stream().anyMatch(i -> i.getStudentId().equals(studentId)))
                .collect(Collectors.toList());
    }


}
