<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My First Servlet</title>
</head>
<body>
<h2>Welcome to My First Servlet</h2>
<p><a href="<%= request.getContextPath() %>/myfirst">Test the first servlet</a></p>
<p><a href="<%= request.getContextPath() %>/login.html">Open the predefined-user login</a></p>
</body>
</html>
