<h1>${game.name}</h1>
	<!-- Warnhinweise -->
	<div class="ml-1">
		<#if game.taglist?has_content>
			<i class="fas fa-exclamation-triangle"></i>
				<div class="row DE">Begrenze deine Spielzeit auf 15-30 Minuten.</div>
				<div class="row EN">Don't play too long.</div><br/>
			
			<#list game.taglist as tag>
			<#if tag.catID == "0"><i class="fas fa-exclamation-triangle"></i>
				<small class="row DE">${tag.labelDE!""}</small>
				<small class="row EN">${tag.labelEN!""}</small><br/>
			</#if>
			</#list>
		</#if>
	</div>
	<br>
	
	<!-- Startbuttons -->
	<#if game.oculusID?has_content>
		<button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#oculusGoHowToStart"><div class="DE">Oculus Go-Spiel</div>
		<div class="EN">Oculus Go-Game</div></button>
		<!--Dialogfenster mit Oculus Go Startinstruktionen-->
		<div class="modal fade" id="oculusGoHowToStart" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title">
						<div class="DE">Diese App ist für die Oculus Go!</div>
						<div class="EN">This App is for the Oculus Go!</div></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
						<div class="DE">Um diese App zu starten, lass dir von der Aufsichtsperson helfen oder folge den Instruktionen auf dem beiliegenden Informationsblatt.</div>
						<div class="EN">To start this app, please ask your supervisor for instructions or follow the instructions on the information sheet.</div>
					</div>
				</div>
			</div>
		</div>
	</#if>
	<#if game.path?has_content>
		<#if game.steamID?has_content>
			<a class="col" href="steam://run/${game.steamID}">
				<button type="button" class="btn btn-secondary btn-lg">
					<div class="DE">Steamspiel starten</div>
					<div class="EN">Start Steam Game</div>
				</button>
			</a>
		<#else>
			<a class="col" href="kioskstart://${game.path}">
				<button type="button" class="btn btn-secondary btn-lg"><div class="DE">Spiel starten</div>
				<div class="EN">Start Game</div></button>
			</a>
		</#if>
	</#if>
<br>
<br>

<!-- Screenshot -->
<div class="row">
	<img class="screenshot" src="screenshots/screenshot_${game.gameID}.jpg" onerror="if (this.src != '${game.screenshotLink}') this.src = '${game.screenshotLink}';">	
</div>
<br>

<!-- Tags -->
	<#if game.taglist?has_content>
	<#list game.taglist as tag>
	<#if tag.catID != "0">
	<div class="DE"><span class="badge badge-secondary">#${tag.labelDE!"kein tagname"}</span></div>
	<div class="EN"><span class="badge badge-secondary">#${tag.labelEN!"no tag label"}</span></div>
	</#if>
	</#list>
	</#if>
<br>

<!-- Beschreibungen -->
<h2>
	<div class="DE">${game.germanDescription!"Keine Beschreibung"}</div>
	<div class="EN">${game.englishDescription!"No Description"}</div>
</h2>