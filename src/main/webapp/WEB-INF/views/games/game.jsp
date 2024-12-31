<%@ page import="com.univ.util.Routes" %>
<%@ page import="com.univ.model.entity.Game" %>
<%@ page import="com.univ.model.entity.Clue" %>
<%@ page import="java.util.List" %>
<%@ page import="com.univ.util.SessionManager" %>
<%
    Object gameObject = request.getAttribute("game");
    if (!(gameObject instanceof Game)) {
        response.sendRedirect(request.getContextPath() + Routes.HOME_ROUTE);
    }
    Game game = (Game) gameObject;
    SessionManager sessionManager = new SessionManager(request.getSession());
%>
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/game.css">
    <script src="${pageContext.request.contextPath}/resources/js/game.js" defer></script>
    <title>Cruciweb - Partie</title>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<div class="container">
    <div class="game-status">
        <span class="${not empty error ? 'error' : 'hidden'}">${error}</span>
        <span class="${not empty success ? 'code-success' : 'hidden'}">${success}</span>
    </div>
    <div class="crossword-wrapper">
        <div class="grid-wrapper">
            <div class="column-indices" id="column-indices">
                <%
                    for (int i = 0; i < game.getGrid().getDimensions().getWidth(); i++) {

                        String sb = "<span>" +
                                Character.toChars(65 + i)[0] +
                                "</span>";
                        out.println(sb);
                    }
                %>
            </div>
            <div class="grid-container">
                <div class="row-indices" id="row-indices">
                    <%
                        for (int i = 0; i < game.getGrid().getDimensions().getHeight(); i++) {
                            String sb = "<span>" +
                                    (i + 1) +
                                    "</span>";
                            out.println(sb);
                        }
                    %>
                </div>
                <div class="grid" id="grid"
                     style="grid-template-columns: repeat(<%=game.getGrid().getDimensions().getWidth()%>, 1fr)">

                    <%
                        char[][] gameMatrix = game.getMatrixRepresentation();
                        for (int row = 0; row < gameMatrix.length; row++) {
                            for (int col = 0; col < gameMatrix[row].length; col++) {
                                if (gameMatrix[row][col] == '*') {
                                    out.print(String.format("<div data-row='%d' data-col='%d' class='grid-cell' style='background-color:white;' aria-valuetext='%s'></div>", row, col, gameMatrix[row][col]));
                                } else {
                                    out.print(String.format("<div data-row='%d' data-col='%d' class='grid-cell' aria-valuetext='%s'>%s</div>", row, col, gameMatrix[row][col], gameMatrix[row][col]));
                                }
                            }
                        }
                    %>
                </div>
            </div>
        </div>
        <div class="clues-wrapper">
            <div class="horizontal-clues">
                <h2>Horizontales</h2>
                <ul>
                    <%
                        List<Clue> horizontalClues = game.getGrid().getHorizontalClues();
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
                        List<Clue> verticalClues = game.getGrid().getVerticalClues();
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
    <div class="buttons-wrapper">

        <form method="post"
              action="<%=!sessionManager.isLoggedIn()?request.getContextPath().concat("/anonymous-game/grid/").concat(game.getGrid().getId().toString()):request.getContextPath().concat("/game/").concat(game.getId().toString())%>"
              id="form-validation">
            <input type="hidden" name="csrfToken" value="${csrfToken}"/>
            <input type="hidden" id="gridMatrixData" name="gridMatrixData">
            <button type="submit" class="button"><%=sessionManager.isLoggedIn() ? "Sauvgarder et Valider" : "Valider"%>
            </button>
        </form>

    </div>
</div>
</body>
</html>