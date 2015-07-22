<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<html>
<head>
	<title>Dogs of Westchester</title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
	<script src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
	<link rel="stylesheet" href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.min.css">
	<link rel="stylesheet" href="/stylesheets/style.css">
	<link href='https://api.tiles.mapbox.com/mapbox.js/v2.2.1/mapbox.css' rel='stylesheet'/>
	<script src='https://api.tiles.mapbox.com/mapbox.js/v2.2.1/mapbox.js'></script>
	<script src="javascript/geolocations.js"></script>
	<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
	<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
	<meta charset=utf-8 />	
</head>
<body>
	<div class="page-header wrap">
		<h1>Dogs of Westchester<small> Searchable database and mapping tool</small></h1>
		<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String authLink = "";
		if (user != null) {
		pageContext.setAttribute("user", user);
		%>
		<p align="right">
			<a href="<%=userService.createLogoutURL("/")%>">Logout</a>
		</p>
		<%
	} else {
	%>
	<p align="right"><a href="<%=userService.createLoginURL("/admin")%>">Admin Login</a></p>
	<%
}
%>
</div>