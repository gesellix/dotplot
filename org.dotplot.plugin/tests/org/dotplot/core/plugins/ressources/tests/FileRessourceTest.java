/**
 * 
 */
package org.dotplot.core.plugins.ressources.tests;

import java.io.File;
import java.io.InputStream;

import org.dotplot.core.plugins.ressources.FileRessource;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FileRessourceTest extends TestCase {

	private File file, dir;
	
	private FileRessource fr;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.file = new File("./testfiles/ressources/test.txt");
		this.dir = new File("./testfiles/ressources");
		this.fr = new FileRessource(this.file);
	}

	/*
	 * Test method for 'org.dotplot.ressources.FileRessource.FileRessource(File)'
	 */
	public void testFileRessourceFile() {
		try {
			new FileRessource((File)null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource(this.dir);
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource(new File("bla"));
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource(file);
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
		
	}

	/*
	 * Test method for 'org.dotplot.ressources.FileRessource.FileRessource(String)'
	 */
	public void testFileRessourceString() {
		try {
			new FileRessource((String)null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource("./testfiles/ressources");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource("bla");
			fail("IllegalArgumentException must be thrown");
		}
		catch (IllegalArgumentException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new FileRessource("./testfiles/ressources/test.txt");
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.ressources.FileRessource.getInputStream()'
	 */
	public void testGetInputStream() {
		try {
			InputStream in = this.fr.getInputStream();
			assertNotNull(in);
			assertEquals(116, in.read());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

	/*
	 * Test method for 'org.dotplot.ressources.FileRessource.getURL()'
	 */
	public void testGetURL() {
		try {
			assertNotNull(this.fr.getURL());
			assertEquals(this.file.toURL(), this.fr.getURL());			
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}
	
	public void testGetFile(){
		assertSame(this.file, this.fr.getFile());
	}
}
