/**
 * 
 */
package org.dotplot.core;

import java.util.Collection;

/**
 * Default implementation of the interface <code>ISourceType</code>.
 * <p>
 * 	This class serves as root of the dotplot type tree.
 * </p>
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class BaseType implements ISourceType {

	/**
	 * A default object of this class.
	 */
	public static final BaseType type = new BaseType();
	
	/**
	 * Name of the type.
	 */
	private String name;
	
	/**
	 * Creates a new <code>BaseType</code>.
	 */
	public BaseType(){
		this("Plotsource");
	}

	/**
	 * Creates a new <code>BaseType</code>.
	 * @param name Name of the type.
	 */
	public BaseType(String name) {
		super();
		if(name == null) throw new NullPointerException();
		this.name = name;
	}

	/**
	 * Derives the common super type of all <code>SourceType</code> objects of a list.
	 * @param list The <code>List</code> of <code>SourceType</code>s.
	 * @return The common super type.
	 */
	public static ISourceType deriveCommonSourceType(Collection<ISourceType> list){
		ISourceType type = null;
		Class current;
		Class source;
		Object o;
		
		for(ISourceType t : list){
			if(type == null){
				type = t;
			}
			else {
				current = type.getClass();
				source = t.getClass();
				if(!current.isAssignableFrom(source)){
					while(!current.isAssignableFrom(source)){
						current = current.getSuperclass();
					}
					try {
						o = current.newInstance();
						if(o instanceof ISourceType){
							type = (ISourceType)o;
						}
						else {
							type = null;
						}
					}
					catch (InstantiationException e) {
						return null;
					}
					catch (IllegalAccessException e) {
						return null;
					}										
				}
			}
		}
		return type;
	}
	
	/* (non-Javadoc)
	 * @see org.dotplot.core.ISourceType#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object obj) {
		ISourceType type = (ISourceType)obj;		
		return this.getName().compareTo(type.getName());
	}	

	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		if(o instanceof ISourceType){
			if(o.getClass() == this.getClass()){
				return this.getName().equals(((ISourceType)o).getName());
			}
		}
		return false;
	}
}
