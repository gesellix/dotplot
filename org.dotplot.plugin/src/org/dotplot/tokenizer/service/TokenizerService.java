/**
 * 
 */
package org.dotplot.tokenizer.service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfiguration;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.ISourceList;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.IRessource;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.IllegalContextException;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.tokenizer.converter.SourceListContext;
import org.dotplot.util.DuplicateRegistrationException;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 *
 */
public class TokenizerService extends DotplotService {

	private static final String TOKENIZER_HOTSPOT_ID = ".newTokenizer";
	public  static final String TOKENIZER_CONFIGURATION_ID = "org.dotplot.tokenizer.Configuration";
	public  static final String DEFAULT_TOKENIZER_ID = "org.dotplot.tokenizer.DefaultTokenizer";
	
	private Map<String, ITokenizer> tokenizers;
	
	/**
	 * @param id
	 */
	public TokenizerService(String id) {
		super(id);
		this.tokenizers = new TreeMap<String, ITokenizer>();
		this.addHotSpot(new PluginHotSpot(id + TOKENIZER_HOTSPOT_ID, ITokenizer.class));		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#workingContextIsCompatible(java.lang.Class)
	 */
	public boolean workingContextIsCompatible(Class contextClass) {
		return SourceListContext.class.isAssignableFrom(contextClass);
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#init()
	 */
	public void init() {
		super.init();
		String id;
		String name;
		ITokenizer tokenizer;
		
		try {
			this.tokenizers = new TreeMap<String, ITokenizer>();
			this.tokenizers.put(DEFAULT_TOKENIZER_ID, new DefaultScanner());
			for(Extention e : this.getHotSpot(this.getID() + TOKENIZER_HOTSPOT_ID).getActiveExtentions()){
				id = e.getParameter("id");				
				name = e.getParameter("name");
				if(name != null && id != null){
					if(! this.tokenizers.containsKey(id)){
						tokenizer = (ITokenizer)e.getExtentionObject();
						tokenizer.setName(name);
						this.tokenizers.put(id, tokenizer);
					}
				}
			}
		}
		catch (UnknownServiceHotSpotException e) {
			/*sollte nicht vorkommen*/
			this.getErrorHandler().fatal(this, e);
		}
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
		ITokenizer tokenizer = null;
		if(this.getWorkingContext() instanceof NullContext){
			this.getErrorHandler().fatal(this, new IllegalContextException(this.getWorkingContext()));
			return null;
		}
		SourceListContext context = (SourceListContext)this.getWorkingContext();		
		IConfiguration configuration = null;
		
		try {
			configuration = this.frameworkContext.getConfigurationRegistry().get(TOKENIZER_CONFIGURATION_ID);
			String tokenizerid = ((ITokenizerConfiguration)configuration).getTokenizerID();
			tokenizer = this.tokenizers.get(tokenizerid);
			if(tokenizer == null) throw new UnknownIDException(tokenizerid);
		}
		catch (UnknownIDException e) {
			this.getErrorHandler().fatal(this,e);
			return null;
		} 
		
		
		ITask task = new Task("org.dotplot.tokenizer.tasks.Tokenizer", new ITaskResultMarshaler(){

			public Object marshalResult(Map<String, ? extends Object> taskResult) {
				return taskResult.get("Part 1");
			}} ,false);
		
		TokenizerTaskPart part = new TokenizerTaskPart("Part 1",tokenizer); 
		
		part.setRessources((Collection<? extends IRessource>) context.getSourceList());
		
		task.addPart(part);
		
		return task;
	}
	
	public Map<String, ITokenizer> getRegisteredTokenizer(){
		return this.tokenizers;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		if(registry == null) throw new NullPointerException();
		try {
			registry.register(TOKENIZER_CONFIGURATION_ID, new DefaultTokenizerConfiguration());
		}
		catch (DuplicateRegistrationException e) {
			this.getErrorHandler().warning(this, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	@Override
	protected IContext createResultContext() {
		Object result = this.getTaskProcessor().getTaskResult();
		if(result == null){
			return NullContext.context;
		}
		else {
			ISourceList list = ((SourceListContext)this.getWorkingContext()).getSourceList();
			return new TokenStreamContext((ITokenStream)result,list);
		}
	}

}
