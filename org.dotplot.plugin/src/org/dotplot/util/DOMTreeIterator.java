package org.dotplot.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Node;

/**
 * Instances of this class provide iteratorfunctionality for DOM-Trees.
 * <p>
 * Starting from a single <code>Node</code> the <code>Iterator</code> traverses
 * through all their childnodes.
 * </p>
 * <p>
 * {@link java.util.Iterator#remove()} is unsupported.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 * @see org.w3c.dom.Node
 */
public class DOMTreeIterator implements Iterator {

    /**
     * The base-<code>Node</code> of the <code>DOMTreeIterator</code>.
     */
    private Node baseNode;

    /**
     * The current processed <code>Node</code>.
     */
    private Node current;

    /**
     * The next returned <code>Node</code>. This is the <code>Node</code> under
     * the iterationpointer.
     */
    private Node next;

    /**
     * Creates a new <code>DOMNodeIterator</code> object assigned to a base-
     * <code>Node</code>;
     * 
     * @param baseNode
     *            - The base-<code>Node</code> of the
     *            <code>DOMTreeIterator</code>.
     */
    public DOMTreeIterator(Node baseNode) {
	this.baseNode = baseNode;
	this.reset();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
	return this.next != null;
    }

    /**
     * Moves the iterationpointer to the next sibling of the last returned
     * <code>Node</code>. Usually the pointer is set to the next
     * <code>Node</code> of the current DOM-tree-hierarchy level, skipping the
     * childnodes of the last returned <code>Node</code>. If the
     * DOM-tree-hierarchy level has no <code>Nodes</code> left the iteration
     * pointer is moved up until a <code>Node</code> is found, or the end of the
     * DOM-tree is reached.
     */
    public void moveToNextSibling() {
	if (current != null) {
	    next = current.getNextSibling();
	    while (next == null) {
		current = current.getParentNode();
		if (current == null || current == this.baseNode) {
		    next = null;
		    break;
		}
		next = current.getNextSibling();
	    }
	    ;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    public Object next() throws NoSuchElementException {
	if (this.hasNext()) {
	    Node result = next;
	    current = next;
	    next = current.getFirstChild();
	    if (next == null) {
		this.moveToNextSibling();
	    }
	    return result;
	} else {
	    throw new NoSuchElementException();
	}
    }

    /**
     * Returns the next <code>Node</code> of the DOM-tree.
     * 
     * @return - the next <code>Node</code>.
     */
    public Node nextNode() {
	return (Node) this.next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#remove()
     */
    public void remove() throws UnsupportedOperationException {
	throw new UnsupportedOperationException();
    }

    /**
     * Resets the <code>DOMTreeIterator</code>. The iterationpointer is set to
     * the <code>DOMTreeIterator</code>'s base-<code>Node</code>.
     */
    public void reset() {
	this.next = baseNode;
    }
}
