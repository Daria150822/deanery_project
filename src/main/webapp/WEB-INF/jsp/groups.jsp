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
            <th>Groups</th>
            <th>Number of students</th>
            <c:if test="${!empty user}">
                <th>Actions</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:if test="${groups == null}">
            <tr>
                <td colspan="100%">Nothing yet</td>
            </tr>
        </c:if>
        <c:forEach var="group" items="${groups}">
            <tr>
                <c:if test="${!empty user && group.groupId == param.editGroupId}">
                    <td colspan="100%">
                        <form style="width: 100%;" action="editGroup" method="POST">
                            <div class="username">Edit group</div>
                            <c:if test="${parentGroup != null}">
                                <input type="hidden" name="groupId" value="${parentGroup.groupId}"/>
                            </c:if>
                            <input type="hidden" name="groupId" value="${group.groupId}"/>
                            <textarea class="boxsizingBorder" name="text">${group.title}</textarea>
                            <input type="submit" value="Save"/>
                        </form>
                    </td>
                </c:if>
                <c:if test="${empty user || group.groupId != param.editGroupId}">
                    <td>
                        <a href="group?groupId=${group.groupId}">${group.title}</a>
                    </td>
                    <td>${group.getStudents() != null ? group.getStudents().size() : 0}</td>
                    <c:if test="${!empty user}">
                        <td>
                            <form method="GET" style="display: contents">
                                <c:if test="${parentGroup != null}">
                                    <input type="hidden" name="groupId" value="${parentGroup.groupId}"/>
                                </c:if>
                                <input type="hidden" name="editGroupId" value="${group.groupId}"/>
                                <button type="submit">Edit</button>
                            </form>
                            <form action="deleteGroup" method="POST" style="display: contents">
                                <input type="hidden" name="groupId" value="${group.groupId}"/>
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </c:if>
                </c:if>
            </tr>
        </c:forEach>
        <c:if test="${!empty user}">
            <tr style="margin-top: 30px">
                <td colspan="100%">
                    <form style="width: 50%; margin-top: 10px; margin-bottom: 10px" action="createGroup"
                          method="POST">
                        <div class="username">Create group</div>
                        <textarea class="boxsizingBorder" name="text" placeholder="Enter title..."></textarea>
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
