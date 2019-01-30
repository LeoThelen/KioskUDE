<html>
<head>
<meta charset="utf-16">

<title>Games</title>
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link href="css/my.css" rel="stylesheet">
<link rel="stylesheet" href="css/myOverview.css" media="screen">
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
</head>
<body>
<div class="background-image"></div>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
		<a class="navbar-brand" href="#">VR Kiosk</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarsExampleDefault"
			aria-controls="navbarsExampleDefault" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon" />
		</button>

		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="login">Login</a>
				</li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
						<div class="DE">&Uuml;ber</div>
						<div class="EN">About</div>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item"
							href="https://github.com/LeoThelen/KioskUDE">
							<div class="DE">Projekt auf GitHub</div>
							<div class="EN">Project on GitHub</div>
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="about.ftl">
							<div class="DE">Impressum</div>
							<div class="EN">Imprint</div>
						</a>
					</div></li>
				<li class="nav-item"><a class="nav-link" id="DE">DE</a></li>
				<li class="nav-item" id="EN"><a class="nav-link">EN</a></li>
        </ul>
      </div>
      <input class="form-control quicksearch" type="search" placeholder="Suchen" aria-label="Search">
			<button class="input-group-append icon-search" id="searchie" />
			<!-- <input class="form-control quicksearch" type="search" placeholder="Suchen" aria-label="Search">
			<div class="input-group-append">
	    		<span class="input-group-text"><i class="icon-search" /></span>
	  		</div> -->
    </nav>
    <div id="header" class="DE">
			<!-- Filter -->
				<div class="row justify-content-start">
				<div class="filters col-12 roterHintergrund">
					<#if tagCats?has_content> <#list tagCats as tagCat>
					<select class="filters-select"
						data-filter-group="${tagCat.filterGroup}">
						<option data-filter="">
							<div class="DE">${tagCat.labelDE!"noCatLabel"}</div>
						</option>
						<#list tagCat.taglist as tag>
						<option data-filter=".${tag.filter}">
							<div class="DE">${tag.labelDE!"noTagLabel"}</div>
						</option>
						</#list>
					</select>
					</#list> </#if>
				</div>
				</div>
	</div>
	<div id="header" class="EN">
			<!-- Filter -->
				<div class="row justify-content-start">
				<div class="filters col-12 roterHintergrund">
					<#if tagCats?has_content> <#list tagCats as tagCat>
					<select class="filters-select"
						data-filter-group="${tagCat.filterGroup}">
						<option data-filter="">
							<div class="EN">${tagCat.labelEN!"noCatLabel"}</div>
						</option>
						<#list tagCat.taglist as tag>
						<option data-filter=".${tag.filter}">
							<div class="EN">${tag.labelEN!"noTagLabel"}</div>
						</option>
						</#list>
					</select>
					</#list> </#if>
				</div>
				</div>
	</div>
	<div class="row">
		<div id="left" class="col-6">
		<button id="back-to-top" type="button" class="btn btn-secondary btn-lg btn-block invisible"><i class="icon-chevron-up"></i> Filter <i class="icon-chevron-up"></i></button>
		<div class="filters">
	
		
		<div class="input-group mb-3">
			
  		</div>
  		
		</div>
			<!-- Hier sind die Spiele. -->
				<#if gamelist?has_content> <br>
				<div class="grid">
					<#list gamelist as game> <#if game?has_content>

					<div class="grid-item ${game.classtags!}" id="${game.gameID!}" gametitle="${game.name}">
						<#if game.thumbnailLink?has_content>
						<img src="screenshots/thumb_${game.gameID}.jpg" onerror="if (this.src != '${game.thumbnailLink}') this.src = '${game.thumbnailLink}';" alt="${game.name}">
						<#else>
						
						</#if>
					</div>
					</#if> </#list>
				</div>
				<#else>
					<div class="DE">Die Liste ist leer. Existieren wohl keine Games.</div>
					<div class="EN">The list is empty. Probably no Games existing.</div>
				</#if>

		</div>
		<div class="col-6 pt-60" id="description">
			<div style="text-align:center; font-size:200px; color:white;">UKE<br>VR
			</div>
		</div>
	</div>
	<style id="dynamicStyle">::-webkit-scrollbar-thumb</style>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.3.1.min.js"><\/script>')
	</script>
	<script	src="js/isotope.pkgd.js"></script>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/my.js"></script>
	
</body>
</html>