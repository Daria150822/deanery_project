package com.example.deanery.dao.impl.inmemory;

import com.example.deanery.dao.GroupDao;
import com.example.deanery.model.Group;

import java.util.ArrayList;

class InMemoryGroupDao extends InMemoryAbstractDao<Group> implements GroupDao {

    InMemoryGroupDao(InMemoryDatabase database) {
        super(database.groups, Group::getGroupId, Group::setGroupId, database);
    }

    @Override
    public void deleteGroup(Group group) {
        this.delete(group);
    }
}
