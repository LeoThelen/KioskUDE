<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width" = device-width, initial-scale = 1">
<meta charset="utf-8">
<title>Spiel hinzuf&uuml;gen</title>
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
		
			div class="form-group">
			  <div class="file-field">
			    <div class="z-depth-1-half mb-4">
			      <img src="https://mdbootstrap.com/img/Photos/Others/placeholder.jpg" class="img-fluid" alt="example placeholder">
			    </div>
			    <div class="d-flex justify-content-center">
			      <div class="btn btn-mdb-color btn-rounded float-left">
			        <span>Choose file</span>
			        <input type="file">
			      </div>
			    </div>
			  </div>
			</div>
		<div class="form-group" id="buttons">
			<button id="submitButton" type ="submit" class="btn btn-primary" role="button">Fertig</button>
			<button type ="cancel" class="btn btn-primary" role="button">Abbrechen</button>
		</div>
		
		</#if>
	
	<#if filterOffen == true>
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
								<input type="checkbox" value=".${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}">
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
		</from>
		
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

</body>
</html>
