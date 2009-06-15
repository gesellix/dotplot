package org.dotplot.eclipse.actions;

import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * Created on 08.08.2003.
 * <p />
 * (C) by Friederich Kupzog Elektronik & Software Köln All rigths reserved.
 */
public class ToolTip implements Listener {
    // Es gibt nur einen Tip
    static private Shell tip = null;

    static private Composite comp = null;

    static private Label label = null;

    static private Label icon = null;

    static private Control lastControl = null;

    // Das Control, zu dem der Tip gezeigt wird:
    private Control control;

    // Der ToolText
    private String tipText; // der komplette Text

    private String[] lines; // Aufspaltung in Zeile nur, wenn Fenster zu klein

    int currentLine;

    /**
     * Konstruktor. Legt einen neuen ToolTip zum Control control an. Die
     * Instanziierung genügt, der Tip registriert sich als Listener selbst beim
     * control.
     * 
     * @param control
     *            "parent" control
     * @param tipText
     *            the tooltip text
     */
    public ToolTip(Control control, String tipText) {
	this.control = control;
	this.tipText = tipText;
	control.addListener(SWT.Dispose, this);
	control.addListener(SWT.KeyDown, this);
	control.addListener(SWT.MouseMove, this);
	control.addListener(SWT.MouseHover, this);
	currentLine = 0;
    }

    private void createContents() {
	// Der Listener, der überprüft, ob der Tip noch gezeigt werden soll
	final Listener myListener = new Listener() {
	    public void handleEvent(Event event) {
		Shell shell = ((Composite) event.widget).getShell();
		switch (event.type) {
		// case SWT.MouseDown:
		case SWT.MouseExit:
		    shell.dispose();
		    break;
		case SWT.MouseDoubleClick:
		    shell.dispose();
		    break;
		case SWT.KeyDown:
		    if (event.keyCode == 16777218) { // Cursor Down
			if (currentLine <= lines.length - 30) {
			    currentLine++;
			    label.setText(getMyLinesFrom(currentLine));
			}
		    } else if (event.keyCode == 16777217) { // Cursor Up
			if (currentLine > 0) {
			    currentLine--;
			    label.setText(getMyLinesFrom(currentLine));
			}
		    }
		    break;
		}
	    }
	};

	lastControl = control;
	if (tip != null && !tip.isDisposed()) {
	    tip.dispose();
	}
	tip = new Shell(control.getShell(), SWT.ON_TOP);
	tip.setLayout(new FillLayout());

	comp = new Composite(tip, SWT.NONE);
	comp.addListener(SWT.MouseExit, myListener);
	comp.addListener(SWT.MouseDoubleClick, myListener);
	comp.addListener(SWT.KeyDown, myListener);

	RowLayout rowLayout = new RowLayout();
	rowLayout.type = SWT.VERTICAL;
	comp.setLayout(rowLayout);
	comp.setBackground(control.getDisplay().getSystemColor(
		SWT.COLOR_INFO_BACKGROUND));

	// Label
	label = new Label(comp, SWT.NONE);
	label.setForeground(control.getDisplay().getSystemColor(
		SWT.COLOR_INFO_FOREGROUND));
	label.setBackground(control.getDisplay().getSystemColor(
		SWT.COLOR_INFO_BACKGROUND));

	currentLine = 0;
	label.setText(getMyLinesFrom(currentLine));

	// Icon
	icon = new Label(comp, SWT.NONE);
	icon.setBackground(control.getDisplay().getSystemColor(
		SWT.COLOR_INFO_BACKGROUND));
	icon.setForeground(control.getDisplay().getSystemColor(SWT.COLOR_BLUE));

	if (lines != null) {
	    icon.setText("source files (line number)                     "
		    + "                       use arrow keys to scroll");
	} else {
	    icon.setText("source files (line number)");
	}
    }

    private String getMyLinesFrom(int line) {
	if (lines == null) {
	    return tipText;
	} else {
	    StringBuffer erg = new StringBuffer();
	    int to = line + 30;
	    if (to > lines.length) {
		to = lines.length;
	    }
	    for (int i = line; i < to; i++) {
		erg.append(lines[i]);
		// erg.append("\n");
	    }
	    return erg.toString();
	}
    }

    /**
     * Listener-Implementierung für ToolTip.
     * 
     * @param event
     *            the event to be handled
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
	switch (event.type) {
	case SWT.Dispose:
	case SWT.KeyDown:
	case SWT.MouseMove:
	    // Tip abbauen
	    if (tip == null) {
		break;
	    }
	    tip.dispose();
	    tip = null;
	    label = null;
	    icon = null;
	    comp = null;
	    lastControl = null;
	    lines = null;
	    break;
	case SWT.MouseHover:
	    // Neu-Anlegen
	    if (lastControl != control) {
		prepareText();
		createContents();
		showContents(event);
	    }
	    break;
	}
    }

    private void prepareText() {
	StringTokenizer alles = new StringTokenizer(tipText + "\n", "\n", true);
	StringTokenizer textZeilen = new StringTokenizer(tipText + "\n", "\n",
		false);
	int zeilenAnz = alles.countTokens() - textZeilen.countTokens();

	if (zeilenAnz > 30) { // Obergrenze angezeigte Zeilenzahl
	    lines = new String[zeilenAnz];
	    for (int i = 0; i < lines.length; i++) {
		String tmp = alles.nextToken();
		if (!tmp.equals("\n")) {
		    lines[i] = tmp + alles.nextToken();
		} else {
		    lines[i] = tmp;
		}
	    }
	}
    }

    private void showContents(Event event) {
	// Größe und Position
	Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	size.x += 20;
	Point pt = control.toDisplay(event.x, event.y);
	Rectangle pos = new Rectangle(pt.x + 10, pt.y + 5, size.x, size.y);

	if (pos.x + pos.width > control.getDisplay().getBounds().width) {
	    pos.x = control.getDisplay().getBounds().width - pos.width;
	}

	if (pos.y + pos.height > control.getDisplay().getBounds().height) {
	    pos.y = control.getDisplay().getBounds().height - pos.height;
	}

	if (pos.x <= 0) {
	    pos.x = 1;
	}

	if (pos.y <= 0) {
	    pos.y = 1;
	}

	tip.setBounds(pos);
	Point lsize = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	label.setSize(lsize.x + 20, lsize.y);

	// verzögertes Anzeigen
	control.getDisplay().timerExec(750, new Runnable() {
	    public void run() {
		if (tip != null) {
		    tip.setVisible(true);
		    if (lines != null) {
			tip.forceFocus();
		    }
		}
	    }
	});
    }
}
