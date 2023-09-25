package com.example.ursers.dao;

import com.example.ursers.model.Role;
import com.example.ursers.model.User;
import com.example.ursers.model.dto.Page;
import com.example.ursers.model.enumration.EGender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends DatabaseConnection{
    public Page<User> findAll(int page, boolean isShowRestore, String search){
        Page<User> result = new Page<>();
        result.setCurrentPage(page);
        List<User> userList = new ArrayList<>();
        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final var DELETED = isShowRestore? 1 : 0;

         String SELECT_ALL = "SELECT u.*, r.name role_name FROM users u JOIN roles r on u.role_id = r.id WHERE u.deleted=? and" +
                 " (LOWER(u.userName) LIKE ? OR LOWER(u.email) LIKE ? OR LOWER(u.firstName) LIKE ?) " +
                 " LIMIT 5 OFFSET ? ";
//                 "LIMIT 2 OFFSET ?";;
        String SELECT_COUNT = "SELECT COUNT(1) cnt FROM users u JOIN roles r on u.role_id = r.id WHERE u.deleted=? and" +
                " (LOWER(u.userName) LIKE ? OR LOWER(u.email) LIKE ? OR LOWER(u.firstName) LIKE ?) ";
         try(Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL)) {
             preparedStatement.setInt(1, DELETED);
             preparedStatement.setString(2, search);
             preparedStatement.setString(3, search);
             preparedStatement.setString(4, search);
             preparedStatement.setInt(5, (page-1) * 5);
             System.out.println(preparedStatement);
             ResultSet rs = preparedStatement.executeQuery();
             while(rs.next()){
//                 return getUserByResultSet(rs);
                 userList.add(getUserByResultSet(rs));
             }
             result.setContent(userList);
             PreparedStatement preparedStatementCount=connection.prepareStatement(SELECT_COUNT);
             preparedStatementCount.setInt(1, DELETED);
             preparedStatementCount.setString(2, search);
             preparedStatementCount.setString(3, search);
             preparedStatementCount.setString(4, search);
             ResultSet rsCount= preparedStatementCount.executeQuery();
             while (rsCount.next()){
                 result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt")/5));
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());;
         }
         return result;

    }
    public void create(User user){
        String Create = "INSERT INTO `user`.`users` (`firstName`, `lastName`, `userName`, `email`, `dob`, `gender`, `role_id`) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Create)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, Date.valueOf(user.getDob()) );
            preparedStatement.setString(6, String.valueOf(user.getGender()));
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public User findByID(int id) {
        var SELECT_BY_ID = "SELECT u.*, r.name role_name " +
                "FROM users u JOIN roles r on " +
                "r.id = u.Role_id " +
                "WHERE u.id = ? and u.deleted='0'";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getUserByResultSet(rs);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void update(User user){
        String Update = "UPDATE `user`.`users` SET `firstName` = ?, `lastName` = ?, `userName` = ?, `email` = ?, `dob` = ?, `gender` =?, `role_id` = ? " +
                " WHERE (`id` = ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Update)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, Date.valueOf(user.getDob()) );
            preparedStatement.setString(6, String.valueOf(user.getGender()));
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void delete(int id){
        String Delete = "UPDATE `user`.`users` SET `deleted` = '1'" +
                "WHERE (`id` = ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Delete)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void restore(int id){
        String restore = "UPDATE `user`.`users` SET `deleted` = '0' " +
                "WHERE (`id` = ?);";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(restore)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    private User getUserByResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setUserName(rs.getString("userName"));
        user.setEmail(rs.getString("email"));
        user.setDob(rs.getDate("dob").toLocalDate());
        user.setGender(EGender.valueOf(rs.getString("gender")));
//                 EGender gender;
//                 try {
//                     gender = EGender.valueOf(genderValue);
//                 } catch (IllegalArgumentException e) {
//                     // Giá trị đọc từ cột không khớp với bất kỳ giá trị enum nào
//                     // Xử lý ngoại lệ tại đây hoặc cung cấp giá trị mặc định
//                     gender = EGender.UNKNOWN;
//                 }
//                 user.seteGender(gender);
        user.setRole(new Role(rs.getInt("role_id"),rs.getString("role_name")));
        return user;
    }

}
