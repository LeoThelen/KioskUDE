<html>
<head>
<meta charset="utf-8">

<title>Games</title>
<#include "cssBindings.ftl">
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/mySelection.css" media="screen">
</head>
<body>
<div class="background-image"></div>
	<!-- Navbar -->
	<#include "navbar.ftl">

			<!-- Filter -->
			<div class="row justify-content-start">
				<div class="filters mt-5 col-6">
					<#if tagCats?has_content> <#list tagCats as tagCat>
					<div class="button-group js-radio-button-group row mb-3"
						data-filter-group="${tagCat.filterGroup}">
						<button class="button is-checked col-3" data-filter="">
							<div class="DE">${tagCat.labelDE!"noCatLabel"}</div>
							<div class="EN">${tagCat.labelEN!"noCatLabel"}</div>
						</button>
						<#list tagCat.taglist as tag>
						<button class="button col-3" data-filter=".${tag.filter}">
							<div class="DE">${tag.labelDE!"noTagLabel"}</div>
							<div class="EN">${tag.labelEN!"noTagLabel"}</div>
						</button>
						</#list>
					</div>
					</#list> </#if>
				</div>
			</div>
			
	<div class="row">
		<div id="left" class="col-6">
			<button id="back-to-top" type="button" class="btn btn-secondary btn-lg btn-block invisible"><i class="fas fa-chevron-up"></i> Filter <i class="fas fa-chevron-up"></i></button>
			<div class="filters">
				<div class="input-group mb-3">
					<input class="form-control quicksearch" type="search" placeholder="Suchen" aria-label="Search">
					<div class="input-group-append">
			    		<span class="input-group-text"><i class="icon-search"></i></span>
			  		</div>
		  		</div>
			</div>
			<!-- Hier sind die Spiele. -->
			
			<#if gamelist?has_content> <br>
			<div class="grid">
				<#list gamelist as game> <#if game?has_content>
					<div class="grid-item ${game.classtags!}" id="${game.gameID!}" gametitle="${game.name}">
						<div class="container-btn-admin">
					<#if loggedin??>
						<a href="gameFormular?editID=${game.gameID!}" type="button" class="btn btn-semitransparent-warning btn-lg">Bearbeiten</a>
						<button type="button" class="btn btn-semitransparent-danger btn-lg" data-toggle="modal" data-target="#confirmDelete">Löschen</button>
					</#if>
					</div>
					<#if game.thumbnailLink?has_content>
					<img class="thumbnail" src="screenshots/thumb_${game.gameID}.jpg" onerror="if (this.src != '${game.thumbnailLink}') this.src = '${game.thumbnailLink}';" alt="${game.name}">
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
			<div style="text-align:center; font-size:200px;">UKE<br>VR
			</div>
		</div>
	</div>
	<div class="modal fade" id="confirmDelete" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
				<h5 class="modal-title">Eintrag wirklich löschen?</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
			</div>
				<div class="modal-body">
					<form class="form-signin" action="delete_game" method="post">
					<input type="text" id="delID" class="hidden form-control" name="delID" required>
						<button class="btn btn-lg btn-danger btn-block" type="submit">Löschen</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<style id="dynamicStyle">::-webkit-scrollbar-thumb {background: #aaa;}</style>
	<#include "jsBindings.ftl">

	
</body>
</html>