<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/isotope-docs.css" media="screen">
<link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="css/my.css">

<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">

</head>
<body>
	<form action="/insert" method="get">
		<div class="table-responsive">
		<table class="table">
			<tbody>
				<#if tagCats?has_content>
				
				<tr><#list tagCats as tagCat>
					<td class="">${tagCat.labelDE!"noCatLabel"}
					<#list tagCat.taglist as tag>
						<div class="input-group input-group-sm">
							<div class="input-group-append">
								<div class="input-group-text">
									<input type="checkbox" value=".${tag.tagID}"
										aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}">
								</div>
								<span class="input-group-text">${tag.labelDE!"noTagLabel"}</span>
							</div>
						</div>
						</#list></td>
				</#list></tr>
				</#if>
			</tbody>
		</table>
		</div>
		<input type="submit" value="Submit">
	</form>
	
	
	
	<form action="/insert" method="get">
		<div class="container">
			<#if tagCats?has_content>
				<div class="row"><#list tagCats as tagCat>
					<div class="col-4">${tagCat.labelDE!"noCatLabel"}
					<#list tagCat.taglist as tag>
						<div class="input-group input-group-sm">
							<div class="input-group-append">
								<div class="input-group-text" data-toggle="tooltip" data-placement="left" title="Tooltip on left">
									<input type="checkbox" value=".${tag.tagID}" 
										aria-label="Checkbox f&uuml;r ${tag.labelDE!" eine Option ohneNamen."}">
								</div>
								<span class="input-group-text">${tag.labelDE!"noTagLabel"}</span>
							</div>
						</div>
						</#list></div>
				</#list></div>
				</#if>
			
		</div>
		</div>
		<input class="btn btn-success" type="submit" value="Submit">
	</form>
	
	
	
	
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="js/jquery-3.3.1.min.js"><\/script>')
	</script>
	<script src="js/isotope.pkgd.js"></script>
	<script src="js/bootstrap.bundle.min.js"></script>
	<script src="js/my.js"></script>

</body>
</html>