<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
</head>
<style>
		.background-image{
			z-index: -1;
		   background-image: url(https://www.indecsur.cl/wp-content/uploads/2020/03/272910-desk-laptop.jpg);
		   width: 100%;
		   position: absolute;
		   height: 100%;
		   filter: blur(5px);
		   background-size: contain;
		}
		
		.form-wrapper{
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 1.5rem;
			width:30rem;
			height: fit-content;
			box-shadow: 0 0 10px black;
			background-color: var(--gris);
			border-radius: 20px;
			margin: 1rem 0 2rem 0;
		}
		
		
		form input, form textarea, form select{
			width: 60% !important;
		}
		
		form div{
			margin: 0.5rem 0;
		}
		
		form {
			margin: 0;
		}
		
		form label{
			font-weight: bold;
		}
	
		
		@media(max-width: 1160px) {
			
			.padding-nav{
				padding-top: 6rem !important;
			}
		
			.form-wrapper{
				box-shadow: unset;
				border-radius: unset;
				margin: unset;
			}
		
		}
		
</style>
<body>
	<jsp:include page="/WEB-INF/views/navbar.jsp"></jsp:include>
	<div class="background-image"></div>
	
	<c:choose>
		<c:when test="${buttonCreate == false}">
			<c:set var="formAction" value="${project.id}" />
		</c:when>
		<c:otherwise>
			<c:set var="formAction" value="create" />
		</c:otherwise>
	</c:choose>

	<div class="padding-nav padding-footer d-flex justify-content-center align-items-center">

		<div class="form-wrapper">
			<h3>Crear/editar proyecto</h3>
	
			<form:form method="POST" action="${formAction}" modelAttribute="project" style="width:100%">
                <div class="d-flex justify-content-between align-items-center">
					<form:label class="rounded-pill form-control-label" path="title">T&iacutetulo</form:label>
					<form:input class="form-control" value="${project.title}" placeholder="Titulo" type="text" path="title"></form:input>
				</div>
				<form:errors style="color:red" path="title"/>
						
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex flex-wrap ">
						<form:label class="rounded-pill form-control-label" path="genre">G&eacutenero</form:label>
					</div>
					<form:select value="${project.genre}" class="form-control" path="genre">
						<form:option value="" selected="true">Selecciona un g&eacutenero</form:option>
						<c:forEach items="${genres}" var="genre">
							<form:option value="${genre}">${genre}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<form:errors style="color:red" path="genre"/>
							
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex flex-wrap ">
						<form:label class="rounded-pill form-control-label" path="description">Resumen</form:label>
					</div>
					<form:textarea class="form-control" value="${project.description}"
						placeholder="Descripcion" type="text" path="description"></form:textarea>
				</div>
				<form:errors style="color:red" path="description" />
				
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex flex-wrap ">
						<form:label class="rounded-pill form-control-label" path="photo">A&ntildeade tu foto</form:label>
					</div>
					<form:input class="form-control" value="${project.photo}"
						placeholder="url" type="text" path="photo"></form:input>
				</div>
				<form:errors style="color:red" path="photo" />
				
				<div class="d-flex justify-content-center align-items-center" style="margin-top: 2rem;">
					<form:button class="boton btn rounded-pill">Guardar</form:button>
				</div>
			</form:form>
		</div>

	</div>
	<jsp:include page="/WEB-INF/views/footer.jsp" ></jsp:include>
</body>
</html>