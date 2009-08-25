package org.dotplot.dpaas;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;

public class ByteRessource implements IPlotSource {

    private byte[] bytearray;
    private String filename;
    private ISourceType type;

    public ByteRessource() {
	// TODO Auto-generated constructor stub
    }

    public ByteRessource(String name, byte[] bytearray, ISourceType type) {
	this.setFilename(name);
	this.setBytearray(bytearray);
	this.setType(type);
    }

    public byte[] getBytearray() {
	return bytearray;
    }

    public String getFilename() {
	return filename;
    }

    @Override
    public InputStream getInputStream() {

	return new ByteArrayInputStream(this.getBytearray());

    }

    @Override
    public String getName() {

	return this.getFilename();

    }

    @Override
    public ISourceType getType() {
	return this.type;
    }

    @Override
    public URL getURL() {
	try {
	    return new URL("http://www.yahoo.com/");
	} catch (MalformedURLException e) {
	    return null;
	}
    }

    public void setBytearray(byte[] bytearray) {
	this.bytearray = bytearray;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public void setType(ISourceType type) {
	this.type = type;
    }

    @Override
    public long size() {
	return this.getBytearray().length;
    }

}
