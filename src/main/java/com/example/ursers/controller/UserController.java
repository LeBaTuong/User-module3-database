package com.example.ursers.controller;

import com.example.ursers.model.Role;
import com.example.ursers.model.User;
import com.example.ursers.model.enumration.EGender;
import com.example.ursers.service.RoleService;
import com.example.ursers.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet (name = "UserController", urlPatterns = "/user")
public class UserController extends HttpServlet {
    private UserService userService;
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req,resp);
                break;
            case "edit":
                showEdit(req,resp);
                break;
            case "restore":
                showRestore(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, true, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", userService.findUserByID(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("genders", EGender.values());
        req.setAttribute("roles", roleService.getRoles());
        req.getRequestDispatcher("create.jsp").forward(req,resp);

    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, false, resp);
    }
    private void showTable(HttpServletRequest req, boolean isShowRestore, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        req.setAttribute("page", userService.getUsers(Integer.parseInt(pageString), isShowRestore, req.getParameter("search")));
        req.setAttribute("message", req.getParameter("message"));
        req.setAttribute("isShowRestore", isShowRestore);
        req.setAttribute("search", req.getParameter("search"));
        req.getRequestDispatcher("user.jsp").forward(req, resp);
    }
    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", new User());
        req.setAttribute("genders", EGender.values());
        req.setAttribute("roles", roleService.getRoles());
        req.getRequestDispatcher("create.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                create(req,resp);
                break;
            case "edit":
                updateProduct(req,resp);
                break;
            case "restore":
                restoreUser(req,resp);
                break;
//            case "delte":
//                delete(req,resp);
//                break;
        }
    }

    private void restoreUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.restore(req.getParameterValues("restore"));
        resp.sendRedirect("/user?message=Restore");

    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Deleted");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.createUser(getUserByRequest(req));
        resp.sendRedirect("/user?message=Created");
    }
    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.update(getUserByRequest(req), Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Edited");
    }
    private User getUserByRequest(HttpServletRequest req) throws IOException{
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        LocalDate dob = LocalDate.parse(String.valueOf(Date.valueOf(req.getParameter("dob"))));
        EGender gender = EGender.valueOf(req.getParameter("gender"));
        String idRole = req.getParameter("role");
        Role role = roleService.getRole(Integer.parseInt(idRole));
        return new User(firstName,lastName,userName,email,dob,gender,role);
    }
}


