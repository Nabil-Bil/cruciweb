<%@ page import="java.util.ArrayList" %>
<%@ page import="com.univ.model.entity.Game" %>
<%@ page import="java.util.List" %>
<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="com.univ.util.DateFormatter" %>
<%
    Object gameListAttr = request.getAttribute("gameList");
    List<Game> gameList;

    if (!(gameListAttr instanceof List)) {
        gameList = new ArrayList<>();
    } else {
        gameList = (List<Game>) gameListAttr;
    }
    int numberOfPages = (int) request.getAttribute("numberOfPages");
    int currentPage = (int) request.getAttribute("page");
%>
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/games.css">
    <script src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
    <title>Cruciweb - Games</title>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="container">
    <div class="container-header">
        <p>Mes parties</p>
    </div>
    <div class="grid-list-container">
        <%if (gameList.isEmpty()) {%>
        <p style="font-size: 20px;">Aucune Parties Trouvé</p>
        <%
        } else {
            for (Game game : gameList) {
        %>
        <div
                class="card">
            <img
                    src="${pageContext.request.contextPath}/resources/assets/images/crossword.png"
                    alt="Crossword placeholder"
                    class="card-header"
            />
            <div class="card-content">
                <h2 class="title"><%=game.getGrid().getName()%>
                </h2>
                <p class="dimension">Dimension : <%=game.getGrid().getDimensions().toString()%>
                </p>
            </div>
            <div class="card-footer">
                <p>Crée par : <%=game.getGrid().getCreatedBy().getUsername()%>
                </p>
                <p>Dernière Modification <%=DateFormatter.isToday(game.getUpdatedAt()) ? "à" : "le"%>
                    : <%=DateFormatter.formatDate(game.getUpdatedAt())%>
                </p>
                <div class="card-footer-buttons">
                    <%
                        String gameURI = request.getContextPath().concat("/game/").concat(game.getId().toString());
                    %>
                    <a href="<%=gameURI%>" class="button success">Jouer</a>
                    <form action="<%=gameURI%>"
                          method="post">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <button type="submit" class="button danger">Supprimer</button>
                    </form>
                </div>
            </div>
        </div>
        <%
                }
            }%>
    </div>
    <div class="pagination">
        <%
            int visiblePages = 3;
            int startPage = Math.max(1, currentPage - visiblePages);
            int endPage = Math.min(numberOfPages, currentPage + visiblePages);
        %>

        <% if (currentPage > visiblePages + 1) { %>
        <a href="javascript:void(0);" onclick="updateParams('page', 1)" class="page-link">1</a>
        <% if (startPage > 2) { %>
        <span class="ellipsis">...</span>
        <% } %>
        <% } %>

        <%
            for (int i = startPage; i <= endPage; i++) {
        %>
        <a href="javascript:void(0);" onclick="updateParams('page', <%=i%>)"
           class="page-link transition-all duration-300 <%=currentPage == i ? "active-link" : ""%>">
            <%=i%>
        </a>
        <%
            }
        %>

        <% if (currentPage < numberOfPages - visiblePages) { %>
        <% if (endPage < numberOfPages - 1) { %>
        <span class="ellipsis">...</span>
        <% } %>
        <a href="javascript:void(0);" onclick="updateParams('page', <%=numberOfPages%>)"
           class="page-link"><%=numberOfPages%>
        </a>
        <% } %>
    </div>
</div>
</body>
</html>