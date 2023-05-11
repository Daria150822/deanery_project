package com.example.deanery.dao.impl.inmemory;

import com.example.deanery.dao.StudentDao;
import com.example.deanery.model.Group;
import com.example.deanery.model.Student;

import java.util.ArrayList;
import java.util.Collection;

class InMemoryStudentDao extends InMemoryAbstractDao<Student> implements StudentDao {

    InMemoryStudentDao(InMemoryDatabase database) {
        super(database.students, Student::getStudentId, Student::setStudentId, database);
    }

    @Override
    public Collection<Student> findByGroupId(Integer groupId) {
        return database.groups.get(groupId).getStudents();
    }

    @Override
    public void addStudent(Group group, String name) {
        Student student = new Student(-1, name);

        this.insert(student, true);
        if (group.getStudents() == null) {
            group.setStudents(new ArrayList<>());
        }

        group.getStudents().add(student);
    }

    @Override
    public void deleteStudent(Group group, Student student) {
        this.delete(student);
        group.getStudents().removeIf(i -> i.getStudentId().equals(student.getStudentId()));
    }
}
