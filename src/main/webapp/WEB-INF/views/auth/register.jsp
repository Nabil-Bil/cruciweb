<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CruciWeb - Register</title>
</head>

<body>
  <jsp:include page="../components/nav.jsp" />
  <h1>Register</h1>
  <form action="register" method="post" id="myForm">
    <label for="username">Username</label>
    <input type="text" name="username" id="username" required>
    <label for="password">Password</label>
    <input type="password" name="password" id="password" required>
    <button type="submit" onclick="submit()">Register</button>
  </form>
  <p>Already have an account? <a href="login">Login</a></p>
</body>

</html>