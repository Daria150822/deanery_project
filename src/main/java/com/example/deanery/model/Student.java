package com.example.deanery.model;

public class Student {
    private Integer studentId;
    private String name;

    public Student(Integer studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
