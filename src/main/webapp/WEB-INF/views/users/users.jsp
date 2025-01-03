<%@ page import="java.util.List" %>
<%@ page import="com.univ.model.entity.User" %>
<%@ page import="com.univ.enums.Role" %>
<%@ page import="com.univ.util.Routes" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cruciweb - Utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/users.css"/>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="container">
    <div class="header">
        <h1>Utilisateurs</h1>
        <a href="<%=request.getContextPath().concat(Routes.CREATE_USER_ROUTE)%>" class="add-button">Ajouter</a>
    </div>
    <table>
        <thead>
        <tr>
            <th>Nom d'utilisateur</th>
            <th>Rôle</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (request.getAttribute("users") != null && request.getAttribute("users") instanceof List) {
                List<User> users = (List<User>) request.getAttribute("users");
                for (User user : users) {
                    if (user.getRole() != Role.ADMIN) {
        %>
        <tr>
            <td><%=user.getUsername()%>
            </td>
            <td><%=user.getRole().name().toLowerCase()%>
            </td>
            <td><%= sdf.format(user.getCreatedAt())%>
            </td>
            <td class="action-buttons">
                
                <form action="<%=request.getContextPath().concat(Routes.USERS_ROUTE)%>" method="POST"
                      onsubmit="return confirm('Voulez-vous vraiment supprimer cet utilisateur ?');">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <input type="hidden" name="userId" value="<%=user.getId()%>"/>
                    <button type="submit" class="delete">Supprimer</button>
                </form>
            </td>
        </tr>
        <%
                    }
                }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>