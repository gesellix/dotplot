/**
 * 
 */
package org.dotplot.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class JarFileClassLoader extends ClassLoader {

	/**
	 * The associated directory of the <code>DirectoryJarClassLoader</code>.
	 */
	private JarFile jarFile;

	/**
	 */
	public JarFileClassLoader(File jarFile) {
		super();
		if (jarFile == null) {
			throw new NullPointerException();
		}
		try {
			this.jarFile = new JarFile(jarFile);
		}
		catch (IOException e) {
			throw new IllegalArgumentException(
					"The assigned argument must be an existing jar file");
		}
	}

	/**
	 */
	public JarFileClassLoader(File jarFile, ClassLoader loader) {
		super(loader);
		if (jarFile == null || loader == null) {
			throw new NullPointerException();
		}
		try {
			this.jarFile = new JarFile(jarFile);
		}
		catch (IOException e) {
			throw new IllegalArgumentException(
					"The assigned argument must be an existing jar file");
		}
	}

	/**
	 * 
	 */
	public JarFileClassLoader(JarFile jarFile) {
		super();
		if (jarFile == null) {
			throw new NullPointerException();
		}
		this.jarFile = jarFile;
	}

	/**
	 * 
	 */
	public JarFileClassLoader(JarFile jarFile, ClassLoader loader) {
		super(loader);
		if (jarFile == null || loader == null) {
			throw new NullPointerException();
		}
		this.jarFile = jarFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] b = loadClassData(name);
		if (b == null) {
			throw new ClassNotFoundException(name);
		}
		return defineClass(name, b, 0, b.length);
	}

	/**
	 * Returns the <code>File</code> object representing the
	 * <code>DirectoryJarClassLoader</code>'s associated directory.
	 * 
	 * @return - The <code>File</code> representing the directory.
	 */
	public JarFile getJarFile() {
		return this.jarFile;
	}

	/**
	 * Loads the class data. All jar-files of the associated directory are
	 * searched for the wanted class. The first hit is loaded.
	 * 
	 * @param name
	 *            - The name of the class to be loaded.
	 * @return - The binary data of the class.
	 */
	private byte[] loadClassData(String name) {
		ZipEntry entry;
		InputStream in = null;

		// name des zipeintrags im jarfile erzeugen.
		String entryName = name.replaceAll("\\.", "/") + ".class";

		entry = jarFile.getEntry(entryName);

		if (entry != null) {
			// eintrag gefunden: die daten laden und zurück geben.
			byte[] data = new byte[(int) entry.getSize()];
			try {
				in = jarFile.getInputStream(entry);
				in.read(data, 0, data.length);
			}
			catch (IOException e) {
				return null;
			}
			finally {
				if (in != null) {
					try {
						in.close();
					}
					catch (IOException e) {
						/* dann eben nicht */
					}
				}
			}
			return data;
		}
		return null;
	}

}
