<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html class="h-100">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">

<title>Artist List</title>
</head>
<body class="h-100" style="background-color: #272727; color: white">
	<!-- Header Artista -->
	<div class="d-flex justify-content-between p-3" style="margin-bottom: 5%">
		<div class="d-flex jsutify-content-center">
			<img src="https://via.placeholder.com/150" class="rounded-circle w-30">
			<div class="py-3 mx-3" style="width:40%">
				<h2>${artist.name}</h2>
				<p>${artist.summary}</p>
			</div>
			<button class="btn rounded-pill" style="color:white;height: fit-content;background-color: #af3248">+ Invitar a mi equipo</button>
		</div>
		<button class="btn rounded-pill" style="color:white;height: fit-content;background-color: #af3248">Contactar</button>
	</div>

	<!-- Info general Artista -->
	<div class="container" style="margin: 0 5%;max-width:90%">
		<div class="row">
			<div class="col-4 p-3" style="border-color: #af3248;border-style: solid;border-width: 0 2px 0 0;">
				<h4 style="margin-bottom: 4%">Sobre mi</h4>
				<p>${artist.description}</p>
			</div>
			<div class="col-4 p-3" style="border-color: #af3248;border-style: solid;border-width: 0 2px 0 0;">
				<h4 style="margin-bottom: 4%">Habilidades</h4>
				<!-- c:forEach items="${artist.roles}" var="role" -->
					<span class="rounded-pill p-2 m-1" style="background-color: #af3248">Habilidad</span><!-- ${role}-->				
				<!-- c:forEach -->
			</div>
			<div class="col-4 p-3">
				<h4 style="margin-bottom: 4%">Proyectos</h4>
				<!-- c:forEach items="${projects}" var="project" -->
					<div class="d-flex justify-content-between">
						<div>
							<h6 style="color:#af3248">Nombre proj</h6><!-- ${project.title} -->
							<ul>
							<!-- c:forEach items="${projects.roles}" var="pjRole" -->
									<li>Guionista</li><!-- ${pjRole} -->
							<!-- /c:forEach -->
							</ul>
						</div>
						<img src="https://via.placeholder.com/150" class="w-30">
					</div>
					<hr class="m-3" style="border-width: 3px;border-style: solid;border-radius: 20px;border-color:#af3248">
						
				<!-- /c:forEach -->
			</div>
		</div>
	</div>

</body>
</html>

<!-- COMENTARIOS PARA BACKEND -->
<!-- 


-Necesito que la entidad Artist tenga una lista de los roles que puede tomar
