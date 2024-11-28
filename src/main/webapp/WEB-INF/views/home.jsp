<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/index.css">
  <title>CruciWeb</title>
</head>
<body>
  <jsp:include page="components/nav.jsp" />
    <section class="hero">
      <div class="hero-content">
        <h1>Bienvenue sur <span>Cruciweb</span></h1>
        <p>Explorez le monde des mots croisés et testez votre esprit logique.</p>
        <a href="#games" class="cta-button">Commencez à jouer</a>
      </div>
    </section>
    
    <section class="features" id="games">
      <h2>Pourquoi choisir Cruciweb ?</h2>
      <div class="features-grid">
        <div class="feature">
          <h3>Des puzzles variés</h3>
          <p>Des mots croisés pour tous les niveaux, du débutant à l'expert.</p>
        </div>
        <div class="feature">
          <h3>Minimaliste</h3>
          <p>Profitez d'une interface claire et sans distractions.</p>
        </div>
        <div class="feature">
          <h3>Gratuit</h3>
          <p>Jouez gratuitement, sans frais cachés.</p>
        </div>
      </div>
    </section>
    
    <footer class="footer">
      <p>&copy; Cruciweb 2024. Tous droits réservés.</p>
    </footer>
</body>
</html>