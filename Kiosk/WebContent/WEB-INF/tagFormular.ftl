<!DOCTYPE html>
<html>
	<head>
		<title>Spiel hinzufügen</title>
		<#include "cssBindings.ftl">
	</head>	
	<body>
		<#include "navbar.ftl">
		<main role="main" class="mt-5">
			<#if loggedin?exists && loggedin==true>
			<!-- Hinweisbox (erhält Feedback von Servlet, wenn Tags gesetzt wurden.) -->
			<div id="warnung" class="alert alert-warning" role="alert">
				Bitte Tags für die Suche setzen.
			</div>
			
			<!-- Tagliste -->
			<#if tagCats?has_content>
			<div class="row">
			<#list tagCats as tagCat>
			<div class="col"><div>${tagCat.labelDE!"noCatLabel"}</div>
			<#list tagCat.taglist as tag>
				<div class="input-group input-group-sm">
					<div class=" input-group-append">
						<div class="input-group-text">
						<#if tag.checkedString=="checked">
							<input type="checkbox" class="tagdeletebutton" value="${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}" ${tag.checkedString}>
						<#else>
							<input type="checkbox" class="tagaddbutton" value="${tag.tagID}" aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}" ${tag.checkedString}>
						</#if>
						</div>
							<span class="input-group-text">${tag.labelDE!"noTagLabel"}</span>
					</div>
				</div>
			</#list></div>
			</#list><div class="col-1"></div></div>
			</#if>
			<!-- Link zur Hauptseite -->
			<form class="container mt-5" action="main" method="get">
				<div class="form-group" id="buttons">
					<button type="submit" id="tagButton" class="btn btn-primary btn-block" role="button">Ok und zurück zur Übersicht</button>
				</div>
			</form>
		</div>
		<#else>
			Nicht eingeloggt.
		</#if>
		</main>				
		<#include "jsBindings.ftl">
		
		<!-- Funktion zum togglen von Tags -->
		<script>
			$('.tagaddbutton, .tagdeletebutton').on('click', function() {
				var tagid = $(this).val();
				if($(this).hasClass('tagaddbutton'))
				{
					$.post('addTag', {tagID:tagid, action:'add', gameID:'${game.gameID}'}, function(data) {
					$('#warnung').html(data);
				});
				}else{
					$.post('addTag', {tagID:tagid, action:'delete', gameID:'${game.gameID}'}, function(data) {
					$('#warnung').html(data);
				});
				}
				$(this).toggleClass("tagaddbutton tagdelete");
				
			});
		</script>
	</body>
</html>
