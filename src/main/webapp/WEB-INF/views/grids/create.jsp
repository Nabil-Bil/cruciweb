<%@ page import="com.univ.util.Routes" %>
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/createGrid.css">
    <script src="${pageContext.request.contextPath}/resources/js/createGrid.js" defer></script>
    <title>Cruciweb - Grid</title>
</head>
<body>
<jsp:include page="../components/nav.jsp"/>
<form class="container" id="grid-form" method="post"
      action="<%=request.getContextPath().concat(Routes.CREATE_GRID_ROUTE)%>">
    <div class="grid-display">
        <h2>Prévisualisation de la Grille</h2>
        <span class="message">
          Cliquez avec le bouton droit sur une case pour la noircir. Cliquez à
          nouveau avec le bouton droit pour la remettre à la normale.
        </span>
        <span class="message">
          Dans chaque ligne/colonne, séparez les indices par des tirets (-).
        </span>
        <div class="crossword-wrapper">
            <div class="grid-wrapper">
                <div class="column-indices" id="column-indices"></div>
                <div class="grid-container">
                    <div class="row-indices" id="row-indices"></div>
                    <div class="grid" id="grid"></div>
                </div>
            </div>
            <div class="clues-wrapper">
                <div class="clues">
                    <h3>Indices Horizontaux</h3>
                    <div class="clues-list" id="horizontal-clues"></div>
                </div>
                <div class="clues">
                    <h3>Indices Verticaux</h3>
                    <div class="clues-list" id="vertical-clues"></div>
                </div>
            </div>
        </div>

    </div>
    <div class="grid-settings">
        <h2>Paramètres de la Grille</h2>
        <div class="form-group">
            <label for="grid-name">Nom de la Grille :</label>
            <input type="text" id="grid-name" name="name" required/>
        </div>
        <div class="form-group">
            <label for="grid-width">Largeur (Colonnes) :</label>
            <input type="number" name="width" id="grid-width" value="6" min="6" max="26"/>
        </div>
        <div class="form-group">
            <label for="grid-height">Hauteur (Lignes) :</label>
            <input type="number" name="height" id="grid-height" value="6" min="6" max="26"/>
        </div>
        <div class="form-group">
            <label for="grid-difficulty">Difficulté :</label>
            <select id="grid-difficulty" name="difficulty">
                <option value="easy">Facile</option>
                <option value="medium">Moyen</option>
                <option value="hard">Difficile</option>
            </select>
        </div>
        <div class="form-group-buttons">
            <input type="hidden" name="csrfToken" value="${csrfToken}"/>
            <input type="hidden" id="gridMatrixData" name="gridMatrixData">
            <button id="reset-button" class="button">Réinitialiser</button>
            <button id="save-button" type="submit" class="button">Créer</button>
        </div>
    </div>
</form>
</body>
</html>