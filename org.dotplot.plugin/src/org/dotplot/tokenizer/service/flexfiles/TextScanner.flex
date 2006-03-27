package org.dotplot.tokenizer.scanner;

import org.dotplot.tokenizer.*;

%%

%class TextScanner
%extends BaseScanner
%public
%{

  protected static TokenType[] tokenTypes = {
  	new TokenType(Token.TYPE_STRING, "word",TokenType.KIND_OTHER),
  	
  	new TokenType(100, ";", TokenType.KIND_PUNCTUATION_MARK),
  	new TokenType(106, ",", TokenType.KIND_PUNCTUATION_MARK),
  	new TokenType(110, "!", TokenType.KIND_PUNCTUATION_MARK),
  	new TokenType(121, ".", TokenType.KIND_PUNCTUATION_MARK),
  	new TokenType(124, ":", TokenType.KIND_PUNCTUATION_MARK),
  	new TokenType(125, "?", TokenType.KIND_PUNCTUATION_MARK),
  };

  /** 
   * Defaultconstructor : erzeugt einen neuen TextScanner
   */
  public TextScanner () {
    
  }
  
  public TokenType[] getTokenTypes(){
  	return tokenTypes;
  }
  
  public ISourceType getSourceType() {
	   return TextFileType.type;
   }
  
%}
%line
%type Token
%state YYINITIAL
%unicode

InputCharakter=[^\r\n\t\ \;\.\,\:\?\!]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
newLine=[\u2028\u2029\u000A\u000B\u000C\u000D\u0085] | \u000D\u000A | \n | \r\n | \r

%% 
{newLine}          { return (new EOLToken(yyline)); } 

{NONNEWLINE_WHITE_SPACE_CHAR}+ { /*no nothing*/ }


<YYINITIAL> "-"+		 	 { return (new Token("-", Token.TYPE_STRING, yyline));} 

<YYINITIAL> {InputCharakter}+     { return (new Token(yytext(), Token.TYPE_STRING, yyline)); }

<YYINITIAL> ";"		 		 { return (new Token(yytext(), 100, yyline));} 
<YYINITIAL> ","		 		 { return (new Token(yytext(), 106, yyline));} 
<YYINITIAL> "!"			 	 { return (new Token(yytext(), 110, yyline));} 
<YYINITIAL> "."	   			 { return (new Token(yytext(), 121, yyline));} 
<YYINITIAL> ":"		 	 	 { return (new Token(yytext(), 124, yyline));} 
<YYINITIAL> "?"		 		 { return (new Token(yytext(), 125, yyline));} 
