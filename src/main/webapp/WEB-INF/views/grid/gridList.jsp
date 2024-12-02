<%@ page import="java.util.List" %>
<%@ page import="com.univ.model.Grid" %>
<%@ page import="java.util.ArrayList" %>
<%
    Object gridListAttr = request.getAttribute("gridList");
    List<Grid> gridList;

    if (!(gridListAttr instanceof List)) {
        gridList = new ArrayList<>();
    } else {
        gridList = (List<Grid>) gridListAttr;
    }
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
    <h1>Liste des Grilles</h1>
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
            </div>
        </div>
        <%
                }
            }%>
    </div>
</div>


<script src="${pageContext.request.contextPath}/resources/js/gridList.js"></script>
</body>
</html>