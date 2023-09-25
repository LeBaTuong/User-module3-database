package com.example.ursers.model;

import com.example.ursers.model.enumration.EGender;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private LocalDate dob;
    private EGender gender;
    private Role role;


    public User() {
        this.role=new Role();
    }

    public User(int id, String firstName, String lastName, String userName, String email, LocalDate dob, EGender gender, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
    }

    public User(String firstName, String lastName, String userName, String email, LocalDate dob, EGender gender, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }
}
