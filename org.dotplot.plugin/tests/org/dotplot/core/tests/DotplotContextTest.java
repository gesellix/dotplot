/**
 * 
 */
package org.dotplot.core.tests;

import java.io.File;

import junit.framework.TestCase;

import org.dotplot.core.DotplotContext;
import org.dotplot.core.DotplotFile;
import org.dotplot.core.ITypeBindingRegistry;
import org.dotplot.core.ITypeRegistry;
import org.dotplot.core.TypeBindingRegistry;
import org.dotplot.core.services.IErrorHandler;
import org.dotplot.core.services.IFrameworkContext;
import org.dotplot.core.services.IJob;
import org.dotplot.core.services.IServiceRegistry;
import org.dotplot.core.services.ITaskProcessor;
import org.dotplot.tokenizer.converter.PdfType;
import org.dotplot.tokenizer.service.TextType;
import org.dotplot.util.UnknownIDException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class DotplotContextTest extends TestCase {

    private class TestJob implements IJob {

	private StringBuffer buffer = new StringBuffer();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.services.IJob#getErrorHandler()
	 */
	public IErrorHandler getErrorHandler() {
	    return null;
	}

	public String getResult() {
	    return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IJob#process(org.dotplot.services.ServiceManager
	 * , org.dotplot.services.IFrameworkContext)
	 */
	public boolean process(IFrameworkContext context) {
	    this.buffer.append("process");
	    this.buffer.append("\n");
	    this.buffer.append(context.getServiceRegistry().getClass()
		    .getName());
	    this.buffer.append("\n");
	    this.buffer.append(context.getClass().getName());
	    this.buffer.append("\n");
	    return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dotplot.services.IJob#setErrorHandler(org.dotplot.services.
	 * IErrorHandler)
	 */
	public void setErrorHandler(IErrorHandler handler) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.core.services.IJob#setTaskProcessor(org.dotplot.core.
	 * services.ITaskProcessor)
	 */
	public void setTaskProcessor(ITaskProcessor processor) {
	    // TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dotplot.services.IJob#validatePreconditions(org.dotplot.services
	 * .ServiceManager, org.dotplot.services.IFrameworkContext)
	 */
	public boolean validatePreconditions(IServiceRegistry manager) {
	    return false;
	}
    }

    private DotplotContext context;

    private File workingDir;

    private File pluginDir;

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	this.workingDir = new File(".");
	this.pluginDir = new File("./plugins");
	this.context = new DotplotContext(this.workingDir.getAbsolutePath(),
		this.pluginDir.getAbsolutePath());
    }

    public void testCreateDotplotFile() {
	try {
	    this.context.createDotplotFile(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.context.createDotplotFile(new File("./testfiles/"));
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    /* all clear */
	} catch (Exception e) {
	    e.printStackTrace();
	    fail("wrong Exception");
	}

	try {
	    File pdf = new File("./testfiles/converter/PO91-1999.pdf");
	    pdf = pdf.getCanonicalFile();
	    File txt = new File("./testfiles/converter/pdfout.txt");
	    txt = txt.getCanonicalFile();

	    ITypeRegistry registry = this.context.getTypeRegistry();
	    assertNotNull(registry);
	    assertTrue(registry.getAll().isEmpty());

	    registry.register("text", TextType.type);
	    registry.register("pdf", PdfType.type);

	    ITypeBindingRegistry registry2 = this.context
		    .getTypeBindingRegistry();
	    registry2.register(".txt", "text");
	    registry2.register(".pdf", "pdf");

	    DotplotFile df;

	    df = this.context.createDotplotFile(pdf);
	    assertNotNull(df);
	    assertEquals(pdf, df.getFile());
	    assertEquals(PdfType.type, df.getType());

	    df = this.context.createDotplotFile(txt);
	    assertNotNull(df);
	    assertEquals(txt, df.getFile());
	    assertEquals(TextType.type, df.getType());

	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for
     * 'org.dotplot.core.DotplotContext.DotplotContext(IServiceManager, String)'
     */
    public void testDotplotContext() {
	try {
	    new DotplotContext(null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    new DotplotContext("/blubtest");
	    fail("IllegalArgumentException must be thrown");
	} catch (IllegalArgumentException e) {
	    assertEquals("/blubtest", e.getMessage());
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    DotplotContext context = new DotplotContext(".");
	    assertNotNull(context.getServiceRegistry());
	    assertNotNull(context.getPluginRegistry());
	    assertNotNull(context.getJobRegistry());
	    assertEquals(workingDir.getAbsolutePath(), context
		    .getWorkingDirectory());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for 'org.dotplot.core.DotplotContext.executeJob(IJob)'
     */
    public void testExecuteJobIJob() {
	TestJob job = new TestJob();
	try {
	    this.context.executeJob((IJob) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    assertEquals("", job.getResult());
	    this.context.executeJob(job);
	    assertEquals(
		    "process\norg.dotplot.core.services.ServiceRegistry\norg.dotplot.core.DotplotContext\n",
		    job.getResult());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for 'org.dotplot.core.DotplotContext.executeJob(String)'
     */
    public void testExecuteJobString() {
	TestJob job = new TestJob();
	try {
	    this.context.executeJob((String) null);
	    fail("NullPointerException must be thrown");
	} catch (NullPointerException e) {
	    /* all clear */
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.context.executeJob("testjob");
	    fail("UnknownIDException must be thrown");
	} catch (UnknownIDException e) {
	    assertEquals("testjob", e.getMessage());
	} catch (Exception e) {
	    fail("wrong Exception");
	}

	try {
	    this.context.getJobRegistry().register("testjob", job);
	    assertEquals("", job.getResult());
	    this.context.executeJob("testjob");
	    assertEquals(
		    "process\norg.dotplot.core.services.ServiceRegistry\norg.dotplot.core.DotplotContext\n",
		    job.getResult());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}

    }

    /*
     * Test method for 'org.dotplot.core.DotplotContext.getServiceManager()'
     */
    public void testGetServiceManager() {
	assertNotNull(this.context.getServiceRegistry());
    }

    public void testGetTypeBindingRegistry() {
	try {
	    ITypeBindingRegistry registry = this.context
		    .getTypeBindingRegistry();
	    assertNotNull(registry);
	    assertTrue(registry.getAll().isEmpty());
	    assertTrue(registry instanceof TypeBindingRegistry);
	    TypeBindingRegistry r = (TypeBindingRegistry) registry;
	    assertSame(this.context.getTypeRegistry(), r.getTypeRegistry());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    public void testGetTypeRegistry() {
	try {
	    ITypeRegistry registry = this.context.getTypeRegistry();
	    assertNotNull(registry);
	    assertTrue(registry.getAll().isEmpty());
	} catch (Exception e) {
	    fail("no exception:" + e.getClass().getName() + ":"
		    + e.getMessage());
	}
    }

    /*
     * Test method for 'org.dotplot.core.DotplotContext.getWorkingDirectory()'
     */
    public void testGetWorkingDirectory() {
	assertEquals(workingDir.getAbsolutePath(), this.context
		.getWorkingDirectory());
    }
}
