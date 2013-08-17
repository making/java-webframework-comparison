<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Not Found Error</title>
<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="alert alert-error">
        <p>
            <%=exception.getMessage()%>
        </p>
    </div>
</body>
</html>