<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Store Database</title>
    <link rel="stylesheet" type="text/css" href="../css/mdb.css">
</head>
<body>
<%@include file="header.jspf" %>
<section>
    <table class="movies-table">
        <thead>
        <tr>
            <th>Students of ${group.title}</th>
            <c:if test="${!empty user}">
                <th>Actions</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:if test="${students == null || students.isEmpty()}">
            <tr>
                <td colspan="100%">No students in this group</td>
            </tr>
        </c:if>
        <c:forEach var="student" items="${students}">
            <tr>
                <c:if test="${!empty user && student.studentId == param.editstudentId}">
                    <td colspan="100%">
                        <form style="width: 100%;" action="editStudent" method="POST">
                            <div class="username">Edit student</div>
                            <c:if test="${group != null}">
                                <input type="hidden" name="groupId" value="${group.groupId}"/>
                            </c:if>
                            <input type="hidden" name="studentId" value="${student.studentId}"/>
                            <textarea class="boxsizingBorder" name="name">${student.name}</textarea>
                            <input type="submit" value="Save"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${empty user || student.studentId != param.editstudentId}">
                    <td>
                        <a href="student?studentId=${student.studentId}">${student.name}</a>
                    </td>
                    <c:if test="${!empty user}">
                        <td>
                            <form method="GET" style="display: contents">
                                <c:if test="${group != null}">
                                    <input type="hidden" name="groupId" value="${group.groupId}"/>
                                </c:if>
                                <input type="hidden" name="editstudentId" value="${student.studentId}"/>
                                <button type="submit">Edit</button>
                            </form>
                            <form action="deleteStudent" method="POST" style="display: contents">
                                <input type="hidden" name="studentId" value="${student.studentId}"/>
                                <input type="hidden" name="groupId" value="${group.groupId}"/>
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </c:if>
                </c:if>
            </tr>
        </c:forEach>
        <c:if test="${!empty user}">
            <tr>
                <td colspan="100%">
                    <form style="width: 50%;  margin-top: 10px; margin-bottom: 10px" action="createStudent"
                          method="POST">
                        <div class="username">Create student</div>
                        <input type="hidden" name="groupId" value="${group.groupId}"/>
                        <textarea class="boxsizingBorder" name="name" placeholder="Enter name..."></textarea>
                        <input type="submit" value="Create"/>
                    </form>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</section>
<br>
<%@include file="footer.jspf" %>
</body>
</html>
