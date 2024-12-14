<%@ page import="com.univ.model.Grid" %>
<%@ page import="com.univ.util.DateFormatter" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="java.util.UUID" %>
<%
    Grid grid = (Grid) request.getAttribute("grid");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grid.css">

    <title>Cruciweb - Grid details</title>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="grid-detail-container">
    <header class="grid-header">
        <div class="grid-info">
            <h1 class="grid-name"><%=grid.getName()%>
            </h1>
            <p class="created-by">Créée par : <span><%=grid.getCreatedBy().getUsername()%></span></p>
            <p class="created-by">Difficulté <span><%=grid.getDifficulty().getDisplayName()%></span></p>
        </div>
        <p class="created-date">Créée <%=DateFormatter.isToday(grid.getCreatedAt()) ? "à " : "le " %> :
            <span><%=DateFormatter.formatDate(grid.getCreatedAt())%></span></p>
    </header>

    <div class="grid-image-container">
        <img src="${pageContext.request.contextPath}/resources/assets/images/crossword.png" alt="Grille"
             class="grid-image">
    </div>

    <div class="action-container">
        <button class="play-button" onclick="window.location.href='gamePage.html'">Jouer</button>
        <%
            SessionManager sessionManager = new SessionManager(session);
            if (sessionManager.isAdmin() || ((UUID) sessionManager.getLoggedInUserId()).equals(grid.getCreatedBy().getId())) {%>
        <button class="delete-button" onclick="window.location.href='gamePage.html'"> Supprimer</button>
        <%
            }
        %>

    </div>
</div>
</body>
</html>