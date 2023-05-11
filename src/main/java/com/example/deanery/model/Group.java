package com.example.deanery.model;

import java.util.List;

public class Group {
    private Integer groupId;
    private String title;
    private List<Student> students;

    public Group(Integer groupId, String title) {
        this.groupId = groupId;
        this.title = title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
