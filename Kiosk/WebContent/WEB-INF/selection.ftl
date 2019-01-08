<html>
<head>
<meta charset="utf-8">

<title>Games</title>
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="css/myOverview.css" media="screen">
</head>
<body>
	<div class="row">
		<div class="col-6">
			<div class="filters">
				<#if taglistlist?has_content> <#list taglistlist as taglist>
				<div class="button-group js-radio-button-group"
					data-filter-group="age">
					<#if taglistlist?has_content>
					<button class="button is-checked" data-filter="">Alter</button>
					<#list taglist as tag>
					<button class="button" data-filter=".${tag}">${tag}</button>
					</#list> </#if>
				</div>
				</#list> </#if>

				<div class="button-group js-radio-button-group"
					data-filter-group="genre">
					<button class="button is-checked" data-filter="">Genre</button>
					<button class="button" data-filter=".simulation">Simulation</button>
				</div>





				<#if gamelist?has_content> <br>
				<div class="grid">
					<#list gamelist as game> <#if game?has_content>

					<div class="grid-item ${game.classtags!}" id="${game.gameID!}">
						<#if game.thumbnailLink?has_content><img src="${game.thumbnailLink}" alt="${game.name}">
						<#else>
						
						</#if>
					</div>
					</#if> </#list>
				</div>
				<#else> Die Liste ist leer. Existieren wohl keine Games. </#if>
			</div>
		</div>
		<div class="col-6" id="description">
			<div style="text-align:center; font-size:120px"><br></div>
			<div style="text-align:center; font-size:200px;color:#666" id="anima">UKE<br>VR
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.1.1.min.js"><\/script>')
	</script>
	<script	src="js/isotope.pkgd.js"></script>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/my.js"></script>
	
</body>
</html>