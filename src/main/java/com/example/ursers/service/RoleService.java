package com.example.ursers.service;

import com.example.ursers.dao.RoleDao;
import com.example.ursers.model.Role;

import java.util.List;

public class RoleService {
    private final RoleDao roleDao = new RoleDao();
    public List<Role> getRoles(){
        return roleDao.findAll();
    }
    public Role getRole(int id){
        return roleDao.findById(id);
    }
}
