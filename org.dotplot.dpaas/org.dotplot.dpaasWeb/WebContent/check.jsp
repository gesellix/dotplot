<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" type="text/css" rel="stylesheet" />
<title>DotPlot as a Service</title>
</head>
<body>
	<jsp:include page="templates/headline.jsp" flush="false"></jsp:include>
	<f:view>
	<% session.invalidate(); %>
	<div id="content">
		<div class="form">
			<div>
				Herzlich willkommen!
				Sie haben hier die Möglichkeit, den Status eines DotPlots abzurufen.
			</div>
			<h:form id="check">
				<div>
					Sie möchten den Status eines DotPlots erfahren? Geben Sie die Job-ID hier ein:
				</div>
				<div>
					<h:inputText value="#{check.jobId}" style="width: 10em; text-align: center;"></h:inputText>
					<h:commandButton value="Status abrufen" action="#{check.checkJob}"></h:commandButton>
				</div>
			</h:form>
		</div>
	</div>
	</f:view>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/dpaas.js"></script>
</body>
</html>