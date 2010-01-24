/**
 * 
 */
package org.dotplot.examples;

import org.dotplot.core.DotplotContext;
import org.dotplot.core.services.AbstractJob;
import org.dotplot.core.services.IServiceRegistry;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class TestJob extends AbstractJob<DotplotContext> {

	/**
	 * Creates a new <code>TestJob</code>.
	 */
	public TestJob() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IJob#process(C)
	 */
	public boolean process(DotplotContext context) {
		System.out.println("Processing TestJob");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.IJob#validatePreconditions(org.dotplot.core
	 * .services.IServiceRegistry)
	 */
	public boolean validatePreconditions(IServiceRegistry registry) {
		return true;
	}

}
