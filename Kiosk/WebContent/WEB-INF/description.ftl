<h1>${game.name}</h1>
<br><div class="anima">

<#if game.steamID?has_content>
<a href="steam://run/${game.steamID}"><button type="button" class="btn btn-primary btn-lg">Steamspiel starten</button></a>
<#else>
<a href="TEST?gameid=${game.gameID}"><button type="button" class="btn btn-warning btn-lg">ID:${game.gameID}</button></a>
</#if>

</div>
<br><img src="${game.screenshotLink}" alt="Screenshot">
<br>${game.germanDescription}