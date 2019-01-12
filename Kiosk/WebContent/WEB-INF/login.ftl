<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Login">
	<meta name="author" content="Leonhard Thelen">
	<title>Login - VR Kiosk</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/my.css" rel="stylesheet">
	<link href="css/login-template.css" rel="stylesheet">
</head>

<body>
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
	<a class="dropdown-item" href="https://github.com/LeoThelen/KioskUDE">Projekt auf GitHub</a>
	<div class="dropdown-divider"></div>
	<a class="dropdown-item" href="about.ftl">Impressum</a>
	</div>
	</li>
	</ul>
	</div>
	</nav>

<main role="main" class="container">
<form class="form-signin" method="post">
<#if loggedin==true><h1>Sie sind eingeloggt.</h1>
<a href="logout"><button type="button" class="btn btn-warning btn-lg">Ausloggen</button></a>
<#else>
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
<a href="#" id="forgot-pw-badge" class="badge badge-light" data-toggle="modal" data-target="#passwortVergessen">Passwort vergessen?</a>
</form>
</#if>

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