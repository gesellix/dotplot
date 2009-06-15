package org.dotplot.util.tests;

import java.io.File;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.dotplot.util.DOMTreeIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 */

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class DOMTreeIteratorTest extends TestCase {

    private Document document;

    private DocumentBuilder builder;

    private File xmlFile;

    private DOMTreeIterator iterator;

    private static final String[] nodeNames = { "#document", "bla", "bla",
	    "test", "testchild", "#text", "test", "testchild", "#text", "test",
	    "testchild", "#text", "testchild", "#text", };

    private static final int[] nodeTypes = { 9, 10, 1, 1, 1, 3, 1, 1, 3, 1, 1,
	    3, 1, 3 };

    /**
     * Constructor for DOMTreeIteratorTest.
     * 
     * @param name
     */
    public DOMTreeIteratorTest(String name) {
	super(name);
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setValidating(true);
	factory.setCoalescing(true);
	factory.setIgnoringComments(true);
	factory.setIgnoringElementContentWhitespace(true);
	try {
	    builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	}
	this.xmlFile = new File("testfiles/utiltests/DOMTreeIteratorTest.xml");

    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.document = builder.parse(xmlFile);
	this.iterator = new DOMTreeIterator(this.document);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    /*
     * Test method for 'DOMTreeIterator.DOMTreeIterator(Node)'
     */
    public void testDOMTreeIterator() {
	DOMTreeIterator iter = null;
	try {
	    iter = new DOMTreeIterator(null);
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
	assertFalse(iter.hasNext());

	try {
	    iter.next();
	    fail("NoSuchElementException should be thrown");
	} catch (NoSuchElementException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong exception was thrown");
	}

	try {
	    iter.nextNode();
	    fail("NoSuchElementException should be thrown");
	} catch (NoSuchElementException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong exception was thrown");
	}

	try {
	    iter = new DOMTreeIterator(document);
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
	assertTrue(iter.hasNext());

	try {
	    assertNotNull(iter.next());
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}

	assertTrue(iter.hasNext());
	try {
	    iter.nextNode();
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
    }

    /*
     * Test method for 'DOMTreeIterator.hasNext()'
     */
    public void testHasNext() {
	try {
	    for (int i = 0; i < nodeNames.length; i++) {
		assertTrue(this.iterator.hasNext());
		this.iterator.nextNode();
	    }
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
	try {
	    assertFalse(this.iterator.hasNext());
	    this.iterator.nextNode();
	    fail("an NoSuchElementException must be thrown");
	} catch (NoSuchElementException e) {
	    /* all clear */
	}
    }

    /*
     * Test method for 'DOMTreeIterator.moveToNextSibling()'
     */
    public void testMoveToNextSibling() {
	Node node1, node2;
	assertTrue(iterator.hasNext());
	assertNotNull(iterator.next());

	assertTrue(iterator.hasNext());
	assertNotNull(iterator.next());

	assertTrue(iterator.hasNext());
	assertNotNull(iterator.next());

	assertTrue(iterator.hasNext());
	node1 = iterator.nextNode();
	assertNotNull(node1);
	assertEquals(nodeNames[3], node1.getNodeName());

	iterator.moveToNextSibling();
	assertTrue(iterator.hasNext());
	node2 = iterator.nextNode();
	assertNotNull(node2);
	assertEquals(nodeNames[3], node2.getNodeName());
	assertEquals(nodeNames[6], node2.getNodeName());
	assertNotSame(node1, node2);
	assertSame(node1.getNextSibling(), node2);
	assertSame(node1, node2.getPreviousSibling());
    }

    /*
     * Test method for 'DOMTreeIterator.next()'
     */
    public void testNext() {
	int i = 0;
	Node node;
	Object o;
	try {
	    while (iterator.hasNext()) {
		o = iterator.next();
		assertNotNull(o);
		assertTrue(o instanceof Node);
		node = (Node) o;
		assertEquals(nodeNames[i], node.getNodeName());
		assertEquals(nodeTypes[i], node.getNodeType());
		i++;
	    }
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
    }

    /*
     * Test method for 'DOMTreeIterator.nextNode()'
     */
    public void testNextNode() {
	int i = 0;
	Node node;
	try {
	    while (iterator.hasNext()) {
		node = iterator.nextNode();
		assertEquals(nodeNames[i], node.getNodeName());
		assertEquals(nodeTypes[i], node.getNodeType());
		i++;
	    }
	    assertEquals(nodeNames.length, i);

	    /* prüfen ob nur kindknoten des basisknotens aufgesucht werden. */

	    // den ersten "test" knoten auswählen
	    DOMTreeIterator iter = new DOMTreeIterator(document.getFirstChild()
		    .getNextSibling().getFirstChild());

	    i = 3; // der start für das namens- und typ-array.
	    while (iter.hasNext()) {
		node = iter.nextNode();
		assertEquals(nodeNames[i], node.getNodeName());
		assertEquals(nodeTypes[i], node.getNodeType());
		i++;
	    }

	    // prüfen, ob rechtzeitig mit dem iterieren aufgehört wurde
	    assertEquals(6, i); // der letzte erreichte knoten ist an stelle 5,
	    // durch i++ muss auf 6 geprüft werden.
	} catch (Exception e) {
	    fail("no exception should be thrown");
	}
    }

    /*
     * Test method for 'DOMTreeIterator.remove()'
     */
    public void testRemove() {
	try {
	    this.iterator.remove();
	    fail("an UnsupportedOperationException must be thrown");
	} catch (UnsupportedOperationException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong exception thrown");
	}
    }

    /*
     * Test method for 'DOMTreeIterator.reset()'
     */
    public void testReset() {
	try {
	    Node node1, node2;
	    node1 = this.iterator.nextNode();
	    node2 = this.iterator.nextNode();
	    assertNotSame("befor reset: both nodes must be diferent", node1,
		    node2);
	    this.iterator.reset();
	    node2 = this.iterator.nextNode();
	    assertSame("after reset: both nodes must be the same", node1, node2);
	} catch (Exception e) {
	    fail("no exception must be thrown");
	}
    }

}
