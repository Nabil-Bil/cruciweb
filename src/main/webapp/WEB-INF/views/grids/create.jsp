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
<div class="container">
    <div class="grid-display">
        <h2>Prévisualisation de la Grille</h2>
        <span class="message">
          Cliquez avec le bouton droit sur une case pour la noircir. Cliquez à
          nouveau avec le bouton droit pour la remettre à la normale.
        </span>
        <div class="grid-wrapper">
            <div class="column-indices" id="column-indices"></div>
            <div class="grid-container">
                <div class="row-indices" id="row-indices"></div>
                <div class="grid" id="grid"></div>
            </div>
        </div>
    </div>
    <div class="grid-settings">
        <h2>Paramètres de la Grille</h2>
        <div class="form-group">
            <label for="grid-name">Nom de la Grille :</label>
            <input type="text" id="grid-name" required/>
        </div>
        <div class="form-group">
            <label for="grid-width">Largeur (Colonnes) :</label>
            <input type="number" id="grid-width" value="6" min="6" max="26"/>
        </div>
        <div class="form-group">
            <label for="grid-height">Hauteur (Lignes) :</label>
            <input type="number" id="grid-height" value="6" min="6" max="26"/>
        </div>
        <div class="form-group">
            <label for="grid-difficulty">Difficulté :</label>
            <select id="grid-difficulty">
                <option value="easy">Facile</option>
                <option value="medium">Moyen</option>
                <option value="hard">Difficile</option>
            </select>
        </div>
        <div class="form-group-buttons">
            <button id="reset-button" class="button">Réinitialiser</button>
            <button id="save-button" class="button">Créer</button>
        </div>
    </div>
</div>
</body>
</html>