<%@ page import="com.univ.model.entity.Grid" %>
<%@ page import="com.univ.util.DateFormatter" %>
<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.util.List" %>
<%@ page import="com.univ.model.entity.Clue" %>
<%@ page import="com.univ.util.Routes" %>
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
        <div class="clues-container">
            <div class="horizontal-clues">
                <h2>Horizontales</h2>
                <ul>
                    <%
                        List<Clue> horizontalClues = grid.getHorizontalClues();
                        for (int i = 0; i < horizontalClues.size(); i++) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("<li class='clue-item'>");
                            sb.append(i + 1);
                            sb.append(" - ");
                            sb.append(horizontalClues.get(i).getValue());
                            sb.append("</li>");
                            out.println(sb.toString());
                        }
                    %>
                </ul>
            </div>
            <div class="vertical-clues">
                <h2>Verticales</h2>
                <ul>
                    <%
                        List<Clue> verticalClues = grid.getVerticalClues();
                        for (int i = 0; i < verticalClues.size(); i++) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("<li class='clue-item'>");

                            sb.append(Character.toChars(65 + i)[0]);
                            sb.append(" - ");
                            sb.append(verticalClues.get(i).getValue());
                            sb.append("</li>");
                            out.println(sb.toString());
                        }
                    %>
                </ul>
            </div>
        </div>


    </div>
    <div class="action-container">
        <%
            SessionManager sessionManager = new SessionManager(request.getSession());
            String anonymousGameRoute = request.getContextPath().concat("/anonymous-game/grid/").concat(grid.getId().toString());
            String loggedInGameRoute = request.getContextPath().concat("/grid/").concat(grid.getId().toString());
            boolean isLoggedInUser = sessionManager.isLoggedIn();
        %>
        <form action="<%=isLoggedInUser ? loggedInGameRoute : anonymousGameRoute%>"
              method="<%=isLoggedInUser?"post":"get"%>">
            <%if (isLoggedInUser) {%>
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <%}%>
            <button class="play-button" type="submit">Jouer</button>
        </form>
        <%
            if (isLoggedInUser) {
                if (sessionManager.isAdmin() || ((UUID) sessionManager.getLoggedInUserId()).equals(grid.getCreatedBy().getId())) {%>
        <form action="<%=loggedInGameRoute%>" method="post">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <button class="delete-button" type="submit">Supprimer</button>
        </form>
        <%
                }
            }
        %>


    </div>
</div>
</body>
</html>