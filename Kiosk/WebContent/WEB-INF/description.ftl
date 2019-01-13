<h1>${game.name}</h1>
<br><div>

<#if game.steamID?has_content>
<a href="steam://run/${game.steamID}"><button type="button" class="btn btn-secondary btn-lg">Steamspiel starten</button></a>
<#else>
<a href="TEST?gameid=${game.gameID}"><button type="button" class="btn btn-warning btn-lg">ID:${game.gameID}</button></a>
</#if>

</div>
<br>
<img src="screenshots/screenshot_${game.gameID}.jpg" onerror="if (this.src != '${game.screenshotLink}') this.src = '${game.screenshotLink}';">
<br><h2>${game.germanDescription!"Keine Beschreibung"}</h2>