<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width" = device-width, initial-scale = 1">
<meta charset="utf-8">
<title>Spiel hinzufügen</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="css/my.css">
<link rel="stylesheet" href="css/login-template.css">
<link rel="stylesheet" href="css/addNewGame.css">
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
<main role="main" class="container">
	<div class="row top-buffer">
	<form method="post">
		<div class="page-header">
			<h1>Spiel hinzuf&uuml;gen</h1>
		</div>
			<#if filterOffen == false>
			<div class="form-group">
				<label for="gameTitle">Titel</label>
				<input type="text" class="form-control" id="gameTitle" name="gameTitle" placeholder="Titel eingeben" required>
			</div>
			<div class="form-group">
				<label for="gamePath">Pfad</label>
				<input type="text" class="form-control" id="gamePath" name="gamePath" placeholder="Pfad eingeben" required>
			</div>
			<div class="form-group">
				<label for="germanDescription">Deutsche Beschreibung</label>
				<textarea class="form-control" id="germanDescription" name="germanDescription" aria-describedby="germanHelp" rows="3"></textarea>
				<small id="germanHelp" class="form-text text-muted">max. 240 Zeichen</small>
			</div>
			<div class = "form-group">
				<label for="englishDescription">Englische Beschreibung</label>
				<textarea class="form-control" id="englishDescription" name="englishDescription" aria-describedby="englishHelp" rows="3"></textarea>
				<small id="englishHelp" class="form-text text-muted">max. 240 Zeichen</small>
			</div>
		
			<div class="form-group">
				<div class="custom-file" id="customFile" lang="es">
					<input type="file" class="custom-file-input" name="thumbnail" aria-describedby="fileHelp">
					<label class="custom-file-label" for="exampleInputFile">
						Thumbnail auswählen...
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="custom-file" id="customFile" lang="es">
					<input type="file" class="custom-file-input" name="screenshot" aria-describedby="fileHelp">
					<label class="custom-file-label" for="exampleInputFile">
						Screenshot auswählen...
					</label>
				</div>
			</div>
			
		<div class="form-group" id="buttons">
			<button id="submitButton" type ="submit" class="btn btn-primary" role="button">Fertig</button>
			<button type ="cancel" class="btn btn-primary" role="button">Abbrechen</button>
		</div>
		
		</#if>
	</form>
	<#if filterOffen == true>
	<p id="tagtext" >tagtext</p>
		<div id="filter"> 
		<form action="/insert" method="get">
			<div class="table-responsive">
			<table class="table">
			<tbody>
				<#if tagCats?has_content>
				<tr><#list tagCats as tagCat>
				<td class="">${tagCat.labelDE!"noCatLabel"}
				<#list tagCat.taglist as tag>
					<div class="input-group input-group-sm">
						<div class="input-group-append">
							<div class="input-group-text">
								<input type="checkbox" class="tagaddbutton" value=".${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}">
							</div>
								<span class="input-group-text">${tag.labelDE!"noTagLabel"}</span>
						</div>
					</div>
				</#list></td>
				</#list></tr>
				</#if>
			</tbody>
			</table>
			</div>
		</form>
		</div>
	
		<form>
			<div class="form-group" id="buttons">
				<button id="submitButton" type ="submit" class="btn btn-primary" role="button">Fertig</button>
			</div>
		</form>
		
		</#if>
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
	<script>
	$('.tagaddbutton').on('click', function(){
		var tag = $(this).val();
		$('#tagtext').append(" " + tag);
		$(this).parent().parent().toggleClass("tagaddbutton tagdeletebutton");
		$(this).toggleClass("tagaddbutton tagdeletebutton");
	});
	</script>
	<script>
	$('.tagdeletebutton').on('click', function(){
		$('#rando').append("nope");
	});
	</script>
</body>
</html>
