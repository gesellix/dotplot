<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" type="text/css" rel="stylesheet" />
<link href="css/settings.css" type="text/css" rel="stylesheet" />
<title>DotPlot as a Service</title>
</head>
<body onload="updateJobList();">
	<jsp:include page="templates/headline.jsp" flush="false"></jsp:include>
	<c:set var="auth" value="#{login.authenticated}" />
	<c:if test="${auth=='false'}">
		<jsp:forward page="/login.jsp"></jsp:forward>
	</c:if>
	<f:view>
	<div id="content" class="form">
		<div id="jobList">
			&nbsp;
		</div>
		<h:form id="admin">
		<div>
			<h3>Administratorpasswort festlegen</h3>
			<c:set var="passwdIsSet" value="#{settings.passwordIsSet}" />
			<c:if test="${passwdIsSet=='false'}">
			<div class="msg_error">
				Das Passwort ist noch nicht festgelegt. Legen Sie es nun fest, um den Standardwert zu ändern.
			</div>
			</c:if>
			<div>
				<label>Passwort:</label>
				<h:inputSecret id="passworda" value="#{settings.pw1}"></h:inputSecret>
				<h:message for="passworda"></h:message>
			</div>
			<div>	
				<label>&nbsp;</label>
				<h:inputSecret id="passwordb" value="#{settings.pw2}"></h:inputSecret>
				<h:message for="passwordb"></h:message>
			</div>
		</div>
		<div>
			<h3>Zu verwendender DotPlot-Server</h3>
			<div>
				<label>URL:</label>
				<h:inputText id="dotplotserver" value="#{settings.url}" required="true"></h:inputText>
				<h:message for="dotplotserver"></h:message>
			</div>
		</div>
		<div>
			<h3>Dienst-Parameter</h3>
			<div>
				<label>Maximale Dateien:</label>
				<h:selectOneMenu id="maxFiles" value="#{settings.maxFiles}">
					<f:selectItem itemValue="2" />
					<f:selectItem itemValue="5" />
					<f:selectItem itemValue="10" />
				</h:selectOneMenu>
				<h:message for="maxFiles"></h:message>
			</div>
		</div>
		<div>
			<h3>Angaben zum Dienstanbieter gem §5 TMG</h3>
			<div>
				<label>Name:</label>
				<h:inputText id="name" value="#{settings.name}" required="true"></h:inputText>
				<h:message for="name"></h:message>
			</div>
			<div>
				<label>PLZ:</label>
				<h:inputText id="zip" value="#{settings.zip}" required="true"></h:inputText>
				<h:message for="zip" title="Test"></h:message>
			</div>
			<div>
				<label>Ort:</label>
				<h:inputText id="place" value="#{settings.place}" required="true"></h:inputText>
				<h:message for="place"></h:message>
			</div>
			<div>
				<label>Email:</label>
				<h:inputText id="email" value="#{settings.email}" required="true"></h:inputText>
				<h:message for="email"></h:message>
			</div>
		</div>
		<h:commandButton value="Speichern" action="#{settings.saveSettings}"></h:commandButton>
		<div>
			<h:commandButton value="Queue starten" action="#{settings.startQueue}"></h:commandButton>
			<h:commandButton value="Queue stoppen" action="#{settings.stopQueue}"></h:commandButton>
			<h:outputText value="#{settings.queueStatus}"></h:outputText>
		</div>
		</h:form>
	</div>
	<div id="footer">
		<span>
			DotPlot as a Service wird angeboten von:
			<span>
				<h:outputText value="#{settings.name}"></h:outputText>
			</span>
			<span>
				<h:outputLink value="mailto:#{settings.email}">
					<h:outputText value="#{settings.email}"></h:outputText>
				</h:outputLink>
			</span>
		</span>
	</div>
	</f:view>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/dpaas.js"></script>
</body>
</html>