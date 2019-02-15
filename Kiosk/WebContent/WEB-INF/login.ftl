<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Login">
	<meta name="author" content="Leonhard Thelen">
	<title>Login - VR Kiosk</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/my.css" rel="stylesheet">
	<link href="css/myLogin.css" rel="stylesheet">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" rel="stylesheet">
</head>

<body>
	<header>
	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
	<a class="navbar-brand" href="main">VR Kiosk</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
	<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarsExampleDefault">
	<ul class="navbar-nav mr-auto">

	<li class="nav-item active">
	<a class="nav-link" href="#">Login</a>
	</li>
	<li class="nav-item dropdown">
	<a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	&Uuml;ber
	</a>
	<div class="dropdown-menu" aria-labelledby="navbarDropdown">
	<a class="dropdown-item" href="about.ftl">Impressum</a>
	</div>
	</li>
	</ul>
	</div>
	</nav>
</header>
<main role="main" class="container">

<#if loggedin==true>
<div class="form-signin">
<h2 class="mb-5">Sie sind eingeloggt.</h2>

<a href="#" class="btn btn-outline-primary btn-lg btn-block text-right" role="button" data-toggle="modal" data-target="#SteamAdder">
	<div class="row"><i class="col-1 fab fa-steam text-left" aria-hidden="true"></i><div class="col text-right">Steam App hinzufügen</div></div>
</a>
<a href="#" class="btn btn-outline-primary btn-lg btn-block text-right" role="button" data-toggle="modal" data-target="#oculusGoAdder">
	<div class="row"><i class="col-1 fas fa-eye text-left" aria-hidden="true"></i><div class="col text-right">Oculus Go App hinzufügen</div></div>
</a>
<a href="gameFormular" class="btn btn-outline-primary btn-lg btn-block text-right" role="button">
	<div class="text-left"><i class="fas fa-ellipsis-h text-left" aria-hidden="true"></i></div>Drittanbieter-App hinzufügen
</a>
<a href="main" class="btn btn-outline-primary btn-lg btn-block text-right" role="button">
	<div class="text-left"><i class="fas fa-edit text-left" aria-hidden="true"></i></div>Spiele Bearbeiten
</a>
<a href="logout" class="btn btn-warning btn-lg mt-5" role="button">Ausloggen</a>
</div>











<div class="modal fade" id="SteamAdder" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Steam App hinzufügen</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="form-signin" action="import_steam" method="post">
					<input type="text" id="steamID" class="form-control" placeholder="Steam App ID" name="steamID" required>
					<button class="btn btn-lg btn-primary btn-block mt-2" type="submit">Spiel hinzufügen...</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="oculusGoAdder" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Oculus Go App hinzufügen</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="form-signin" action="import_oculusgo" method="post">
					<input type="text" id="oculusID" class="form-control" placeholder="Oculus App ID" name="oculusID" required>
					<button class="btn btn-lg btn-primary btn-block mt-2" type="submit">Spiel hinzufügen...</button>
				</form>
			</div>
		</div>
	</div>
</div>

<#else>
<form class="form-signin" method="post">

<h1 class="h3 font-weight-normal">Bitte einloggen</h1>

<input type="text" id="inputUser" class="form-control" placeholder="E-Mail-Adresse" name="user" required autofocus>
<label for="inputPassword" class="sr-only">Password</label>
<input type="password" id="inputPassword" class="form-control" placeholder="Passwort" name="password" required>
<button class="btn btn-lg btn-primary btn-block" type="submit">Anmelden</button>
	<#if wrongpassword??>
	<div class="alert alert-secondary" role="alert">
	Login schlug fehl.
	<br>Bitte nochmal versuchen.
	</div>
	</#if>
<br>
<a href="#" id="forgot-pw-badge" class="badge badge-light transition2" data-toggle="modal" data-target="#passwortVergessen">Passwort vergessen?</a>
</form>
<div class="modal fade" id="passwortVergessen" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Passwort zur&uuml;cksetzen</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="form-signin" action="/resetPassword" method="post">
					<input type="text" id="inputUserForgotPW" class="form-control" placeholder="E-Mail-Adresse" name="user" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Zur&uuml;cksetzen</button>
				</form>
			</div>
		</div>
	</div>
</div>
</#if>
</main>
<!-- /.container -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.3.1.min.js"><\/script>')
	</script>
	<script src="js/bootstrap.bundle.min.js"></script>
<script>	
  $('#forgot-pw-badge').click(function(){
  	var daMail=$('#inputUser').val();
  	$('#inputUserForgotPW').val(daMail);
  });
</script>
<#if lastmail??>
<script>
  $(document).ready(function(){
  	$('#inputUser').val("${lastmail}");
  }); 
</script>
</#if>
</body></html>