/*
 * Created on 23.05.2004
 */
package org.dotplot.tokenizer;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.dotplot.ui.configuration.GlobalConfiguration;

/**
 * Repräsentiert eine Dotplotdatei.
 * <p />
 * Da sich nicht jedes Dateiformat auf sinvolle weiese Tokenizen läßt müssen diese Dateien in
 * ein entsprechendes Format konvertiert werden. Dies geschieht mit Hilfe eines entsprechenden
 * <code>Konverters</code>. Um nun eine Beziehung zweichen konvertierter Datei und original Datei
 * zu erhalten wird diese Klasse benutzt. Sie verhällt sich in vielen Dingen ähnlich wie ihre
 * Oberklasse <code>File</code>. Allerdings sollte immer dann wenn eine konvertierte Datei
 * existieren könnte (vorsicht Konjunktiv!), und eine Arbeit mit dieser konvertierten Datei
 * erwünscht ist, die Methode <code>getFile()</code> aufgerufen werden.
 * z.B.
 * <code>
 * Dotplotfile df;
 * FileReader fr = new FileReader(df.getFile());
 * </code>
 * <code>getFile()</code> gibt die konvertierte Datei zurück, falls diese existiert, ansonsten
 * das Original.
 * Die Konvertierte Datei wird im Temp-Verzeichnis des Systems gespeichert, und zum gleichen Zeitpunkt
 * gelöscht, in dem die <code>finalize()</code> Methode des DotplotFile Objekts aufgerufen wird.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class DotplotFile extends File
{
   /**
    * Einfacher Converter, der einfach eine neue Datei anlegt...
    * nicht für den richtigen gebrauch gedacht.
    *
    * @author Christian Gerhardt <case42@gmx.net>
    */
   //TODO den Converter durch einen sinnvollen ersetzen!
   private class SimpleConverter implements IConverter
   {
      /**
       * @see org.dotplot.tokenizer.IConverter#convert(java.io.File)
       */
      public File convert(File file)
      {
         return convert(file, null);
      }

      /**
       * @see org.dotplot.tokenizer.IConverter#convert(java.io.File, java.io.File)
       */
      public File convert(File file, File directory)
      {
         File f = null;
         try
         {
            f = File.createTempFile(file.getName(), null, directory);
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
         return f;
      }
   }

   /**
    * Die Konvertierte Datei
    */
   private File convertedFile;

   /**
    * Der zu benutzende Konverter.
    */
   private IConverter converter;

   /**
    * Creates a new File instance by converting the given pathname string into an abstract pathname.
    *
    * @param pathname - A pathname string
    *
    * @see java.io.File#File(String)
    */
   public DotplotFile(String pathname)
   {
      super(pathname);
   }

   /**
    * Creates a new File instance by converting the given file: URI into an abstract pathname.
    *
    * @param uri - An absolute, hierarchical URI with a scheme equal to "file", a non-empty path component, and undefined authority, query, and fragment components
    *
    * @see java.io.File#File(URI)
    */
   public DotplotFile(URI uri)
   {
      super(uri);
   }

   /**
    * Creates a new File instance from a parent abstract pathname and a child pathname string.
    *
    * @param parent - The parent abstract pathname
    * @param child  - The child pathname string
    *
    * @see java.io.File#File(File, String)
    */
   public DotplotFile(File parent, String child)
   {
      super(parent, child);
   }

   /**
    * Creates a new File instance from a parent pathname string and a child pathname string.
    *
    * @param parent - The parent pathname string
    * @param child  - The child pathname string
    *
    * @see java.io.File#File(String, String)
    */
   public DotplotFile(String parent, String child)
   {
      super(parent, child);
   }

   /**
    * Creates a new File instance by converting the given pathname string into an abstract pathname.
    *
    * @param pathname - A pathname string
    * @param convert  - If the File should be converted by construction
    *
    * @see java.io.File#File(String)
    */
   public DotplotFile(String pathname, boolean convert)
   {
      super(pathname);
      if (convert)
      {
         this.converter = new SimpleConverter();
         this.convertFile();
      }
   }

   /**
    * Creates a new File instance by converting the given file: URI into an abstract pathname.
    *
    * @param uri     - An absolute, hierarchical URI with a scheme equal to "file", a non-empty path component, and undefined authority, query, and fragment components
    * @param convert - If the File should be converted by construction
    *
    * @see java.io.File#File(URI)
    */
   public DotplotFile(URI uri, boolean convert)
   {
      super(uri);
      if (convert)
      {
         this.converter = new SimpleConverter();
         this.convertFile();
      }
   }

   /**
    * Creates a new File instance from a parent abstract pathname and a child pathname string.
    *
    * @param parent  - The parent abstract pathname
    * @param child   - The child pathname string
    * @param convert - If the File should be converted by construction
    *
    * @see java.io.File#File(File, String)
    */
   public DotplotFile(File parent, String child, boolean convert)
   {
      super(parent, child);
      if (convert)
      {
         this.converter = new SimpleConverter();
         this.convertFile();
      }
   }

   /**
    * Creates a new File instance from a parent pathname string and a child pathname string.
    *
    * @param parent  - The parent pathname string
    * @param child   - The child pathname string
    * @param convert - If the File should be converted by construction
    *
    * @see java.io.File#File(String, String)
    */
   public DotplotFile(String parent, String child, boolean convert)
   {
      super(parent, child);
      if (convert)
      {
         this.converter = new SimpleConverter();
         this.convertFile();
      }
   }

   /**
    * Deletes the file or directory denoted by this abstract pathname.
    *
    * @return - true if and only if the file or directory is successfully deleted; false otherwise
    */
   public boolean delete()
   {
      if (this.convertedFile != null)
      {
         this.convertedFile.delete();
      }
      return super.delete();
   }

   /**
    * Requests that the file or directory denoted by this abstract pathname be deleted when the virtual machine terminates.
    */
   public void deleteOnExit()
   {
   }

   /**
    * Tests this abstract pathname for equality with the given object.
    *
    * @param obj - The object to be compared with this abstract pathname
    *
    * @return - true if and only if the objects are the same; false otherwise
    */
   public boolean equals(Object obj)
   {
      if (super.equals(obj))
      {
         return true;
      }
      else if (this.getConvertedFile() != null)
      {
         return this.getConvertedFile().equals(obj);
      }
      else
      {
         return false;
      }
   }

   /**
    * Gibt die Datei zur weiteren Bearbeitung zurück.
    * Falls eine konvertierte Datei vorliegt, wird diese zurückgegeben, ansonsten
    * die Originaldatei.
    *
    * @return - die Datei
    */
   public File getFile()
   {
      if (this.convertedFile == null)
      {
         return this;
      }
      else
      {
         return this.convertedFile;
      }
   }

   /**
    * Gibt die Original-Datei zurück.
    * Das Original ist unkonvertiert.
    *
    * @return - die Datei
    */
   public File getOrigin()
   {
      return (File) this;
   }

   /**
    * Gibt die konvertierte Datei zurück.
    * Wurde die Datei noch nicht konvertiert, wird <code>null</code> zurückgegeben.
    *
    * @return - die Datei
    */
   public File getConvertedFile()
   {
      return this.convertedFile;
   }

   /**
    * Konvertiert die Datei.
    * Zum konvertieren wird der aktuell eingestellte Konverter benutzt.
    *
    * @return - ob das konvertieren erfolgreich war.
    */
   public boolean convertFile()
   {
      try
      {
         this.convertedFile = this.converter.convert(this);
      }
      catch (IOException e)
      {
         this.convertedFile = null;
      }
      if (this.convertedFile == null)
      {
         return false;
      }
      return true;
   }

   /**
    * Konvertiert die Datei.
    * Zum konvertieren wird der aktuell eingestellte Konverter benutzt.
    *
    * @param destination - Zielverzeichnis
    *
    * @return - ob das konvertieren erfolgreich war.
    */
   public boolean convertFile(File destination)
   {
      try
      {
         this.convertedFile = this.converter.convert(this, destination);
      }
      catch (IOException e)
      {
         this.convertedFile = null;
      }
      if (this.convertedFile == null)
      {
         return false;
      }
      return true;
   }

   /**
    * gibt den aktuellen Konverter zurück.
    *
    * @return - derKonverter
    *
    * @see #setConverter(IConverter)
    */
   public IConverter getConverter()
   {
      return this.converter;
   }

   /**
    * Setzt den aktuellen Konverter.
    *
    * @param converter - der Konverter
    *
    * @see #getConverter()
    */
   public void setConverter(IConverter converter)
   {
      this.converter = converter;
   }

   /**
    * Sorgt dafür, das auch die konvertierte Datei gelöscht wird.
    */
   protected void finalize()
   {
      if (this.convertedFile != null)
      {
         if (!((Boolean) GlobalConfiguration.getInstance().get(GlobalConfiguration.KEY_TOKENIZER_SAVE_CONVERTED_FILES)).booleanValue())
         {
            this.convertedFile.delete();
         }
      }
   }

   public String toString()
   {
      return super.toString() + " : " + this.convertedFile;
   }
}
