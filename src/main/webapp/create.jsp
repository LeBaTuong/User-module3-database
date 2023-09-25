<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 9/18/2023
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="card container px-6" style="height: 100vh">

        <c:if test="${user.id == 0}">
            <h3 class="text-center">Create Product</h3>
        <form action="/user?action=create" method="post">
        </c:if>
            <c:if test="${user.id != 0}">
            <h3 class="text-center">Edit Product</h3>
            <form action="/user?action=edit&id=${user.id}" method="post">
                </c:if>
                    <input type="hidden" name="id" value="${user.id}">
            <div class="mb-3">
                <label for="firstName" class="form-label">firstName</label>
                <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}">
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">lastName</label>
                <input type="text" class="form-control" name="lastName" id="lastName" value="${user.lastName}">
            </div>
            <div class="mb-3">
                <label for="userName" class="form-label">userName</label>
                <input type="text" class="form-control" name="userName" id="userName" value="${user.userName}">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">email</label>
                <input type="text" class="form-control" name="email" id="email" value="${user.email}">
            </div>
            <div class="mb-3">
                <label for="dob" class="form-label">Dob</label>
                <input type="date" class="form-control" name="dob" id="dob" value="${user.dob}">
            </div>
            <div class="mb-3">
                <label for="gender" class="form-label">Gender</label>
                <select class="form-control" name="gender" id="gender">
                    <option value="MALE" ${user.gender == 'MALE' ? 'selected' : ''}>MALE</option>
                    <option value="FEMALE" ${user.gender == 'FEMALE' ? 'selected' : ''}>FEMALE</option>
                    <option value="OTHER" ${user.gender == 'OTHER' ? 'selected' : ''}>OTHER</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">Role</label>
                <select class="form-control" name="role" id="role" >
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.id}" <c:if test="${role.id==user.role.id}">
                        selected
                        </c:if>>${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>