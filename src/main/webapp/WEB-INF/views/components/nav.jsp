<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
  SessionManager sessionManager=new SessionManager(request.getSession(false));
  boolean isLoggedIn = sessionManager.isLoggedIn();
%>
<nav id="topbar">
  <a href="${pageContext.request.contextPath}/" id="logo">
    <img src="${pageContext.request.contextPath}/resources/assets/images/logo.png" alt="Logo" id="logo-image" />
    <h1>CruciWeb</h1>
  </a>
  <ul>
    <li><a href="${pageContext.request.contextPath}/">Acceuil</a></li>
    <% if(isLoggedIn) { %>
    <li>
      <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Se deconnecter">
        <input type="hidden" name="csrfToken" value="${csrfToken}" />
      </form>
    </li>
    <% } else{ %>
    <li><a href="${pageContext.request.contextPath}/login">Se connecter</a></li>
    <li><a href="${pageContext.request.contextPath}/register">S'inscrire</a></li>
    <% } %>
  </ul>
</nav>