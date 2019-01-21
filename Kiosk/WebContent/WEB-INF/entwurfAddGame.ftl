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
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
</head>

<body>
<div class="container">
	<div class="row">
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
		
		<div class="form-group" id="buttons">
			<button id="submitButton" type ="submit" class="btn btn-primary" role="button">Fertig</button>
			<button type ="cancel" class="btn btn-primary" role="button">Abbrechen</button>
		</div>
		</#if>
	</form>		
	
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
</div>		

	
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
