/**
 * 
 */
package org.dotplot.core.services;

import java.util.Collection;
import java.util.Vector;

/**
 * Default implementation of the <code>IServiceHotSpot</code> interface.
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class ServiceHotSpot implements IServiceHotSpot {

	/**
	 * The if of the <code>ServiceHotSpot</code>.
	 */
	private String id;
	
	/**
	 *  The <code>Class</code> ot the <code>ServiceHotSpot</code>'s <code>Extentions</code>.
	 */
	private Class<?> extentionClass;
	
	/**
	 * Storrage for <code>Extentions</code>.
	 * @see Extention
	 */
	private Vector<Extention> extentions;

	/**
	 * Creates a new <code>ServiceHotSpot</code>.
	 * @param id - the if fd the <code>ServiceHotSpot</code>. 
	 * @param extentionClass The <code>Class</code> ot the <code>ServiceHotSpot</code>'s <code>Extentions</code>. 
	 */
	public ServiceHotSpot(String id, Class extentionClass) {
		super();
		if(id == null || extentionClass == null) throw new NullPointerException();
		this.id = id;
		this.extentionClass = extentionClass;
		this.extentions = new Vector<Extention>();
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#getID()
	 */
	public String getID() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#getExtentionClass()
	 */
	public Class getExtentionClass() {
		return this.extentionClass;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#isValidExtention(java.lang.Object)
	 */
	public boolean isValidExtention(Class extentionClass) {
		if(extentionClass == null) throw new NullPointerException();
		return this.extentionClass.isAssignableFrom(extentionClass);
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#isValidExtention(java.lang.Object)
	 */
	public boolean isValidExtention(Extention extention) {
		return this.isValidExtention(extention.getExtentionObject().getClass());
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#getExtentions()
	 */
	public Collection<Extention> getAllExtentions() {
		return this.extentions;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#getActiveExtentions()
	 */
	public Collection<Extention> getActiveExtentions() {
		Vector<Extention> activeExtentions = new Vector<Extention>(this.extentions.size());
		Extention[] array = this.extentions.toArray(new Extention[0]);
		for(int i = 0; i < array.length; i++){
			if(array[i].getActivator().isActivated()){
				activeExtentions.add(array[i]);
			}
		}
		return activeExtentions;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#addExtention(org.dotplot.services.Extention)
	 */
	public void addExtention(Extention extention) {
		if(extention == null) throw new NullPointerException();
		if(! this.isValidExtention(extention)) throw new IllegalArgumentException();
		this.extentions.add(extention);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#removeExtention(org.dotplot.services.Extention)
	 */
	public void removeExtention(Extention extention) {
		if(extention == null) return;
		while(this.extentions.contains(extention)){
			this.extentions.remove(extention);
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.services.IServiceHotSpot#getObjectsFromActivatedExtentions()
	 */
	public Collection<Object> getObjectsFromActivatedExtentions() {
		Extention[] extentions = this.getActiveExtentions().toArray(new Extention[0]);
		Vector<Object> objects = new Vector<Object>(extentions.length);
		for(int i = 0; i < extentions.length; i++){
			objects.add(extentions[i].getExtentionObject());
		}
		return objects;
	}
}
