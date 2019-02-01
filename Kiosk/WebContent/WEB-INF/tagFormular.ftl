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
	<li class="nav-item">
	<a class="nav-link" href="login">Login</a>
	</li>
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

<div id="warnung" class="alert alert-warning" role="alert">
</div>
	<div class="row top-buffer">
		<div id="filter"> 
		<form>
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
							<#if tag.checkedString=="checked">
								<input type="checkbox" class="tagdeletebutton" value="${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}" ${tag.checkedString}>
							<#else>
								<input type="checkbox" class="tagaddbutton" value="${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}" ${tag.checkedString}>
							</#if>
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
		</div>
		</form>
		<form action="main" method="get">
			<div class="form-group" id="buttons">
				<button type="submit" id="tagButton" class="btn btn-primary" role="button">OK</button>
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
	<script>
	$('.tagaddbutton').on('click', function() {
		var tagid = $(this).val();
		if($(this).hasClass('tagaddbutton'))
		{
			$.post('addTag', {tagID:tagid, action:'add', gameID:'${game.gameID}'}, function(data) {
			$('#warnung').html(data);
			});
		}else{
			$.post('addTag', {tagID:tagid, action:'delete', gameID:'${game.gameID}'}, function(data) {
			$('#warnung').html(data);
			});
		}
		$(this).toggleClass("tagaddbutton tagdelete");
		
	});
	</script>
</body>
</html>
