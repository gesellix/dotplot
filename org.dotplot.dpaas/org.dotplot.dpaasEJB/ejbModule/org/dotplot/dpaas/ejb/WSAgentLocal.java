package org.dotplot.dpaas.ejb;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface WSAgentLocal {

	void start();
	void stop();
	boolean state();
	void runJobs(Timer timer);

}
