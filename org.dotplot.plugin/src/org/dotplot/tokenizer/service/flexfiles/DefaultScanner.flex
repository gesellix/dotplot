package org.dotplot.tokenizer.scanner;

import org.dotplot.tokenizer.*;

%%

%class DefaultScanner
%extends BaseScanner
%public
%{

  protected static TokenType[] tokenTypes = {
  	new TokenType(Token.TYPE_STRING, "Token", TokenType.KIND_OTHER)
  	
  };

  /** 
   * Defaultconstructor : erzeugt einen neuen TextScanner
   */
  public DefaultScanner () {
    
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

InputCharakter=[^\r\n\t\ ]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
newLine=[\u2028\u2029\u000A\u000B\u000C\u000D\u0085] | \u000D\u000A | \n | \r\n | \r

%% 
{newLine}          { return (new EOLToken(yyline)); } 

{NONNEWLINE_WHITE_SPACE_CHAR}+ { /*no nothing*/ }

<YYINITIAL> {InputCharakter}+     { return (new Token(yytext(), Token.TYPE_STRING, yyline)); }
