<%@ page import="java.util.List" %>
<%@ page import="com.univ.model.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.univ.util.DateFormatter" %>
<%@ page import="com.univ.util.SessionManager" %>
<%
    Object gridListAttr = request.getAttribute("gridList");
    List<Grid> gridList;

    if (!(gridListAttr instanceof List)) {
        gridList = new ArrayList<>();
    } else {
        gridList = (List<Grid>) gridListAttr;
    }
    SessionManager sessionManager = new SessionManager(session);
    int numberOfPages = (int) request.getAttribute("numberOfPages");
    int currentPage = (int) request.getAttribute("page");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CruciWeb - Grid List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grids.css">

</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="container">
    <div class="container-header">
        <p>Liste des Grilles</p>
        <div>
            <% if (sessionManager.isLoggedIn() && !sessionManager.isAdmin()) { %>
            <button class="button">Create</button>
            <% } %>
            <div class="button filter-button clicked-filter-button">Filtrer par
                <img src="${pageContext.request.contextPath}/resources/assets/icons/arrow_drop_down.svg" alt="icon">
                <div class="dropdown hidden">
                    <a href="${pageContext.request.contextPath}/grids?filter=creationDate">Date de création</a>
                    <a href="${pageContext.request.contextPath}/grids?filter=difficultyAsc">Difficulté Ascendente</a>
                    <a href="${pageContext.request.contextPath}/grids?filter=difficultyDesc">Difficulté Descendente</a>
                </div>

            </div>

        </div>
    </div>
    <div class="grid-list-container">
        <%if (gridList.isEmpty()) {%>
        <p style="font-size: 20px;">Aucune Grille Trouvé</p>
        <%
        } else {
            for (Grid grid : gridList) {
        %>
        <div class="card transition-all duration-300">
            <img
                    src="${pageContext.request.contextPath}/resources/assets/images/crossword.png"
                    alt="Crossword placeholder"
                    class="card-header"
            />
            <div class="card-content">
                <h2 class="title"><%=grid.getName()%>
                </h2>
                <p class="difficulty">Difficulty : <%=grid.getDifficulty().name().toLowerCase()%>
                </p>
                <p class="dimension">Dimension : <%=grid.getDimensions().toString()%>
                </p>
            </div>
            <div class="card-footer">
                <p>Crée par : <%=grid.getCreatedBy().getUsername()%>
                </p>
                <p>Crée le : <%=DateFormatter.formatDate(grid.getCreatedAt())%>
                </p>
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
        <a href="${pageContext.request.contextPath}/grids?page=1" class="page-link">1</a>
        <% if (startPage > 2) { %>
        <span class="ellipsis">...</span>
        <% } %>
        <% } %>

        <%
            for (int i = startPage; i <= endPage; i++) {
        %>
        <a href="${pageContext.request.contextPath}/grids?page=<%=i%>"
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
        <a href="${pageContext.request.contextPath}/grids?page=<%=numberOfPages%>" class="page-link"><%=numberOfPages%>
        </a>
        <% } %>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/gridList.js"></script>
</body>
</html>