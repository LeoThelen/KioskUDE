<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="description" content="Login">
		<meta name="author" content="Leonhard Thelen">
		<title>Login - VR Kiosk</title>
		<#include "cssBindings.ftl">
		<link href="css/myLogin.css" rel="stylesheet">
	</head>
	
	<body>
	
		<#include "navbar.ftl">
	
		<main role="main" class="container">
		
		<#if loggedin?exists && loggedin==true>
		<div class="form-signin">
		<h2 class="mb-5">Sie sind eingeloggt.</h2>
	<!-- Linkbutton zum Aufrufen des Modals "Steam App Hinzufügen" -->	
		<a href="#" class="btn btn-outline-primary btn-lg btn-block text-right" role="button" data-toggle="modal" data-target="#SteamAdder">
			<div class="row"><i class="col-1 fab fa-steam text-left" aria-hidden="true"></i><div class="col text-right">Steam App hinzufügen</div></div>
		</a>
	<!-- Linkbutton zum Aufrufen des Modals "Oculus Go App Hinzufügen"-->	
		<a href="#" class="btn btn-outline-primary btn-lg btn-block text-right" role="button" data-toggle="modal" data-target="#oculusGoAdder">
			<div class="row"><i class="col-1 fas fa-eye text-left" aria-hidden="true"></i><div class="col text-right">Oculus Go App hinzufügen</div></div>
		</a>
	<!-- Linkbutton zum Aufrufen des Formulars "Spiel Hinzufügen" -->	
		<a href="gameFormular" class="btn btn-outline-primary btn-lg btn-block text-right" role="button">
			<div class="row"><i class="col-1 fas fa-ellipsis-h text-left" aria-hidden="true"></i><div class="col text-right">manuell App hinzufügen</div></div>
		</a>
	<!-- Linkbutton zum Aufrufen der Spiele Bearbeiten-Übersicht -->	
		<a href="main" class="btn btn-outline-primary btn-lg btn-block text-right" role="button">
			<div class="row"><i class="col-1 fas fa-edit text-left" aria-hidden="true"></i><div class="col text-right">Spiele Bearbeiten</div></div>
		</a>
	<!-- Linkbutton zum Aufrufen des Modals "Passwort Ändern" -->	
		<a href="#" class="btn btn-outline-primary btn-lg btn-block text-right" role="button" data-toggle="modal" data-target="#editPassword">
		<div class="row"><i class="col-1 fas fa-key text-left" aria-hidden="true"></i><div class="col text-right">Passwort ändern</div></div>
		</a>
	<!-- Linkbutton zum Ausloggen -->	
		<a href="logout" class="btn btn-warning btn-lg mt-5" role="button">Ausloggen</a>
		</div>
		
		<!-- Modals -->
		<!-- Modal "Steam App Hinzufügen" -->	
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
						<i class="col-1 fas fa-info-circle text-left fa-lg info-symbol" aria-hidden="true"></i>
						<div class="info hidden">
							<div class="dropdown-divider"></div>
							<p>Wie finde ich die Steam ID?</p>
							<p>Schritt 1: Starten sie die Webseite im Fenstermodus.</p>
							<p>Schritt 2: Öffnen Sie die <a href="https://store.steampowered.com/?l=german"> Steam Website. </a></p>
							<p>Schritt 3: Suchen Sie nach dem gewünschten Spiel.</p>
							<p>Schritt 4: Die Steam ID finden Sie nun in dem Link.</p>
							<img class="gif" src="screenshots/steamInfo.gif">
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal "Oculus Go App Hinzufügen"-->	
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
						<i class="col-1 fas fa-info-circle text-left fa-lg info-symbol" aria-hidden="true"></i>
						<div class="info hidden">
							<div class="dropdown-divider"></div>
							<p> Wie finde ich die Oculus ID? </p>
							<p>Schritt 1: Starten sie die Webseite im Fenstermodus.</p>
							<p>Schritt 2: Öffnen Sie die <a href="https://www.oculus.com/experiences/go/">Oculus Website</a></p>
							<p>Schritt 3: Suchen Sie nach dem gewünschten Spiel.</p>
							<p>Schritt 4: Die Oculus ID finden Sie nun in dem Link.</p>
							<img class="gif" src="screenshots/oculusInfo.gif">
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal "Passwort Ändern" -->	
		<div class="modal fade" id="editPassword" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Passwort ändern</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form class="form-signin" action="edit_password" method="post">
							<input type="password" id="password" class="form-control" placeholder="Neues Passwort" name="newPassword" required>
							<button class="btn btn-lg btn-primary btn-block mt-2" type="submit">Passwort ändern</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<#else>
		<!-- Dialog zum einloggen -->
		<form class="form-signin" action="login" method="post">
		
		<h1 class="h3 font-weight-normal">Bitte einloggen</h1>
		
		<input type="text" id="inputUser" class="form-control" placeholder="E-Mail-Adresse" name="username" required autofocus>
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
		
		<!-- Modal "Passwort vergessen" -->	
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
		<#include "jsBindings.ftl">
		<!-- Liest Email aus "Passwort vergessen" Dialog -->
		<script>	
		  $('#forgot-pw-badge').click(function(){
		  	var daMail=$('#inputUser').val();
		  	$('#inputUserForgotPW').val(daMail);
		  });
		</script>
		<!-- Toggeled informationen für Steam und Oculus Go Modals -->
		<script>
			$('.info-symbol').on('click', function() {
				$('.info').toggleClass("hidden");
			});
		</script>
		<!-- überträgt automatisch den usernamen vom letzten Loginversuch bei fehlerhaft eingegebenem Passwort -->
		<#if username??>
		<script>
		  $(document).ready(function(){
			$('#inputUser').val("${username}");
		  }); 
		</script>
		</#if>
	</body>
</html>
