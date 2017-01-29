<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<HTML>
<head>
<style>
body {
	background: transparent
		url('${pageContext.request.contextPath}/images/hexellence.png');
	
}
label {
margin:5px;
}
input{
margin:5px;
}
button{
margin-top:15px;
}
#LoginContainer{
    display: block;
  	margin:0 auto;

text-align: center;
border:1px solid black;
width:240px;

position: absolute;
    top: 50%;
    left: 50%;
    margin-right: -50%;
    transform: translate(-50%, -50%) }
}

</style>

</head>
<body>

<div id="LoginContainer">
	<form role="form" method="post" action="login">
	<h2>Logowanie</h2>
	<label>Login: </label> <input type="text" placeholder="login"
		name="login" id="login"> </br>
	<label>Hasło: </label> <input type="password" placeholder="password"
		name="password" id="password">
	</br>
	<button type="submit">Zaloguj</button>
	</div>
	</div>
	</form>
</div>
</body>
</HTML>