/**
 * 
 */
package org.dotplot.image.tests;

import java.util.Vector;

import org.dotplot.core.IDotplot;
import org.dotplot.core.ISourceType;
import org.dotplot.core.services.IRessource;
import org.dotplot.fmatrix.DefaultFMatrixConfiguration;
import org.dotplot.fmatrix.FMatrixManager;
import org.dotplot.fmatrix.ITypeTableNavigator;
import org.dotplot.image.IQImageConfiguration;
import org.dotplot.image.QImageConfiguration;
import org.dotplot.image.QImageTaskPart;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TextType;

import junit.framework.TestCase;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class QImageTaskPartTest extends TestCase {

	private QImageTaskPart part;
	private ITypeTableNavigator navigator;
	private ITokenStream stream;
	private IQImageConfiguration config;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.stream = this.stream = new ITokenStream(){

			private String[] strings = {"to","be","or","not","to","be"}; 
			private int i = 0;
			
			public Token getNextToken() throws TokenizerException {
				if(i < strings.length){
					return new Token(this.strings[i++],0,1);
				}
				else {
					return new EOSToken();
				}
			}

			public ISourceType getStreamType() {
				return TextType.type;
			}};
		FMatrixManager manager = new FMatrixManager(this.stream, new DefaultFMatrixConfiguration());
		manager.addTokens();
		this.navigator = manager.getTypeTableNavigator();
		this.config = new QImageConfiguration();
		this.part = new QImageTaskPart("part 1", this.navigator, this.config);
	}

	/*
	 * Test method for 'org.dotplot.image.QImageTaskPart.QImageTaskPart(String)'
	 */
	public void testQImageTaskPart() {
		assertSame(this.navigator, this.part.getNavigator());
		assertSame(this.config, this.part.getConfiguration());
		
		try {
			new QImageTaskPart("test", null, this.config);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}
		
		try {
			new QImageTaskPart("test", this.navigator, null);
			fail("NullPointerException must be thrown");
		}
		catch (NullPointerException e) {
			/*all clear*/
		}
		catch (Exception e) {
			fail("wrong Exception");
		}

	}

	/*
	 * Test method for 'org.dotplot.image.QImageTaskPart.getResult()'
	 */
	public void testGetResult() {
		assertNull(this.part.getResult());
		this.part.run();
		Object result = this.part.getResult();
		assertNotNull(result);
		assertTrue(result instanceof IDotplot);
	}

	/*
	 * Test method for 'org.dotplot.image.QImageTaskPart.setLocalRessources(Collection<? extends IRessource>)'
	 */
	public void testSetLocalRessources() {
		try {
			this.part.setLocalRessources(null);
			this.part.setLocalRessources(new Vector<IRessource>());
		}
		catch (Exception e) {
			fail("no exception:" + e.getClass().getName() + ":"
					+ e.getMessage());
		}
	}

}
