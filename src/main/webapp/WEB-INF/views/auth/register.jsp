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
    <form action="<%=request.getContextPath().concat(Routes.REGISTER_ROUTE)%>" method="post" id="auth-form"
          class="card">
        <h1>Inscription</h1>
        <span class="${not empty username_error ? 'error' : 'hidden'}">${username_error}</span>
        <label for="username">Nom d'utilisateur</label>
        <input type="text" name="username" id="username" required
               class="${not empty username_error ? 'error-input' : ''}" value="${username}"/>
        <span class="${not empty password_error ? 'error' : 'hidden'}">${password_error}</span>
        <label for="password">Mot de passe</label>
        <input type="password" name="password" id="password" required
               class="${not empty password_error or not empty password_confirmation_error ? 'error-input' : ''}"/>
        <span class="${not empty password_confirmation_error ? 'error' : 'hidden'}">${password_confirmation_error}</span>
        <label for="confirm_password">Confirmer mot de passe</label>
        <input type="password" name="confirm_password" id="confirm_password" required
               class="${not empty password_confirmation_error ? 'error-input' : ''}"/>

        <input type="hidden" name="csrfToken" value="${csrfToken}"/>

        <button type="submit" class="button">S'inscrire</button>
        <p style="align-self: center; margin-top: 25px">
            Vous avez déja un compte? <a href="login">Connectez-vous</a>
        </p>
    </form>
</div>
</body>

</html>