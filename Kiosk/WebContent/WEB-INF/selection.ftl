<html>
<head>
<meta charset="utf-8">

<title>Games</title>
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link href="css/my.css" rel="stylesheet">
<link rel="stylesheet" href="css/myOverview.css" media="screen">

</head>
<body>
 <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="#">VR Kiosk</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="login">Login</a>
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
            <li class="nav-item">
            <a class="nav-link" href="DE">DE</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="EN">EN</a>
            </li>

        </ul>
      </div>
    </nav>
			<div class="filters">
			<!-- Hier sind die Filter. -->
				<#if tagCats?has_content> <#list tagCats as tagCat>
				<div class="button-group js-radio-button-group"
					data-filter-group="${tagCat.filterGroup}">
					<button class="button is-checked" data-filter="">${tagCat.labelDE!"noCatLabel"}</button>
					<#list tagCat.taglist as tag>
					<button class="button" data-filter=".${tag.filter}">${tag.labelDE!"noTagLabel"}</button>
					</#list>
				</div>
				</#list> </#if>
			</div>
<div class="hidden">Der Filterfilter filtert Filter!</div>
	<div class="row">
		<div class="col-6">
		<input class="form-control mr-sm-2" type="quicksearch" placeholder="Suchen" aria-label="Search">

			<!-- Hier sind die Spiele. -->
				<#if gamelist?has_content> <br>
				<div class="grid">
					<#list gamelist as game> <#if game?has_content>

					<div class="grid-item ${game.classtags!}" id="${game.gameID!}">
						<#if game.thumbnailLink?has_content>
						<img src="screenshots/thumb_${game.gameID}.jpg" onerror="if (this.src != '${game.screenshotLink}') this.src = '${game.screenshotLink}';" alt="${game.name}">
						<#else>
						
						</#if>
					</div>
					</#if> </#list>
				</div>
				<#else> Die Liste ist leer. Existieren wohl keine Games. </#if>

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