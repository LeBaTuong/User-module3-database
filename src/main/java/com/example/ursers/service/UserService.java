package com.example.ursers.service;

import com.example.ursers.dao.UserDao;
import com.example.ursers.model.User;
import com.example.ursers.model.dto.Page;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(){
        userDao = new UserDao();
    }
    public Page<User> getUsers(int page, boolean isShowRestore, String search){
        return userDao.findAll(page, isShowRestore,search);
    }
    public void createUser(User user){
        userDao.create(user);
    }
    public void update(User user, int id){
        user.setId(id);
        userDao.update(user);
    }
    public User findUserByID(int id){
        return userDao.findByID(id);
    }
    public void delete(int id){
        userDao.delete(id);
    }
    public void restore(String[] ids){
       for(var id:ids){
           userDao.restore(Integer.parseInt(id));
       }
    }
}
