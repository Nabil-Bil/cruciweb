<%@ page import="java.util.List" %>
<%@ page import="com.univ.model.entity.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.univ.util.DateFormatter" %>
<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="com.univ.util.Routes" %>
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
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CruciWeb - Grid List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grids.css">
    <script src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/gridList.js" defer></script>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="container">
    <div class="container-header">
        <p>Liste des Grilles</p>
        <div>
            <% if (sessionManager.isLoggedIn() && !sessionManager.isAdmin()) { %>
            <a class="button" style="text-align: center;"
               href="<%=request.getContextPath().concat(Routes.CREATE_GRID_ROUTE)%>">Create</a>
            <% } %>


            <%
                if (sessionManager.isLoggedIn() && !sessionManager.isAdmin()) {
                    String filter = request.getParameter("filter");
                    if ("my-grids".equals(filter)) { %>
            <a href="javascript:void(0)" onclick="updateParams('filter',undefined)" class="button">Toutes les
                grilles</a>
            <% } else { %>
            <a href="javascript:void(0)" onclick="updateParams('filter','my-grids')" class="button">Mes grilles</a>
            <% }
            } %>

            <div class="button sort-button clicked-sort-button">Trier par
                <img src="${pageContext.request.contextPath}/resources/assets/icons/arrow_drop_down.svg" alt="icon">
                <div class="dropdown hidden">
                    <a href="javascript:void(0);" onclick="updateParams('sort', 'creationDate')">Date de création</a>
                    <a href="javascript:void(0);" onclick="updateParams('sort', 'difficultyAsc')">Difficulté
                        Ascendante</a>
                    <a href="javascript:void(0);" onclick="updateParams('sort', 'difficultyDesc')">Difficulté
                        Descendante</a>
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
        <a href="<%=request.getContextPath().concat("/grid/").concat(grid.getId().toString())%>"
           class="card transition-all duration-300">
            <img
                    src="${pageContext.request.contextPath}/resources/assets/images/crossword.png"
                    alt="Crossword placeholder"
                    class="card-header"
            />
            <div class="card-content">
                <h2 class="title"><%=grid.getName()%>
                </h2>
                <p class="difficulty">Difficulty : <%=grid.getDifficulty().getDisplayName()%>
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
        </a>
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