<h1>${game.name}</h1>
<br>
<div>
<#if game.steamID?has_content>
<a href="steam://run/${game.steamID}">
	<button type="button" class="btn btn-secondary btn-lg">Steamspiel starten</button>
</a>
<#else>
	<button type="button" class="btn btn-warning btn-lg" data-toggle="modal" data-target="#oculusGoHowToStart">Oculus Go-Spiel</button>
	<div class="modal fade" id="oculusGoHowToStart" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
				<h5 class="modal-title">Diese App ist für die Oculus Go!</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				</div>
				<div class="modal-body">
					Um diese App zu starten, <br><br>[...]
				</div>
			</div>
		</div>
	</div>

</#if>

</div>
<br>
<img class="screenshot" src="screenshots/screenshot_${game.gameID}.jpg" onerror="if (this.src != '${game.screenshotLink}') this.src = '${game.screenshotLink}';">
<br><h2>${game.germanDescription!"Keine Beschreibung"}</h2>