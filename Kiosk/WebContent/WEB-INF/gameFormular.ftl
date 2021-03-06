<!DOCTYPE html>
<html>
<head>
<title>Spiel hinzufügen</title>
<#include "cssBindings.ftl">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jodit/3.1.39/jodit.min.css">
</head>

<body>
	<#include "navbar.ftl">
	<main role="main" class="container mt-5">
		<#if loggedin?exists && loggedin==true>
		<div class="row mt-4">
		<form class="col-md-12" action="save_game" method="post" accept-charset="UTF-8">
			<div class="page-header">
				<h1>Spieleformular</h1>
			</div>
			
			<!-- verstecktes Feld für ID -->
				<#if game?has_content && game.gameID?has_content>
					<div class="hidden form-group row" >
						<label for="gameID" class="col-sm-2 col-form-label">ID</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="gameID" name="gameID" value="${game.gameID}">
						</div>
					</div>
				</#if>
			<!-- verstecktes Feld für steamID -->
				<#if game?has_content && game.steamID?has_content>
					<div class="hidden form-group row" >
						<label for="SteamID" class="col-sm-2 col-form-label">steamID</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="steamID" name="steamID" value="${game.steamID}">
						</div>
					</div>
				</#if>
			<!-- verstecktes Feld für oculusID -->
				<#if game?has_content && game.oculusID?has_content>
					<div class="hidden form-group row" >
						<label for="gameID" class="col-sm-2 col-form-label">oculusID</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="oculusID" name="oculusID" value="${game.oculusID}">
						</div>
					</div>
				</#if>
	<!-- Feld für Titel -->
					<div class="form-group row" >
						<label for="gameTitle" class="col-sm-2 col-form-label">Titel</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="gameTitle" name="gameTitle" placeholder="Titel eingeben" <#if game?exists && game.name?has_content>value="${game.name}"</#if> required>
						</div>
					</div>
	<!-- Feld für Dateipfad -->
					<div class="form-group row" >
						<label for="gamePath" class="col-sm-2 col-form-label">Dateipfad</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="gamePath" name="gamePath" placeholder="Pfad eingeben" <#if game?exists && game.path?has_content>value="${game.path}"</#if> required>
						</div>
						<div class="col">
							<a id="pathTest" class="hidden" href="#">
								<button type="button" class="btn btn-primary"><div class=DE>Pfad testen</div>
								<div class=EN>Test Path</div></button>
							</a>
						</div>
					</div>
	<!-- Feld für Deutsche Beschreibung -->
					<div class="form-group row" >
						<label for="germanDescription" class="col-sm-2 col-form-label">Deutsche Beschreibung</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="germanDescription" name="germanDescription" aria-describedby="germanHelp" rows="3"><#if game?exists>${game.germanDescription!}</#if></textarea>
						</div>
					</div>
	<!-- Feld für Englische Beschreibung -->
					<div class="form-group row" >
						<label for="englishDescription" class="col-sm-2 col-form-label">Englische Beschreibung</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="englishDescription" name="englishDescription" aria-describedby="englishHelp" rows="3"><#if game?has_content>${game.englishDescription!}</#if></textarea>
						</div>
					</div>		
	<!-- Feld für ThumbnailLink(Link zum Vorschaubild)-->
				<#if game?has_content && game.thumbnailLink?has_content>
				<div class="form-group row">
				<label for="thumbnailLink" class="col-sm-2 col-form-label">Thumbnaillink</label>
					<div class="custom-file col-sm-10" id="customFile">
							<input type="text" class="form-control" id="thumbnailLink" name="thumbnailLink" value="${game.thumbnailLink}">
					</div>
				</div>
				<#else>
				<div class="form-group row">
				<label for="thumbnailLink" class="col-sm-2 col-form-label">Thumbnaillink</label>
					<div class="custom-file col-sm-10" id="customFile">
							<input type="text" class="form-control" id="thumbnailLink" name="thumbnailLink">
					</div>
				</div>
				</#if>
	<!-- Feld für ScreenshotLink -->
				<#if game?has_content && game.screenshotLink?has_content>
				<div class="form-group row">
				<label for="screenshotLink" class="col-sm-2 col-form-label">Screenshot-Link</label>
					<div class="custom-file col-sm-10" id="customFile">
							<input type="text" class="form-control" id="screenshotLink" name="screenshotLink" value="${game.screenshotLink}">
					</div>
				</div>
				<#else>
				<div class="form-group row">
				<label for="screenshotLink" class="col-sm-2 col-form-label">Screenshot-Link</label>
					<div class="custom-file col-sm-10" id="customFile">
							<input type="text" class="form-control" id="screenshotLink" name="screenshotLink">
					</div>
				</div>
				</#if>
	<!-- Button zum Abschicken -->
			<div class="form-group row" id="buttons">
				<div class="col-sm-2">
				</div>
				<div class="col-sm-10">
					<button id="submitButton" type ="submit" class="btn btn-primary" role="button">&gt;&gt;</button>
				</div>
			</div>
		</form>
		</div>
		<#else>
			Nicht eingeloggt.
		</#if>
	</main>				
		<#include "jsBindings.ftl">
	
	<!-- HTML-Editor für Beschreibungsfelder-->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jodit/3.1.39/jodit.min.js"></script>
	<script>
	    $(document).ready(function() {
	           var editor = new Jodit('#germanDescription');
	           var editoreng = new Jodit('#englishDescription');
	    });
	    </script>
	    
	<!-- Script zum Testen des angegebenen Spielpfades -->
	<script>
		$('#gamePath').on('input', function(e){
			var path = $('#gamePath').val();
			var startlink = "kioskstart://" + path;
			$('#pathTest').attr("href", startlink);
			$('#pathTest').removeClass("hidden");
		});
	</script>
	</body>
</html>
