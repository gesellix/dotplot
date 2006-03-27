/**
 * 
 */
package org.dotplot.tokenizer.converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.DotplotService;
import org.dotplot.core.IConfigurationRegistry;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.core.ISourceType;
import org.dotplot.core.ITypeBindingRegistry;
import org.dotplot.core.ITypeRegistry;
import org.dotplot.core.plugins.PluginHotSpot;
import org.dotplot.core.services.Extention;
import org.dotplot.core.services.IContext;
import org.dotplot.core.services.ITask;
import org.dotplot.core.services.ITaskResultMarshaler;
import org.dotplot.core.services.NullContext;
import org.dotplot.core.services.Task;
import org.dotplot.core.services.UnknownServiceHotSpotException;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.util.DuplicateRegistrationException;

/**
 * 
 * <ul>
 * <li>newSourceType</li> <code>{@link org.dotplot.core.ISourceType}</code>
 * 	<p>
 * 		<b>Parameter:</b><br>
 * 		<u>name</u> - Used to identify the new type in the typeregistry.<br> 
 * 		<u>suffix</u> [optional] - Used to assign the type to a file suffix.
 * 		This parameter only works with a <code>{@link org.dotplot.core.FileType}</code>
 * 		and is ignored otherwise.<br> 
 *  </p>
 * <li>newConverter</li> <code>{@link org.dotplot.tokenizer.converter.IConverter}</code>
 * </ul> 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class ConverterService extends DotplotService {

	private static Logger logger = Logger.getLogger(ConverterService.class
			.getName());
	
	public static final String CONVERTER_CONFIGURATION_ID = "org.dotplot.converter.Cofiguration";
	
	public static final String CONVERTER_HOTSPOT_ID = ".newConverter";
	public static final String TYPE_HOTSPOT_ID = ".newType";
	
	public static final String TYPE_TEXT_ID = "org.dotplot.types.Text";
	public static final String TYPE_PDF_ID = "org.dotplot.types.Pdf";
	
	public static final String CONVERTER_PDF_TO_TEXT_ID = "org.dotplot.converter.Pdf2txt";
	
	private Map<String, IConverter> converters;	
	
	private ITask lastTask;
	
	/**
	 * @param id
	 */
	public ConverterService(String id) {
		super(id);
		this.addHotSpot(new PluginHotSpot(id + CONVERTER_HOTSPOT_ID, IConverter.class));
		this.addHotSpot(new PluginHotSpot(id + TYPE_HOTSPOT_ID, ISourceType.class));
		
		this.converters = new TreeMap<String, IConverter>();
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
		String name;
		String suffix;
		
		ITypeRegistry types = this.frameworkContext.getTypeRegistry();
		ITypeBindingRegistry bindings = this.frameworkContext.getTypeBindingRegistry();
		try {
			types.register(TYPE_TEXT_ID, TextType.type);
			types.register(TYPE_PDF_ID, PdfType.type);
			
			bindings.register(".txt", TYPE_TEXT_ID);
			bindings.register(".pdf", TYPE_PDF_ID);
			
			this.converters.put(CONVERTER_PDF_TO_TEXT_ID, new PDFtoTxtConverter());
		}
		catch (DuplicateRegistrationException e) {
			this.getErrorHandler().error(this, e);
		}
		try {
			for(Extention e : this.getHotSpot(this.getID() + CONVERTER_HOTSPOT_ID).getAllExtentions()){
				try {
					name = e.getParameter("name");
					if(name == null){
						throw new NullPointerException();
					}
					else {
						if(this.converters.containsKey(name)){
							throw new DuplicateRegistrationException(name);
						}
						else {
							this.converters.put(name, (IConverter)e.getExtentionObject());
						}
					}
				}
				catch(Exception e1){
					this.getErrorHandler().warning(this, e1);
				}
			}
			
			for(Extention e : this.getHotSpot(this.getID() + TYPE_HOTSPOT_ID).getAllExtentions()){
				name = e.getParameter("name");
				suffix = e.getParameter("suffix");
				if(name == null){
					continue;
				}
				else {
					try {
						types.register(name, (ISourceType)e.getExtentionObject());						
					}
					catch (DuplicateRegistrationException e1) {
						if(suffix == null){
							this.getErrorHandler().warning(this, e1);
						}
					}
					if(suffix != null){
						try {
							bindings.register(suffix, name);
						}
						catch (DuplicateRegistrationException e1) {
							this.getErrorHandler().warning(this, e1);
						}
					}
				}
			}
		}	
		catch (UnknownServiceHotSpotException e) {
			this.getErrorHandler().error(this, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#getResultContextClass()
	 */
	public Class getResultContextClass() {
		return SourceListContext.class;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.services.AbstractService#createTask()
	 */
	public ITask createTask() {
		IConverter converter = null;
		ISourceType sourceType;
		Collection<IPlotSource> ressources;
		Task task = null;
		ConverterTaskPart part;
		String converterid;
		
		try{
			IConverterConfiguration config = (IConverterConfiguration)this.frameworkContext.getConfigurationRegistry().get(CONVERTER_CONFIGURATION_ID); 
			ISourceType targetType = config.getTargetType();
			SourceListContext context = (SourceListContext)this.getWorkingContext();
			
			final Vector<IPlotSource> v = new Vector<IPlotSource>(context.getSourceList().size());
			v.setSize(context.getSourceList().size());
			
			task = new Task("Converter task", new ITaskResultMarshaler(){

				public Object marshalResult(Map<String, ? extends Object> taskResult) {
					List result;
					for(String key: taskResult.keySet()){
						result = (List)taskResult.get(key);
						if(!result.isEmpty()){ 
							v.set(Integer.parseInt(key), (IPlotSource)result.get(0));
						}
					}
					return v;
				}}, true);

			int i = 0;
			for (IPlotSource s : context.getSourceList()){
				sourceType = s.getType();
				
				//prüfen ob der quell-type dem zieltyp zugewiesen werden kann
				if(targetType.getClass().isAssignableFrom(sourceType.getClass())){
					//ja, kann er, also in die ergebnisliste aufnehmen
					v.set(i, s);
					logger.debug("sourceResult: " + s.getName());
				}
				else {
					logger.debug("toConvert: " + s.getName() + " " + sourceType.getName());
					//nein, kann er nicht, also muss er konvertiert werden 

					//die converterid besorgen
					converterid = config.getConverterID(sourceType);
					if(converterid == null){
						throw new UnconvertableException(sourceType.getName());
					}
					
					//den richtigen converter raussuchen
					converter = this.getRegisteredConverter().get(converterid);
					if(converter == null){
						throw new UnknownConverterException(converterid);
					}
					
					//ein neues converterobjekt erstellen um für die parallel-verarbeitung gerüstet zu sein.
					converter = converter.getClass().newInstance();
					
					//letztes überprüfen der convertertypen
					if(sourceType.getClass().isAssignableFrom(converter.getSourceTye().getClass()) 
							&& targetType.getClass().isAssignableFrom(converter.getTargetTye().getClass())){
						//taskpart erstellen
						ressources = new Vector<IPlotSource>();
						ressources.add(s);
						part = new ConverterTaskPart(String.valueOf(i), converter ,config.getConvertedFilesDirectory(), config.overwriteConvertedFiles());
						part.setRessources(ressources);
						task.addPart(part);						
					}
					else {
						throw new ConverterMismatchException(converterid);
					}					
				}
				i++;
			}			
		}
		catch(Exception e){
			logger.fatal(e);
			this.getErrorHandler().fatal(this, e);
			task = null;
		}
		this.lastTask = task;
		return task;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#registerDefaultConfiguration(org.dotplot.core.IConfigurationRegistry)
	 */
	public void registerDefaultConfiguration(IConfigurationRegistry registry) {
		try {
			registry.register(CONVERTER_CONFIGURATION_ID, new DefaultConverterConfiguration());
		}
		catch (DuplicateRegistrationException e) {
			this.getErrorHandler().warning(this, e);
		}
	}
	
	public Map<String, IConverter> getRegisteredConverter(){
		return this.converters;
	}

	/* (non-Javadoc)
	 * @see org.dotplot.core.DotplotService#createResultContext()
	 */
	@Override
	protected IContext createResultContext() {
		logger.debug("generating resultContext");
		Object result = this.getTaskProcessor().getTaskResult();
		if(lastTask == null || ! lastTask.isDone()){
			logger.debug("task not be done");
			return NullContext.context;
		}
		else {
			this.lastTask = null;
			logger.debug("task is done");
			ISourceList list = new DefaultSourceList();
			if(result != null) {
				list.addAll((List<IPlotSource>)result);
			}
			return new SourceListContext(list);
		}
	}	
}
