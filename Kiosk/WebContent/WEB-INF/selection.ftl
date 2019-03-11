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

		<div id="filterbar">
			<!-- Filter -->
			<div class="row justify-content-start">
				<div class="filters col-12 roterHintergrund">
					<#if tagCats?has_content>
					<#list tagCats as tagCat>
					<#if tagCat.labelDE != "Warnhinweise">
					<select class="filters-select DE" data-filter-group="${tagCat.filterGroup}">
						<option value="">
							${tagCat.labelDE!"noCatLabel"}
						</option>
						<#list tagCat.taglist as tag>
						<option value=".${tag.filter}">
							${tag.labelDE!"noTagLabel"}
						</option>
						</#list>
					</select>
					<select class="filters-select EN" data-filter-group="${tagCat.filterGroup}">
						<option value="">
							${tagCat.labelEN!"noCatLabel"}
						</option>
						<#list tagCat.taglist as tag>
						<option value=".${tag.filter}">
							${tag.labelEN!"noTagLabel"}
						</option>
						</#list>
					</select>
					</#if>
					</#list>
					</#if>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="left" class="col-6">
					
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
			<div class="col-6 pt-90" id="description">
				<img class="center" src="screenshots/UKE_Logo.png"/>
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