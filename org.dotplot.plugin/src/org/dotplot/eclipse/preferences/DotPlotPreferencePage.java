package org.dotplot.eclipse.preferences;

import org.dotplot.eclipse.DotplotPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p />
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public final class DotPlotPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * 
	 */
	private static final String P_PATH = "pathPreference";

	/**
	 * 
	 */
	private static final String P_BOOLEAN = "booleanPreference";

	/**
	 * 
	 */
	private static final String P_CHOICE = "choicePreference";

	/**
	 * 
	 */
	private static final String P_STRING = "stringPreference";

	/**
	 * Creates a new <code>DotPlotPreferencePage</code>.
	 */
	public DotPlotPreferencePage() {
		super(FLAT);

		try {
			DotplotPlugin plugin = DotplotPlugin.getDefault();
			IPreferenceStore store = plugin.getPreferenceStore();

			setPreferenceStore(store);
			setDescription("A demonstration of a preference page implementation");

			initializeDefaults();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(P_PATH, "&Directory preference:",
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(P_BOOLEAN,
				"&An example of a boolean preference", getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(P_CHOICE,
				"An example of a multiple-choice preference", 1,
				new String[][] { { "&Choice 1", "choice1" },
						{ "C&hoice 2", "choice2" } }, getFieldEditorParent()));
		addField(new StringFieldEditor(P_STRING, "A &text preference:",
				getFieldEditorParent()));
	}

	/**
	 * 
	 * @param workbench
	 */
	public void init(final IWorkbench workbench) {
	}

	/**
	 * Sets the default values of the preferences.
	 */
	private void initializeDefaults() {
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(P_BOOLEAN, true);
		store.setDefault(P_CHOICE, "choice2");
		store.setDefault(P_STRING, "Default value");
	}
}
