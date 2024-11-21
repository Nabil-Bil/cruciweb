<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CruciWeb - Login</title>
</head>
<body>
    <h1>Login</h1>
    <form action="login" method="post">
      <label for="username">Email</label>
      <input type="text" name="email" id="email" required>
      <label for="password">Password</label>
      <input type="password" name="password" id="password" required>
      <button type="submit">Login</button>
    </form>
    <p>Don't have an account? <a href="register">Register</a></p>

</body>
</html>