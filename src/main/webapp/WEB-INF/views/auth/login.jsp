<%@ page import="com.univ.util.Routes" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/auth.css">
    <title>CruciWeb - Register</title>
</head>

<body>
<jsp:include page="../components/nav.jsp"/>
<div class="center-content">
    <form action="<%=request.getContextPath().concat(Routes.LOGIN_ROUTE)%>" method="post" id="auth-form" class="card">
        <h1>Connexion</h1>
        <span class="${not empty username_error ? 'error' : 'hidden'}">${username_error}</span>
        <label for="username">Nom d'utilsiateur</label>
        <input type="text" name="username" id="username" required
               class="${not empty username_error ? 'error-input' : ''}" value="${username}"/>
        <span class="${not empty password_error ? 'error' : 'hidden'}">${password_error}</span>
        <label for="password">Mot de passe</label>
        <input type="password" name="password" id="password" required
               class="${not empty password_error ? 'error-input' : ''}"/>

        <input type="hidden" name="csrfToken" value="${csrfToken}"/>

        <button type="submit" class="button">Se connecter</button>
        <p style="align-self: center; margin-top: 25px">
            Vous n'avez pas de compte? <a href="register">Rejoignez nous!</a>
        </p>
    </form>
</div>
</body>

</html>