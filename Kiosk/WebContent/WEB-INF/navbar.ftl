<!-- Navigationsleiste -->	
	<nav id="navie" class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
		<a class="navbar-brand" href="main">VR Kiosk</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarsExampleDefault"
			aria-controls="navbarsExampleDefault" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon" />
		</button>

		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="login">
						<div class="DE">Adminbereich</div>
						<div class="EN">Administration</div>
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="about.ftl">
						<div class="DE">Info</div>
						<div class="EN">Info</div>
					</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<div class="DE">Sprache</div>
						<div class="EN">Language</div>
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" id="DE">
							Deutsch
						</a>
						<a class="dropdown-item" id="EN">
							English
						</a>
					</div>
				</li>
				</ul>
				<form class="form-inline my-2 my-lg-0">
				<div class="input-group mt-2 mb-2">
						<input class="form-control quicksearch" type="search" placeholder="Suchen" aria-label="Search">
						<div class="input-group-append">
				    		<span class="input-group-text"><i class="fas fa-search"></i></span>
				  		</div>
			  		</div>
			  		</form>
		</div>
	</nav>