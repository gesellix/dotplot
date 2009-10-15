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
<title>DotPlot as a Service</title>
</head>
<body>
	<jsp:include page="templates/headline.jsp" flush="false"></jsp:include>
	<f:view>
	<div id="content">
		<div class="form">
		<h:form id="creator" enctype="multipart/form-data">
		
			<div class="headline">
				Persönliche Angaben
			</div>
			<div id="nametab">
					Bitte geben sie ihren Namen und ihre EMailadresse an:
				<div>
					<label>Name:</label>
					<h:inputText id="name" 
						value="#{create.name}" 
						validator="#{create.nameValidate}"
						required="true"></h:inputText>
					<h:message for="name" 
						errorClass="msg_error" 
						fatalClass="msg_fatal" 
						infoClass="msg_info" 
						styleClass="msg" 
						warnClass="msg_warn"></h:message>
				</div>
				<div>
					<label>EMail:</label>
					<h:inputText id="email" 
						value="#{create.email}" 
						validator="#{create.emailValidate}"
						required="true"></h:inputText>
					<h:message for="email" 
						errorClass="msg_error" 
						fatalClass="msg_fatal" 
						infoClass="msg_info" 
						styleClass="msg" 
						warnClass="msg_warn"></h:message>
				</div>
				<div><div class="nexttab" onclick="changeTab('nametab','agbtab');">Weiter</div></div>
			</div>
			<div class="headline">
				AGB
			</div>
			<div id="agbtab" style="display:none;">
				Um den Dienst nutzen zu können, müssen Sie den Nutzungsbedingungen zustimmen!
				<div>
					<ol>
						<li>Ihr Name und Ihre E-Mail-Adresse werden benötigt. Diese werden in der DotPlot-Datenbank gespeichert und nur zu Auswertungszwecken verwendet. Diese Daten werden also nicht weitergegeben.
						</li><li> Um einen DotPlot-Job durchzuführen, müssen Sie eine Datei hochladen. Auch diese Datei wird mit dem Endresultat, dem DotPlot-Bild in der Datenbank gespeichert. Dies geschieht im Sinne des Aufbewahrungszweckes.
						</li><li> Erstellte Bilder die in der Datenbank gespeichert werden können gegebenenfalls, ohne Angabe von Autor, als Werbestücke für das DotPlot-Programm von den Distributoren verwendet werden.
						</li><li> Die erstellten Bilder stehen Ihnen zur freien Verfügung. Sie können diese veröffentlichen, verbreiten und tauschen mit wem und wann Sie wollen.
						</li><li> Der hier verwendete Service stellt nicht die Vollversion des DotPlot-Programmes dar. Es ist eine Demo-Version die als Webservice nutzbar ist. Es werden nur ausgewählte Funktionen des DotPlots genutzt.
						</li><li> DotPlot ist Open-Source-Software. Sie können das Programm herunterladen und verwenden. Die Projektseite ist unter http://www.dotplot.org zu finden. 
						</li><li> Fertig erstellte DotPlots können auch von anderen Benutzern eingesehen werden. Hierbei handelt es sich nur um die Bilder, nicht um die verwendeten Dateien zum Plotten.
						</li>
					</ol>
				</div>
				<div>
					<t:selectOneMenu id="tos" 
						value="#{create.tosAccepted}" 
						validator="#{create.tosValidate}"
						required="true">
						<f:selectItem id="si1" itemLabel="Ja, ich stimme zu!" itemValue="1" />
						<f:selectItem id="si2" itemLabel="Nein, ich stimme nicht zu!" itemValue="0" />
					</t:selectOneMenu>
					<h:message for="tos" 
						errorClass="msg_error" 
						fatalClass="msg_fatal" 
						infoClass="msg_info" 
						styleClass="msg" 
						warnClass="msg_warn"></h:message>
				</div>
				<div><div class="nexttab" onclick="changeTab('agbtab','filestab');">Weiter</div></div>
			</div>
			<div class="headline">
				Dateiauswahl
			</div>
			<div id="filestab" style="display:none;">
				W&auml;hlen Sie nun die Datei aus. Sie haben die M&ouml;glichkeit, bis zu 200 kB zu verwenden.
				<c:set var="maxFiles" value="#{create.maxFiles}"></c:set>
				<c:forEach var="id" items="${maxFiles}">
					<div>
						<t:inputFileUpload storage="file" value="#{create.file}" id="file"></t:inputFileUpload>
					</div>
				</c:forEach>
				<div><div class="nexttab" onclick="changeTab('filestab','settingstab');">Weiter</div></div>
			</div>
			<div class="headline">
				Weitere Einstellungen
			</div>
			<div id="settingstab" style="display:none;">
				<div>
					<label>Farbprofil:</label>
					<h:selectOneMenu value="#{create.jobLuts}">
						<f:selectItem itemValue="Rot" />
						<f:selectItem itemValue="Gelb" />
						<f:selectItem itemValue="Grün" />
						<f:selectItem itemValue="Blau" />
					</h:selectOneMenu>
				</div>
				<div>
					<label>Art der Dateien:</label>
					<h:selectOneMenu value="#{create.jobType}">
						<f:selectItem itemValue="Text" />
						<f:selectItem itemValue="C++" />
						<f:selectItem itemValue="C" />
						<f:selectItem itemValue="HTML" />
						<f:selectItem itemValue="Java" />
						<f:selectItem itemValue="PHP" />
						<f:selectItem itemValue="XML" />
					</h:selectOneMenu>
				</div>
				<div>
					<h:commandButton value="Fertigstellen" action="#{create.finish}" styleClass="nexttab"></h:commandButton>
				</div>
			</div>
			
		</h:form>
		
		</div>
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