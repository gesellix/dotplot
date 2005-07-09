/*
 * Created on 30.05.2004
 */
package org.dotplot.tokenizer;

/**
 * Class for a nearer description of a Token.
 *
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class TokenType
{
   /**
    * This token type has no special kind.
    */
   public static final int KIND_OTHER = 0;

   /**
    * This token type descripes a keyword.
    */
   public static final int KIND_KEYWORD = 1;

   /**
    * This token type descripes an operator.
    */
   public static final int KIND_OPERATOR = 2;

   /**
    * This token type descripes a punctuation mark.
    */
   public static final int KIND_PUNCTUATION_MARK = 3;

   /**
    * Name of the token type
    */
   private String name;

   /**
    * The type of the Token
    */
   private int type;

   /**
    * The kinde of the Token
    */
   private int kind;

   /**
    * Creates a new <code>TokenType</code> Object.
    *
    * @param type - the token type
    * @param name - the name
    * @param kind - the token kinde
    */
   public TokenType(int type, String name, int kind)
   {
      this.type = type;
      this.name = name;
      this.kind = kind;
   }

   /**
    * returns the name of the Token.
    *
    * @return - the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * returns the type.
    *
    * @return - the Typ
    */
   public int getType()
   {
      return type;
   }

   /**
    * returns the Token kind.
    *
    * @return - the token kind
    */
   public int getKind()
   {
      return this.kind;
   }

   /**
    * Creates a <code>Token</code> which is described through this <code>TokenTyp</code> Object.
    *
    * @return - the new Token
    *
    * @see Token
    */
   public Token getToken()
   {
      return new Token(this.name, this.type, 0);
   }
}
