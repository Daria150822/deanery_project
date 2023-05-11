package com.example.deanery.dao;

import com.example.deanery.model.Group;
import com.example.deanery.model.Student;

import java.util.Collection;

public interface StudentDao extends AbstractDao<Student> {

    Collection<Student> findByGroupId(Integer groupId);

    void addStudent(Group group, String name);

    void deleteStudent(Group group, Student student);
}
