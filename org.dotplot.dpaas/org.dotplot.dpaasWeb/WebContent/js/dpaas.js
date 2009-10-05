var step = 1;

function nextStep() {
	var url = ('ajax/step' + ++step + '.jsf').toString();
	new Ajax.Updater('content', url);
}

function prevStep() {
	var url = ('ajax/step' + --step + '.jsf').toString();
	new Ajax.Updater('content', url);
}


function changeTab(oldTab, newTab) {
	$(oldTab).style.display = 'none';
	$(newTab).style.display = 'block';
}

function hoverDotPlot( filename ) {
	$('hover').style.display = 'block';
	$('hoverimg').src = 'css/dotplot_images/' + filename;
}

function hideDotPlot( ) {
	$('hover').style.display = 'none';
}