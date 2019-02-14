<h1>${game.name}</h1>
<br>
<div>

	<#if game.oculusID?has_content>
		<button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#oculusGoHowToStart"><div class=DE>Oculus Go-Spiel</div>
		<div class=EN>Oculus Go-Game</div></button>
		<div class="modal fade" id="oculusGoHowToStart" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title"><div class=DE>Diese App ist für die Oculus Go!</div>
											<div class=EN>This App is for the Oculus Go!</div></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
						<div class=DE>Um diese App zu starten, <br><br>[...]</div>
						<div class=EN>To start this app, please ask your supervisor for instructions.</div>
						
					</div>
				</div>
			</div>
		</div>
	<#else>
		ok :(
	</#if>
	<#if game.path?has_content>
		<#if game.steamID?has_content>
			<a href="steam://run/${game.steamID}">
				<button type="button" class="btn btn-secondary btn-lg"><div class=DE>Steamspiel starten</div>
			<div class=EN>Start Steam Game</div></button>
			</a>
		<#else>
			<a href=\"kioskstart://X.java\">KLICK</a>
		</#if>
	</#if>

</div>
<br>
<img class="screenshot" src="screenshots/screenshot_${game.gameID}.jpg" onerror="if (this.src != '${game.screenshotLink}') this.src = '${game.screenshotLink}';">

<!-- tags des spiels anzeigen lassen -->
<br>
	<#if taglist?has_content> <#list taglist as tag>
	<div class=DE><span class="badge badge-secondary">#${tag.labelDE!"keine tags"}</span></div>
	<div class=EN><span class="badge badge-secondary">#${tag.labelEN!"keine tags"}</span></div>
	</#list>
	</#if>
	
<br><h2><div class=DE>${game.germanDescription!"Keine Beschreibung"}</div>
	<div class=EN>${game.englishDescription!"No Description"}</div></h2>
	