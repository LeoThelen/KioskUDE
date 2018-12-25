<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<title>Games</title>
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/myOverview.css" media="screen">
</head>
<body>
	<div class="row">
		<div class="col-6">
			<div class="filters">
				<#if taglistlist?has_content> <#list taglistlist as taglist>
				<div class="button-group js-radio-button-group"
					data-filter-group="color">
					<#if taglistlist?has_content>
					<button class="button is-checked" data-filter="">Alter</button>
					<#list taglist as tag>
					<button class="button" data-filter=".${tag}">${tag}</button>
					</#list> </#if>
				</div>
				</#list> </#if>

				<div class="button-group js-radio-button-group"
					data-filter-group="ballen">
					<button class="button is-checked" data-filter="">Genre</button>
					<button class="button" data-filter=".simulation">Simulation</button>
				</div>





				<#if gamelist?has_content> <br>
				<div class="grid">
					<#list gamelist as game> <#if game?has_content>

					<div class="grid-item ${game.classtags}" id="${game.id}"
						style="background-image: url(https://steamcdn-a.akamaihd.net//steam//apps//503630//header.jpg); background-repeat: no-repeat">
						${game.name} <br> <a href="/babble_details?gameid=${game.id}">kp, was hier hin soll, vllt Warnhinweissymbole</a><br><br><br><br><br><br>&#x1F648;eine wunderbare Erfahrung für Epileptiker.
					</div>
					</#if> </#list>
				</div>
				<#else> Die Liste ist leer. Existieren wohl keine Games. </#if>
			</div>
		</div>
		<div class="col-6" id="description">
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.1.1.min.js"><\/script>')
	</script>
	<script	src="js/isotope.pkgd.js"></script>
	<script src="js/my.js"></script>
	
</body>
</html>