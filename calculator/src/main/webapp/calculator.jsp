<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="assets/img/favicon.png">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>My Web-Calculator</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />

	<!--     Fonts and icons     -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

	<!-- CSS Files -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/material-kit.css" rel="stylesheet"/>
</head>

<body>

<script type="text/javascript" src="assets/js/view-manipulator-jquery.js"></script>

<div class="wrapper">
	<div class="header header-filter" 
		style="background-image: url('https://images.unsplash.com/photo-1423655156442-ccc11daa4e99?crop=entropy&dpr=2&fit=crop&fm=jpg&h=750&ixjsv=2.1.0&ixlib=rb-0.3.5&q=50&w=1450');">
    </div>
	<!-- you can use the class main-raised if you want the main area to be as a page with shadows -->
	<div class="main main-raised">
		<div class="container">
			<div class="page-header">
				<h2>Welcome to my web-calculator! <small>The best calculator in the university...</small></h2>
			</div>
			<form id="calc-form" method="POST" action="<c: url value="/calculator"/>">
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group label-floating">
							<label class="control-label">Put your number here!</label>
							<input id="input-to-validate" type="text" class="form-control" name="digit">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-5">
						<button type="submit" class="btn btn-primary" name="mathfunction" value="+">
							<i class="material-icons">add</i>
						</button>
						<button type="submit" class="btn btn-info" name="mathfunction" value="-">
							<i class="material-icons">remove</i>
						</button>
						<button type="submit" class="btn btn-success" name="mathfunction" value="/">
							<i class="material-icons">more_vert</i>
						</button>
						<button type="submit" class="btn btn-warning" name="mathfunction" value="*">
							<i class="material-icons">close</i>
						</button>
						<button type="submit" class="btn btn-danger" name="mathfunction" value="=">
							<i class="material-icons">drag_handle</i>
						</button>
						<a class="btn btn-default col-sm-5" href="/web-calculator-bootstrap/calc">Clear all</a>
					</div>
				</div>
			</form>
			<h3>
				<% if (((models.Command) request.getAttribute("command")).getMathFunction() == null) { %>
					You have no arithmetic functions yet.
				<% } else { %>
					Your last arithmetic function: ${command.mathFunction} 
				<% } %>
			</h3>
			<h3>
				<% if (((models.Command) request.getAttribute("command")).getNumber() == null) { %>
					You have no numbers yet.
				<% } else { %>
					Your last number: ${command.number} 
				<% } %>
			</h3>
			<a class="btn btn-warning" href="<c:url value="/history"/>">History</a>
			<footer class="footer">
			  <div class="container">
			    <span class="text-muted">&copy; 2017 Oleg Bedrin</span>
			  </div>
			</footer>		
		</div>
	</div>
</div>
<!--   Core JS Files   -->
<script src="assets/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/material.min.js"></script>

<!--  Plugin for the Sliders, full documentation here: http://refreshless.com/nouislider/ -->
<script src="assets/js/nouislider.min.js" type="text/javascript"></script>

<!--  Plugin for the Datepicker, full documentation here: http://www.eyecon.ro/bootstrap-datepicker/ -->
<script src="assets/js/bootstrap-datepicker.js" type="text/javascript"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="assets/js/material-kit.js" type="text/javascript"></script>
</body>
</html>