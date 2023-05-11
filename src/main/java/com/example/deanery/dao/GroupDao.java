package com.example.deanery.dao;


import com.example.deanery.model.Group;

public interface GroupDao extends AbstractDao<Group> {

    void deleteGroup(Group group);
}
