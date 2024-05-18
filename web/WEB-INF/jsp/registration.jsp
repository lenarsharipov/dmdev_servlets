<%--
  Created by IntelliJ IDEA.
  User: Lenar
  Date: 08.05.2024
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
        <label for="name">Name:
            <input type="text" name="name" id="name" required>
        </label><br>

        <label for="birthday">Birthday:
            <input type="date" name="birthday" id="birthday" required>
        </label><br>

        <label for="imageId">Image:
            <input type="file" name="image" id="imageId" required>
        </label><br>

        <label for="email">Email:
            <input type="email" name="email" id="email" required>
        </label><br>

        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label><br>

        <select name="role" id="role" required>
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
         </select><br>

        <c:forEach var="gender" items="${requestScope.genders}">
            <input type="radio" name="gender" value="${gender}" required checked>${gender}<br>
        </c:forEach>

        <button type="submit">Send</button>

        <div>
            <c:if test="${not empty requestScope.errors}">
                <div style="color: red">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <span>${error.message}</span><br>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </form>

</body>
</html>
