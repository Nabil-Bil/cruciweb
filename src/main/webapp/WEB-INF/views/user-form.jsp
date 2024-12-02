<%@ page import="com.univ.util.Routes" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cruciweb - Utilisateur</title>
</head>
<body>
<jsp:include page="components/nav.jsp" />
<form action="<%=request.getContextPath().concat(Routes.USERS_ROUTE)%>">
    <input type="hidden" name="_method" value="POST" />
    <input type="hidden" name="csrfToken" value="${csrfToken}" />
    <label for="username">Nom d'utilisateur</label>
    <input type="text" name="username" id="username" required />
    <label for="password">Mot de passe</label>
    <input type="password" name="password" id="password" required />
    <label for="role">Role</label>
    <select name="role" id="role">
        <option value="USER">Utilisateur</option>
        <option value="ADMIN">Administrateur</option>
    </select>
    <button type="submit">Ajouter</button>
</form>
</body>
</html>