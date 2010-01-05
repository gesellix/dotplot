package org.dotplot.dpaas.ejb;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.dotplot.dpaas.entities.Job;
import org.dotplot.dpaas.ws.Configuration;
import org.dotplot.dpaas.ws.Dotplotjob;
import org.dotplot.dpaas.ws.Dotplotjobresponse;
import org.dotplot.dpaas.ws.File;
import org.dotplot.dpaas.ws.Filelist;
import org.dotplot.dpaas.ws.Filter;
import org.dotplot.dpaas.ws.FilterTypes;
import org.dotplot.dpaas.ws.Filters;
import org.dotplot.dpaas.ws.WebServicePort;
import org.dotplot.dpaas.ws.WebServicePortService;

@Stateless
public class WSAgent implements WSAgentLocal {

	@Resource
	TimerService timerService;
	
	@EJB
	BackendLocal backend;
	
	// Msecs to wait untill next call
	private static int timerDelay = 60000;
	
	private Timer timer;
	private boolean running = false;
	
	@Override
	public void start() {
		if (!running)
			timer = timerService.createTimer(timerDelay, new String("Run"));
		running = true;
	}
	
	@Override
	public void stop() {
		if (running)
			timer.cancel();
		running = false;
	}
	
	@Override
	public boolean state() {
		return running;
	}
	
	@Timeout
	@Override
	public synchronized void runJobs(Timer timer) {
		if (running) {
			for (Job j : backend.unfinishedJobs()) {
				if (j!=null) this.callWebService(j);
			}
			timer = timerService.createTimer(timerDelay, new String("Run"));
		}
	}
	
	private void callWebService(Job job) {
		try {
			Dotplotjob dpjob = wsDotplotjob(job);
			Dotplotjobresponse response = null;
			
			WebServicePortService ws = new WebServicePortService();
			WebServicePortService.setWsdlUrl(backend.setting("url"));
			WebServicePort port = ws.getWebServicePortPort();
			response = port.doDotPlot(dpjob);
			job.setImage(response.getImage().getContent());
			backend.updateJob(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Dotplotjob wsDotplotjob(Job job) {
		Dotplotjob dpjob = new Dotplotjob();
		
		Filelist filelist = wsFilelist(job);
		Configuration conf = wsJobConfiguration(job);
		
		dpjob.setFilelist(filelist);
		dpjob.setConfiguration(conf);
		return dpjob;
	}

	private Configuration wsJobConfiguration(Job job) {
		Configuration conf = new Configuration();
		Filter filter = new Filter();
		filter.setID(FilterTypes.ORG_DOTPLOT_FILTER_KEY_WORD_FILTER);
		
		List<Filter> ftl = new LinkedList<Filter>();
		ftl.add(filter);
		
		Filters filters = new Filters();
		filters.setFilter(ftl);
		
		conf.setFilter(filters);
		conf.setLUTs(job.getLuts());
		return conf;
	}

	private Filelist wsFilelist(Job job) {
		Filelist filelist = new Filelist();
		List<File> fll = new LinkedList<File>();
		File file = null;
		
		for (org.dotplot.dpaas.entities.File eFile : job.getFiles()) {
			file = new File();
			file.setFilename(eFile.getName());
			file.setFileType(job.getJobtype());
			file.setContent(eFile.getContent());
			fll.add(file);
		}
		
		filelist.setFiles(fll);
		return filelist;
	}
}
