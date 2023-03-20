<%-- 
    Document   : users
    Created on : 12-Mar-2023, 11:16:34 PM
    Author     : allen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
        <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <h1>Manage Users</h1>
        
        <c:if test="${users.size() gt 0}">
            <table id="table">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.role.roleName}</td>
                        <td><a href="user?action=edit&amp;userEmail=${user.email.replace("+", "%2B")}">Edit</a></td>
                        <td><a href="user?action=delete&amp;userEmail=${user.email.replace("+", "%2B")}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        
        <c:if test="${users.size() eq 0}">
            <p>No users found. Please add a user.</p>
        </c:if>
        
        <c:if test="${edit eq false}">
            <h2>Add User</h2>
            <form action="user" method="post">
                Email: <input type="email" name="email" ><br />
                First Name: <input type="text" name="fname" ><br />
                Last Name: <input type="text" name="lname" ><br />
                Password: <input type="password" name="password" ><br />
                Role: <select name="role">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select><br />

                <input type="hidden" name="action" value="add">
                <input type="submit" value="Add User">
            </form>
            <p>${error}</p>
        </c:if>
            
        <c:if test="${edit eq true}">
            <h2>Edit User</h2>
            <form action="user" method="post">
                Email: ${editUser.email}<br />
                <input type="hidden" name="email" value="${editUser.email}">
                First Name: <input type="text" name="fname"  value="${editUser.firstName}"><br />
                Last Name: <input type="text" name="lname"  value="${editUser.lastName}"><br />
                Password: <input type="password" name="password" ><br />
                Role: <select name="role">
                    <!--<option value="${editUser.role.roleId}">${editUser.role.roleName}</option>-->
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}" ${role.roleId eq editUser.role.roleId ? 'selected' : ''} >${role.roleName}</option>
                    </c:forEach>
                </select><br />

                <input type="hidden" name="action" value="update">
                <input type="hidden" name="userEmail" value="${editUser.email}">
                <input type="submit" value="Update User">
                <button><a href="user" id="button">Cancel</a></button> <!-- Has style so it doesn't look like a hyperlink -->
            </form>
            <p>${error}</p>
        </c:if>
        
    </body>
</html>