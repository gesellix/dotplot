/**
 * 
 */
package org.dotplot.tokenizer.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dotplot.core.BaseType;
import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.ISourceList;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.tokenizer.filter.ui.GeneralTokenFilterUI;
import org.dotplot.tokenizer.filter.ui.IFilterUI;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TokenStreamContext;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;


/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class FilterService extends DotplotService {

	/**
	 * Logger for debug informations.
	 */
	private static Logger logger = Logger.getLogger(FilterService.class.getName());
	
	private final static String FILTER_HOTSPOT_ID 		= ".newFilter";
	public  final static String FILTER_CONFIGURATION_ID = "org.dotplot.filter.Configuration";
	public	final static String GENERAL_TOKEN_FILTER_ID	= "org.dotplot.filter.GeneralFilter";
	
	private Map<String, ITokenFilter> filter;
	private List<IFilterUI> uis;
	
	/**
	 * @param id
	 */
	public FilterService(String id) {
		super(id);
		this.addHotSpot(new PluginHotSpot(id + FILTER_HOTSPOT_ID, ITokenFilter.class));
		this.filter = new TreeMap<String, ITokenFilter>();
		this.uis = new ArrayList<IFilterUI>();
	}

	public Map<String, ITokenFilter> getRegisteredFilters(){
		return this.filter;
	}
	
	public void init(){
		super.init();
		String name, uiClass;
		ITokenFilter filter;
		
		logger.debug("--init--");
		
		try {
			//reset storrage objects
			this.filter.clear();
			this.uis.clear();
			
			//fill storrage objects
			for(Extention e : this.getHotSpot(this.getID() + FILTER_HOTSPOT_ID).getActiveExtentions()){
				name = e.getParameter("name");
				uiClass = e.getParameter("ui");
				if(name != null){
					if(! this.filter.containsKey(name)){
						filter = (ITokenFilter)e.getExtentionObject();
						this.filter.put(name, filter);
						if(uiClass!= null){
							logger.debug("name: " + name);
							logger.debug("ui: " + uiClass);
							try {
								Object o = e.getExtentionObject().getClass().getClassLoader().loadClass(uiClass).newInstance();
								if(o instanceof IFilterUI){
									IFilterUI ui = (IFilterUI)o;
									ui.setFilterID(name);
									ui.setSourceType(filter.getStreamType());
									this.uis.add(ui);
									logger.debug("added successfully");
								}
							}
							catch (Exception e1) {
								this.getErrorHandler().warning(this, e1);
								logger.debug("error", e1);
							}
						}
					}
				}				
			}
			
			//standard input
			this.filter.put(GENERAL_TOKEN_FILTER_ID, new GeneralTokenFilter());
			IFilterUI ui = new GeneralTokenFilterUI();
			ui.setFilterID(GENERAL_TOKEN_FILTER_ID);
			ui.setSourceType(BaseType.type);
			this.uis.add(ui);
		}
		catch (UnknownServiceHotSpotException e) {
			/*sollte nicht vorkommen*/
			this.getErrorHandler().fatal(this, e);
		}
		
	}
	
	public List<IFilterUI> getUIs(){		
		return this.uis;
	}	
	
	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		if(registry == null) throw new NullPointerException();
		try {
			registry.register(FILTER_CONFIGURATION_ID, new DefaultFilterConfiguration());
		}
		catch (DuplicateRegistrationException e) {
			this.getErrorHandler().warning(this, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	public boolean workingContextIsCompatible(Class contextClass) {
		return TokenStreamContext.class.isAssignableFrom(contextClass);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	public Class getResultContextClass() {
		return TokenStreamContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	public ITask createTask() {
		IContext workingContext = this.getWorkingContext();
		if(workingContext instanceof NullContext){
			this.getErrorHandler().fatal(this, new IllegalContextException(new NullContext()));
			return null;			
		}
		
		final ITokenStream stream = ((TokenStreamContext) workingContext).getTokenStream();
		
		if(stream == null){
			this.getErrorHandler().fatal(this, new IllegalContextException(new NullContext()));
			return null;
		}

		Task task = new Task("Filter Task", new ITaskResultMarshaler(){

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				return new FilteredTokenStream(stream, (ITokenFilter)taskResult.get("Part 1"));
			}}, false);

		
		IFilterConfiguration config;
		try {
			config = (IFilterConfiguration)this.frameworkContext.getConfigurationRegistry().get(FILTER_CONFIGURATION_ID);
			task.addPart(new FilterTaskPart("Part 1",this.getRegisteredFilters(),config	));
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this, e);
			return null;
		}		
		
		return task;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	protected IContext createResultContext() {
		Object result = this.getTaskProcessor().getTaskResult();
		if(result == null){
			return NullContext.context;
		}
		else {
			ISourceList list = ((TokenStreamContext)this.getWorkingContext()).getSourceList();
			return new TokenStreamContext((ITokenStream)result, list);
		}
	}		
	
}
