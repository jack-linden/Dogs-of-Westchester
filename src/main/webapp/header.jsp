<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>


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
</head>
<style>
body {
	margin-top: 100px;
	margin-bottom: 100px;
	margin-right: 150px;
	margin-left: 50px;
}

div {
	width: 900px; 
	margin: 0 auto; 
}

#wrap {
	width: 900px; 
	margin: 0 auto; 
}
</style>
<div class="page-header" id="wrap">
	<h1>
		Dogs of Westchester<small> Searchable database and mapping tool</small>
	</h1>
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
	<p align="right">
		<a href="<%=userService.createLoginURL("/admin")%>">Admin Login</a>
	</p>
	<%
		}
	%>
</div>


