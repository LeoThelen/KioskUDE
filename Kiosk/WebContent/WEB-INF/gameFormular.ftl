<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width" = device-width, initial-scale = 1">
<meta charset="utf-16">
<title>Spiel hinzufügen</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="css/my.css">
<link rel="stylesheet" href="css/myGameFormular.css">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
</head>

<body>
	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
	<a class="navbar-brand" href="main">VR Kiosk</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
	<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarsExampleDefault">
	<ul class="navbar-nav mr-auto">
	<li class="nav-item dropdown">
	<a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	&Uuml;ber
	</a>
	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
	<a class="dropdown-item" href="https://github.com/LeoThelen/KioskUDE">Projekt auf GitHub</a>
	<div class="dropdown-divider"></div>
	<a class="dropdown-item" href="about.ftl">Impressum</a>
	</div>
	</li>
	</ul>
	</div>
	</nav>
<main role="main" class="container mt-5">
	<div class="row top-buffer">
	<form class="col-md-12" action="addGame" method="post">
		<div class="page-header">
			<h1>Spiel hinzuf&uuml;gen</h1>
		</div>
			<#if game?has_content && game.gameID?has_content>
				<div class="form-group row" >
					<label for="gameID" class="col-sm-2 col-form-label">ID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="gameID" name="gameID" value="${game.gameID}" disabled>
					</div>
				</div>
			</#if>
			<#if game?has_content && game.steamID?has_content>
				<div class="form-group row" >
					<label for="SteamID" class="col-sm-2 col-form-label">steamID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="steamID" name="steamID" value="${game.steamID}">
					</div>
				</div>
			</#if>
			<#if game?has_content && game.oculusID?has_content>
				<div class="form-group row" >
					<label for="gameID" class="col-sm-2 col-form-label">oculusID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="oculusID" name="oculusID" value="${game.oculusID}" disabled>
					</div>
				</div>
			</#if>
				<div class="form-group row" >
					<label for="gameTitle" class="col-sm-2 col-form-label">Titel</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="gameTitle" name="gameTitle" placeholder="Titel eingeben" <#if game?exists && game.name?has_content>value="${game.name}"</#if> required>
					</div>
				</div>
			<#if game?has_content>
			<#else>
				<div class="form-group row" >
					<label for="gamePath" class="col-sm-2 col-form-label">Dateipfad</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="gamePath" name="gamePath" placeholder="Pfad eingeben" required>
					</div>
				</div>
			</#if>
				<div class="form-group row" >
					<label for="germanDescription" class="col-sm-2 col-form-label">Deutsche Beschreibung</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="germanDescription" name="germanDescription" aria-describedby="germanHelp" rows="3"><#if game?exists>${game.germanDescription!}</#if></textarea>
					</div>
				</div>
				<div class="form-group row" >
					<label for="englishDescription" class="col-sm-2 col-form-label">Englische Beschreibung</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="englishDescription" name="englishDescription" aria-describedby="englishHelp" rows="3"><#if game?has_content>${game.englishDescription!}</#if></textarea>
					</div>
				</div>		
			<div class="form-group row">
				<div class="col-sm-2">
				</div>
				<div class="custom-file col-sm-10" id="customFile" lang="es">
					<input type="file" class="custom-file-input" name="thumbnail" aria-describedby="fileHelp">
					<label class="custom-file-label" for="exampleInputFile">
						Thumbnail auswählen...
					</label>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-2">
				</div>
				<div class="custom-file col-sm-10" id="customFile" lang="es">
					<input type="file" class="custom-file-input" name="screenshot" aria-describedby="fileHelp">
					<label class="custom-file-label" for="exampleInputFile">
						Screenshot auswählen...
					</label>
				</div>
			</div>
		<div class="form-group row" id="buttons">
			<div class="col-sm-2">
			</div>
			<div class="col-sm-10">
				<button id="submitButton" type ="submit" class="btn btn-primary" role="button">Fertig</button>
				<button type ="cancel" class="btn btn-primary" role="button">Abbrechen</button>
			</div>
		</div>
	</form>
	</div>
</main>				
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.3.1.min.js"><\/script>')
	</script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="js/isotope.pkgd.js"></script>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/my.js"></script>
	<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.js"></script>
  <script>
    $(document).ready(function() {
    	$('#germanDescription').summernote();
    	$('#englishDescription').summernote();
    });
  </script>
</body>
</html>
