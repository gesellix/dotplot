<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" type="text/css" rel="stylesheet" />
<title>DotPlot as a Service</title>
</head>
<body>
	<jsp:include page="templates/headline.jsp" flush="false"></jsp:include>
	<f:view>
	<div id="content">
		<h3>Dieser Dienst wird betrieben von:</h3>
		<div>
			<h:outputText value="#{settings.name}"></h:outputText>
		</div>
		<div>
			<h:outputLink value="mailto:#{settings.email}">
				<h:outputText value="#{settings.email}"></h:outputText>
			</h:outputLink>
		</div>
		<div>
			<h:outputText value="#{settings.zip}"></h:outputText>
			&nbsp;
			<h:outputText value="#{settings.place}"></h:outputText>
		</div>
		
	</div>
	</f:view>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/dpaas.js"></script>
</body>
</html>