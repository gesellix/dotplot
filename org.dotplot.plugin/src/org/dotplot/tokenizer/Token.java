package org.dotplot.tokenizer;

import java.io.File;

/**
 * Representation type of a Token.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class Token
{
   /**
    * Standard Token type for end of files.
    */
   public static final int TYPE_EOF = -1;

   /**
    * Standard token type for end of line.
    */
   public static final int TYPE_EOL = -2;

   /**
    * Standard token type for end of stream.
    * This Token returns, when the end of the tokenstream is reached.
    */
   public static final int TYPE_EOS = -3;

   /**
    * Standard Tokentype for a text line.
    */
   public static final int TYPE_LINE = -4;

   /**
    * Standard Tokentype for an Identifier.
    */
   public static final int TYPE_IDENT = -5;

   /**
    * Standard Tokentype for numbers.
    */
   public static final int TYPE_NUMBER = -6;

   /**
    * Standard Tokentype for strings.
    */
   public static final int TYPE_STRING = -7;

   /**
    * Die Zeichenkette die durch das Token repräsentiert wird.
    * The string which is represented through the token
    */
   private String value;

   /**
    * The file which contains the Token
    */
   private File file;

   /**
    * The line in which the Token occures
    */
   private int line;

   /**
    * the type of the Token
    * <p/>
    * The Type depends on the used language and the definition and can't be
    * predicted in any way.
    */
   private int type;

   /**
    * Creates a Token.
    *
    * @param value the string wich represents the Token
    */
   public Token(String value)
   {
      this.value = value;
      this.line = -1;
      this.type = -1;
   }

   /**
    * Creates a Token.
    *
    * @param value The string which represents the Token
    * @param type  a type
    */
   public Token(String value, int type)
   {
      this.value = value;
      this.line = -1;
      this.type = type;
   }

   /**
    * Creates a Token.
    *
    * @param value - The string which represents the Token
    * @param type  a type
    * @param line  the line
    */
   public Token(String value, int type, int line)
   {
      this.value = value;
      this.line = line;
      this.type = type;
   }

   /**
    * returns the string representation of the Token.
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      if (this.file != null)
      {
         sb.append(file.toString()).append(' ');
      }

      sb.append("Token");

      if (this.type != -1)
      {
         sb.append("(");
         sb.append(this.type);
         sb.append(")");
      }

      sb.append(" \"").append(this.value).append("\"");

      if (this.line > -1)
      {
         sb.append(" in line ");
         sb.append(this.line);
      }

      return sb.toString();
   }

   /**
    * Returns the file which contains the Token.
    *
    * @return - The file
    *
    * @see #setFile(java.io.File)
    */
   public File getFile()
   {
      return this.file;
   }

   /**
    * Returns the string which represents the Token.
    *
    * @return - the string
    */
   public String getValue()
   {
      return this.value;
   }

   /**
    * Sets the file which contains the Token.
    *
    * @param file - the file
    *
    * @see #getFile()
    */
   public void setFile(File file)
   {
      this.file = file;
   }

   /**
    * returns the line which contains the Token.
    *
    * @return - The line
    *
    * @see #setLine(int)
    */
   public int getLine()
   {
      return this.line;
   }

   /**
    * returns the token type.
    *
    * @return - the token type
    *
    * @see #setType(int)
    */
   public int getType()
   {
      return this.type;
   }

   /**
    * returns the line which contains the Token.
    *
    * @param line - the line number
    *
    * @see #getLine()
    */
   public void setLine(int line)
   {
      this.line = line;
   }

   /**
    * Sets the Tokentype.
    *
    * @param type - the Tokentype
    *
    * @see #getType()
    */
   public void setType(int type)
   {
      this.type = type;
   }

   /**
    * Proves if two objects are equal.
    * <p />
    * It returns only a positiv resulst, if the objects are Token.
    * Two Token are equal, if the type and value are equal.
    *
    * @param object the object which should be examined
    *
    * @return the result
    */
   public boolean equals(Object object)
   {
      if (object instanceof Token)
      {
         Token token = ((Token) object);
         return (token.getType() == this.getType() && token.getValue().equals(this.getValue()));
      }

      return false;
   }
}
