<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<style>

		.filtro-wrap-mobile{
			display: none  !important;
		}
		
		.filtro-wrap{
			display: block;
			position: relative;
			background-color: var(--rojo);
			border-radius: 0 100px 100px 0;
		    box-shadow: 0 0 10px black;
		}
		
		
		.padding-nav > h2{
			text-align:center;
			margin-bottom: 2rem;
			text-shadow: 0 0 10px black;
		}
		
		
		.filtro-scroller{
			position: sticky;
			top:10rem;
			bottom:0;
			padding-bottom: 14rem;
		}
		
		.background-image{
			z-index: -1;
		   background-image: url(https://cdn.hipwallpaper.com/i/33/55/hreiP4.jpg);
		   width: 110%;
		   position: absolute;
		   height: 100%;
		   filter: blur(5px);
		   background-size: contain;
		}
		
		.fondo-a {
			background-color: rgb(49 5 24 / 69%);
		}
			
		#list-wrap{
			transition: 0.5s;
			display: block;
			flex-direction: column;
		}
		
		.filtro-wrap button{
			border-color: var(--gris);
		}
		
		.filtro-wrap button:hover{
			background-color: var(--gris);
		}
		
		h3,h5,label{
			text-align: center;
		}
		
		h5{
			margin: 0.5vw;
		}
		
		.lista{
			display: grid;
 			 grid-template-columns: repeat(5, 15rem);
		}
		
		#list-wrap-mobile{
			display: none  !important;
		}
			
			
		label{
			width:50%;
			padding-right: 0.6rem;
		}	
			
		@media(max-width: 1545px) {
			.lista{
			 	grid-template-columns: repeat(4, 13rem);
			 	margin:auto;

			}
	
		}
			
			
		@media(max-width: 1160px) {
			
			.filtro-wrap-mobile{
				display: block  !important;
    			padding: 0 1.5rem;
			}
			
			.padding-footer{
				padding-bottom: 32rem !important;
			}
			
			.filtro-wrap{
				display: none  !important;
			}
			
			#global-wrap{
				display:block !important;
			}
			
			#list-wrap-mobile{
				display: block  !important;
				background-color: var(--gris-claro);
			}
			
			#list-wrap{
				display:none !important;
			}
			
			.background-image{
				width: 100%;
			}
			
			.padding-nav > h2{
				font-size: 2.5rem;
			}
			
			.padding-nav{
			    background-color: rgb(0,0,0, 20%);
			}
			
			.linea-hor{
				border-color: var(--rojo);
				background-color: var(--rojo);
			}
			
			.filtro-scroller{
				padding-bottom: unset;
			}
			
		}
</style>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shorcut icon" type="image/ico" href="https://github.com/Carcotmed/CinemaParadisoGrupo-04/blob/feature/fix-general/src/main/webapp/WEB-INF/views/static/favicon.ico?raw=true" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
</head>
<body>
	<jsp:include page="/WEB-INF/views/navbar.jsp" ></jsp:include>
	<script>
	
	$(window).scroll(function () {
	    var lista = document.getElementById("list-wrap");

	    if($(window).scrollTop() > 400){
	    	lista.classList.add("fondo-a");

	    }else{
	    	lista.classList.remove("fondo-a");
	    }
		
	})
	
</script>
	<a id="top"></a>
	<div class="background-image"></div>
	<div class="padding-nav">
		<h2>Artistas</h2>
		<div id="global-wrap" class="d-flex">
	
			<!--  Filtros -->
			<div class="filtro-wrap p-4 w-25">
				<div class="filtro-scroller">
					<h3 class="page-header mb-4" >Filtros</h3>
					<hr class="m-3 linea-hor">
					<form:form class="my-5" action="list" method="POST" modelAttribute="artistsFiltered">
						<div class="form-group d-flex justify-content-between align-items-center my-4">
							<form:label class="form-control-label" path="user.username">Nombre de usuario:</form:label>
							<form:input class="form-control" style="width:60%" type="text" path="user.username" />
						</div>
						
						<div class="form-group d-flex justify-content-between align-items-center my-4">
							<form:label class="form-control-label" path="role">Rol:</form:label>
							<form:select class="form-control" style="width:60%" path="role">
								<form:option value="" selected="true">Selecciona un rol</form:option>
								<c:forEach items="${roles}" var="role">
									<form:option value="${role}">${role}</form:option>
								</c:forEach>
							</form:select>
						</div>
						
						
				
										
						<div class="form-group d-flex justify-content-center align-items-center my-4">
							<form:button class="boton btn rounded-pill">Filtrar</form:button>
						</div>
					</form:form>
				</div>
			</div>
			
			<!--  Filtros Mobile -->
			<div class="filtro-wrap-mobile w-100">
				<div class="filtro-scroller">
					<hr class="m-3 linea-hor">
					<h3 class="page-header mb-4" >Filtros</h3>
					<form:form action="list" method="POST" modelAttribute="artistsFiltered">
						<div class="form-group d-flex justify-content-between align-items-center my-4">
							<form:label class="form-control-label" path="user.username">Nombre de usuario:</form:label>
							<form:input class="form-control" style="width:60%" type="text" path="user.username" />
						</div>
						
						<div class="form-group d-flex justify-content-between align-items-center my-4">
							<form:label class="form-control-label" path="role">Rol:</form:label>
							<form:select class="form-control" style="width:60%" path="role">
								<form:option value="" selected="true">Selecciona un rol</form:option>
								<c:forEach items="${roles}" var="role">
									<form:option value="${role}">${role}</form:option>
								</c:forEach>
							</form:select>
						</div>
		
										
						<div class="form-group d-flex justify-content-center align-items-center my-4">
							<form:button class="boton btn rounded-pill">Filtrar</form:button>
						</div>
					</form:form>
				</div>
			</div>
	
			<!--  Listado  -->
			<div id="list-wrap" class="d-flex justify-content-center w-75 padding-footer">
				<div class="lista">
		      		<c:forEach items="${artistsPro}" var="artistPro">
			      		<c:if test="${artistPro.user.enabled}">
			      			<div class="element-wrapper element-pro d-flex flex-column align-items-center justify-content-evenly" onClick="location.href='/artists/show/${artistPro.id}'">
								<img class="rounded-circle" src="${artistPro.photo}">
				      			<h5>${artistPro.user.username}</h5>
				      		</div>
				      	</c:if>
		      		</c:forEach>
		      		<c:forEach items="${artistsNoPro}" var="artistNoPro">
			      		<c:if test="${artistNoPro.user.enabled}">
			      			<div class="element-wrapper d-flex flex-column align-items-center justify-content-evenly" onClick="location.href='/artists/show/${artistNoPro.id}'">
								<img class="rounded-circle" src="${artistNoPro.photo}">
				      			<h5>${artistNoPro.user.username}</h5>
				      		</div>
				      	</c:if>
		      		</c:forEach>
		      		
		      	</div>
		      	<sec:authorize access="hasAuthority('admin')">
			      	<h2 style="text-align: center;margin:2rem 0;">Artistas Desactivados</h2>	
    				<div  class="lista">
			      		<c:forEach items="${artistsDisabled}" var="artistDisabled">
				      			<div class="element-wrapper d-flex flex-column align-items-center justify-content-evenly" onClick="location.href='/artists/show/${artistDisabled.id}'">
									<img class="rounded-circle" src="${artistDisabled.photo}">
					      			<h5>${artistDisabled.user.username}</h5>
					      		</div>
			      		</c:forEach>
			      	</div>
			     </sec:authorize>		      		
			</div>
			
			<!-- Listado Mobile -->
			<div id="list-wrap-mobile" class="padding-footer">
				<c:forEach items="${artistsPro}" var="artistPro">
		      		<c:if test="${artistPro.user.enabled}">
		      			<div class="element-wrapper d-flex justify-content-between align-items-center w-100 " onClick="location.href='/artists/show/${artistPro.id}'">
							<img class="rounded-circle" src="${artistPro.photo}">
			      			<h5>${artistPro.user.username}</h5>
			      		</div>
  							<hr class="m-3 linea-hor linea-hor-pro">
			      	</c:if>
	      		</c:forEach>
	      		<c:forEach items="${artistsNoPro}" var="artistNoPro">
		      		<c:if test="${artistNoPro.user.enabled}">
		      			<div class="element-wrapper d-flex justify-content-between align-items-center w-100 " onClick="location.href='/artists/show/${artistNoPro.id}'">
							<img class="rounded-circle" src="${artistNoPro.photo}">
			      			<h5>${artistNoPro.user.username}</h5>
			      		</div>   
     			      		<hr class="m-3 linea-hor">
			      		 		
			      	</c:if>
	      		</c:forEach>
	      		
  				<sec:authorize access="hasAuthority('admin')">
		      		<h2 style="text-align: center;margin: 2rem 0;font-size: 1.8rem;">Artistas Desactivados</h2>
		      		<c:forEach items="${artistsDisabled}" var="artistDisabled">
			      			<div class="element-wrapper d-flex flex-column align-items-center justify-content-evenly" onClick="location.href='/artists/show/${artistDisabled.id}'">
								<img class="rounded-circle" src="${artistDisabled.photo}">
				      			<h5>${artistDisabled.user.username}</h5>
				      		</div>
				      		<hr class="m-3 linea-hor">	
		      		</c:forEach>
		      	</sec:authorize>
			</div>
			
			<div id="boton-up" onClick="location.href='/artists/list#top'">
				<span>^</span>
			</div>
			
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/views/footer.jsp" ></jsp:include>
	
</body>
</html>