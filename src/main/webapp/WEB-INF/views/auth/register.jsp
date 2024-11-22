<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
  <title>CruciWeb - Register</title>
</head>

<body>
  <jsp:include page="../components/nav.jsp" />
  <h1>Register</h1>
  <form action="register" method="post">
    ${username_error}<br>
    <label for="username">Nom d'utilsiateur</label>
    <input type="text" name="username" id="username" required>
    ${password_error}<br>
    <label for="password">Mot de passe</label>
    <input type="password" name="password" id="password" required>
    ${password_confirmation_error}<br>
    <label for="confirm_password">Confirmer mot de passe</label>
    <input type="password" name="confirm_password" id="confirm_password" required>

    <input type="hidden" name="csrfToken" value="${csrfToken}" />

    <button type="submit">Register</button>
  </form>
  <p>Already have an account? <a href="login">Login</a></p>
</body>

</html>