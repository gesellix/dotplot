package org.dotplot.plugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class DotplotPreferenceInitializer extends AbstractPreferenceInitializer {

	public DotplotPreferenceInitializer() {
		super();

	}

	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode("org.dotplot.plugin");
	}

}
