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
		<div id="left_menu">
			<div class="thumb_and_desc">
				<div class="thumb">
					<img onmouseover="hoverDotPlot('340vs380.png');" 
						onmouseout="hideDotPlot();"
						src="css/dotplot_images/340vs380_thump.png" 
						alt="DotPlot 1"/>
				</div>
				<div class="thumb_desc">
					Spezifikation: A380 versus A340 Airbusse im Vergleich.
					Der DotPlot stammt von Markus Gutmann. 
				</div>
			</div>
			
			<div class="thumb_and_desc">
				<div class="thumb">
					<img onmouseover="hoverDotPlot('eStudyQuellen.png');" 
						onmouseout="hideDotPlot();"
						src="css/dotplot_images/eStudyQuellen_thump.png" 
						alt="DotPlot 2"/>
				</div>
				<div class="thumb_desc">
					Der Dotplot zeigt den Selbstvergleich der 
					eStudy-Quellen.
				</div>
			</div>
			
			<div class="thumb_and_desc">
				<div class="thumb">
					<img onmouseover="hoverDotPlot('lottozahlen.png');" 
						onmouseout="hideDotPlot();"
						src="css/dotplot_images/lottozahlen_thump.png" 
						alt="DotPlot 3"/>
				</div>
				<div class="thumb_desc">
					50 Jahre Lottozahlen!
					Der DotPlot	stammt von Timo Lautenschläger.
				</div>
			</div>
			
			<div class="thumb_and_desc">
				<div class="thumb">
					<img onmouseover="hoverDotPlot('wahlprogramme.png');" 
						onmouseout="hideDotPlot();"
						src="css/dotplot_images/wahlprogramme_thump.png" 
						alt="DotPlot 4"/>
				</div>
				<div class="thumb_desc">
					Reihenfolge: SPD - FDP - CDU/CSU - Grüne.
					Der DotPlot stammt von Richard Bayer.
				</div>
			</div>
		</div>
	
		<div id="right_text">
			<p>
			Ursprünglich stammen Dotplots aus der Genetik, um einen Vergleich von Genen durchzuführen 
			und die Suche nach Ähnlichkeiten in den Erbinformationen zu vereinfachen. 
			Dabei werden die Ketten der Elemente (Guanin, Cytosin, Adenin und Thymin) miteinander verglichen
			und die graphische Repräsentation des Vergleichs geliefert.
			Da die Gene oft sehr groß sind, kann eine solche Visualisierung einen besseren Überblick geben.
			Man kann natürlich auch verallgemeinernd jede Art einer Anordnung von Elementen oder Tokens miteinander
			vergleichen, wie z.B. die Wörter einer Sprache.
			</p><p>
			Grundsätzlich erfolgt die Visualisierung so, dass die Token-Ketten zu einer 2-dimensionalen Matrix aufgespannt
			werden und jedes Token auf der X-Achse mit jedem auf der Y-Achse verglichen wird.
			Falls die Tokens gleich sind entsteht an diesem Punkt eine Markierung.
			</p><p>
			Auf der linken Seite sind einige Beispiel-DotPlots zu sehen, die von Studenten der Fachhochschule Gießen-Friedberg
			in verschiedenen Vorlesungen erstellt wurde. 
			Bei diesen DotPlots handelt es sich um ein paar außergewöhnliche, wie auch der Beschreibung zu entnehmen ist.
			</p><p>
			Wenn Sie ein eigenes DotPlot erstellen wollen, können Sie diesen Dienst <a href="create.jsf">hier testen</a> oder 
			Sie klicken in der Menüleiste auf <b>DotPlot erstellen</b>. 
			</p>
		</div>
		
		<div id="hover">
			<img id="hoverimg" src="" alt="Dotplot" />
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