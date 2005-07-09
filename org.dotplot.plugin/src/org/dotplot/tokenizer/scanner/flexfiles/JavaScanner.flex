package org.dotplot.tokenizer.scanner;

import org.dotplot.tokenizer.*;
%%

%class JavaScanner
%extends BaseScanner
%public
%{

  private static TokenType[] tokenTypes = {
  	new TokenType(Token.TYPE_STRING, "comment", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_NUMBER, "number", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_IDENT, "identifier", TokenType.KIND_OTHER),

  	new TokenType( 0, "abstract", TokenType.KIND_KEYWORD),
  	new TokenType( 1, "boolean", TokenType.KIND_KEYWORD),
  	new TokenType( 2, "break", TokenType.KIND_KEYWORD),
  	new TokenType( 3, "byte", TokenType.KIND_KEYWORD),
  	new TokenType( 4, "case", TokenType.KIND_KEYWORD),
  	new TokenType( 5, "catch", TokenType.KIND_KEYWORD),
  	new TokenType( 7, "char", TokenType.KIND_KEYWORD),
  	new TokenType( 8, "class", TokenType.KIND_KEYWORD),
  	new TokenType( 9, "const", TokenType.KIND_KEYWORD),
  	new TokenType(10, "continue", TokenType.KIND_KEYWORD),
  	new TokenType(11, "default", TokenType.KIND_KEYWORD),
  	new TokenType(12, "do", TokenType.KIND_KEYWORD),
  	new TokenType(13, "double", TokenType.KIND_KEYWORD),
  	new TokenType(14, "else", TokenType.KIND_KEYWORD),
  	new TokenType(15, "extends", TokenType.KIND_KEYWORD),
  	new TokenType(16, "final", TokenType.KIND_KEYWORD),
  	new TokenType(17, "finally", TokenType.KIND_KEYWORD),
  	new TokenType(18, "float", TokenType.KIND_KEYWORD),
  	new TokenType(19, "for", TokenType.KIND_KEYWORD),
  	new TokenType(20, "goto", TokenType.KIND_KEYWORD),
  	new TokenType(21, "if", TokenType.KIND_KEYWORD),
  	new TokenType(22, "implements", TokenType.KIND_KEYWORD),
  	new TokenType(23, "import", TokenType.KIND_KEYWORD),
  	new TokenType(24, "instanceof", TokenType.KIND_KEYWORD),
  	new TokenType(25, "int", TokenType.KIND_KEYWORD),
  	new TokenType(26, "interface", TokenType.KIND_KEYWORD),
  	new TokenType(27, "long", TokenType.KIND_KEYWORD),
  	new TokenType(28, "native", TokenType.KIND_KEYWORD),
  	new TokenType(29, "new", TokenType.KIND_KEYWORD),
  	new TokenType(30, "package", TokenType.KIND_KEYWORD),
  	new TokenType(31, "private", TokenType.KIND_KEYWORD),
  	new TokenType(32, "protected", TokenType.KIND_KEYWORD),
  	new TokenType(33, "public", TokenType.KIND_KEYWORD),
  	new TokenType(34, "return", TokenType.KIND_KEYWORD),
  	new TokenType(35, "short", TokenType.KIND_KEYWORD),
  	new TokenType(36, "static", TokenType.KIND_KEYWORD),
  	new TokenType(37, "strictfp", TokenType.KIND_KEYWORD),
  	new TokenType(38, "super", TokenType.KIND_KEYWORD),
  	new TokenType(39, "switch", TokenType.KIND_KEYWORD),
  	new TokenType(40, "synchronied", TokenType.KIND_KEYWORD),
  	new TokenType(41, "this", TokenType.KIND_KEYWORD),
  	new TokenType(42, "throw", TokenType.KIND_KEYWORD),
  	new TokenType(43, "throws", TokenType.KIND_KEYWORD),
  	new TokenType(44, "transient", TokenType.KIND_KEYWORD),
  	new TokenType(45, "try", TokenType.KIND_KEYWORD),
  	new TokenType(46, "void", TokenType.KIND_KEYWORD),
  	new TokenType(47, "volatile", TokenType.KIND_KEYWORD),
  	new TokenType(48, "while", TokenType.KIND_KEYWORD),
  	  	
  	new TokenType(100, ";", TokenType.KIND_OTHER),
  	new TokenType(101, "==", TokenType.KIND_OPERATOR),
  	new TokenType(102, "+", TokenType.KIND_OPERATOR),
  	new TokenType(103, "-", TokenType.KIND_OPERATOR),
  	new TokenType(104, "/", TokenType.KIND_OPERATOR),
  	new TokenType(105, "*", TokenType.KIND_OPERATOR),
  	new TokenType(106, ",", TokenType.KIND_OPERATOR),
  	new TokenType(107, ">=", TokenType.KIND_OPERATOR),
  	new TokenType(108, "<=", TokenType.KIND_OPERATOR),
  	new TokenType(109, "=", TokenType.KIND_OPERATOR),
  	new TokenType(110, "!", TokenType.KIND_OPERATOR),
  	new TokenType(111, "\"", TokenType.KIND_OTHER),
  	new TokenType(112, "&", TokenType.KIND_OPERATOR),
  	new TokenType(113, "{", TokenType.KIND_OTHER),
  	new TokenType(114, "}", TokenType.KIND_OTHER),
  	new TokenType(115, "(", TokenType.KIND_OTHER),
  	new TokenType(116, ")", TokenType.KIND_OTHER),
  	new TokenType(117, "[", TokenType.KIND_OTHER),
  	new TokenType(118, "]", TokenType.KIND_OTHER),
  	new TokenType(119, "|", TokenType.KIND_OPERATOR),
  	new TokenType(120, "\\", TokenType.KIND_OTHER),
  	new TokenType(121, ".", TokenType.KIND_OPERATOR),
  	new TokenType(122, ">", TokenType.KIND_OPERATOR),
  	new TokenType(123, "<", TokenType.KIND_OPERATOR),
  	new TokenType(124, ":", TokenType.KIND_OTHER),
  	new TokenType(125, "?", TokenType.KIND_OTHER)
  	
  };

  private int comment_count;

  /** 
  * Defaultconstructor : erzeugt einen neuen JavaScanner
  */
  public JavaScanner () {
    comment_count = 0;
  }
  
  public TokenType[] getTokenTypes(){
  	return tokenTypes;
  }
  
  
%}
%type Token
%line
%state YYINITIAL
%state COMMENT 
%state LINE_COMMENT
%unicode

InputCharakter=[^\r\n\t\ ]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
newLine=[\u2028\u2029\u000A\u000B\u000C\u000D\u0085] | \u000D\u000A | \n | \r\n | \r
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
STRING_TEXT=(\".+\")

%% 

<YYINITIAL> abstract	 { return (new KeyWordToken(yytext(),  0, yyline));} 
<YYINITIAL> boolean		 { return (new KeyWordToken(yytext(),  1, yyline));} 
<YYINITIAL> break		 { return (new KeyWordToken(yytext(),  2, yyline));} 
<YYINITIAL> byte		 { return (new KeyWordToken(yytext(),  3, yyline));} 
<YYINITIAL> case		 { return (new KeyWordToken(yytext(),  4, yyline));} 
<YYINITIAL> catch		 { return (new KeyWordToken(yytext(),  5, yyline));} 
<YYINITIAL> char		 { return (new KeyWordToken(yytext(),  7, yyline));} 
<YYINITIAL> class		 { return (new KeyWordToken(yytext(),  8, yyline));} 
<YYINITIAL> const		 { return (new KeyWordToken(yytext(),  9, yyline));} 
<YYINITIAL> continue	 { return (new KeyWordToken(yytext(), 10, yyline));} 
<YYINITIAL> default		 { return (new KeyWordToken(yytext(), 11, yyline));} 
<YYINITIAL> do			 { return (new KeyWordToken(yytext(), 12, yyline));} 
<YYINITIAL> double		 { return (new KeyWordToken(yytext(), 13, yyline));} 
<YYINITIAL> else		 { return (new KeyWordToken(yytext(), 14, yyline));} 
<YYINITIAL> extends		 { return (new KeyWordToken(yytext(), 15, yyline));} 
<YYINITIAL> final		 { return (new KeyWordToken(yytext(), 16, yyline));} 
<YYINITIAL> finally		 { return (new KeyWordToken(yytext(), 17, yyline));} 
<YYINITIAL> float		 { return (new KeyWordToken(yytext(), 18, yyline));} 
<YYINITIAL> for			 { return (new KeyWordToken(yytext(), 19, yyline));} 
<YYINITIAL> goto		 { return (new KeyWordToken(yytext(), 20, yyline));} 
<YYINITIAL> if			 { return (new KeyWordToken(yytext(), 21, yyline));} 
<YYINITIAL> implements	 { return (new KeyWordToken(yytext(), 22, yyline));} 
<YYINITIAL> import		 { return (new KeyWordToken(yytext(), 23, yyline));} 
<YYINITIAL> instanceof	 { return (new KeyWordToken(yytext(), 24, yyline));} 
<YYINITIAL> int			 { return (new KeyWordToken(yytext(), 25, yyline));} 
<YYINITIAL> interface	 { return (new KeyWordToken(yytext(), 26, yyline));} 
<YYINITIAL> long		 { return (new KeyWordToken(yytext(), 27, yyline));} 
<YYINITIAL> native		 { return (new KeyWordToken(yytext(), 28, yyline));} 
<YYINITIAL> new			 { return (new KeyWordToken(yytext(), 29, yyline));} 
<YYINITIAL> package		 { return (new KeyWordToken(yytext(), 30, yyline));} 
<YYINITIAL> private		 { return (new KeyWordToken(yytext(), 31, yyline));} 
<YYINITIAL> protected	 { return (new KeyWordToken(yytext(), 32, yyline));} 
<YYINITIAL> public		 { return (new KeyWordToken(yytext(), 33, yyline));} 
<YYINITIAL> return		 { return (new KeyWordToken(yytext(), 34, yyline));} 
<YYINITIAL> short		 { return (new KeyWordToken(yytext(), 35, yyline));} 
<YYINITIAL> static		 { return (new KeyWordToken(yytext(), 36, yyline));} 
<YYINITIAL> strictfp	 { return (new KeyWordToken(yytext(), 37, yyline));} 
<YYINITIAL> super		 { return (new KeyWordToken(yytext(), 38, yyline));} 
<YYINITIAL> switch		 { return (new KeyWordToken(yytext(), 39, yyline));} 
<YYINITIAL> synchronized { return (new KeyWordToken(yytext(), 40, yyline));} 
<YYINITIAL> this		 { return (new KeyWordToken(yytext(), 41, yyline));} 
<YYINITIAL> throw		 { return (new KeyWordToken(yytext(), 42, yyline));} 
<YYINITIAL> throws		 { return (new KeyWordToken(yytext(), 43, yyline));} 
<YYINITIAL> transient	 { return (new KeyWordToken(yytext(), 44, yyline));} 
<YYINITIAL> try 		 { return (new KeyWordToken(yytext(), 45, yyline));} 
<YYINITIAL> void		 { return (new KeyWordToken(yytext(), 46, yyline));} 
<YYINITIAL> volatile	 { return (new KeyWordToken(yytext(), 47, yyline));} 
<YYINITIAL> while		 { return (new KeyWordToken(yytext(), 48, yyline));} 

<YYINITIAL> ";"		 	 { return (new Token(yytext(), 100, yyline));} 
<YYINITIAL> "=="		 { return (new Token(yytext(), 101, yyline));} 
<YYINITIAL> "+"		 	 { return (new Token(yytext(), 102, yyline));} 
<YYINITIAL> "-"		 	 { return (new Token(yytext(), 103, yyline));} 
<YYINITIAL> "/"		 	 { return (new Token(yytext(), 104, yyline));} 
<YYINITIAL> "*"		 	 { return (new Token(yytext(), 105, yyline));} 
<YYINITIAL> ","		 	 { return (new Token(yytext(), 106, yyline));} 
<YYINITIAL> ">="		 { return (new Token(yytext(), 107, yyline));} 
<YYINITIAL> "<="		 { return (new Token(yytext(), 108, yyline));} 
<YYINITIAL> "="		 	 { return (new Token(yytext(), 109, yyline));} 
<YYINITIAL> "!"		 	 { return (new Token(yytext(), 110, yyline));} 
<YYINITIAL> "\""	 	 { return (new Token(yytext(), 111, yyline));} 
<YYINITIAL> "&"		 	 { return (new Token(yytext(), 112, yyline));} 
<YYINITIAL> "{"		 	 { return (new Token(yytext(), 113, yyline));} 
<YYINITIAL> "}"		 	 { return (new Token(yytext(), 114, yyline));} 
<YYINITIAL> "("		 	 { return (new Token(yytext(), 115, yyline));} 
<YYINITIAL> ")"		 	 { return (new Token(yytext(), 116, yyline));} 
<YYINITIAL> "["		 	 { return (new Token(yytext(), 117, yyline));} 
<YYINITIAL> "]"		 	 { return (new Token(yytext(), 118, yyline));} 
<YYINITIAL> "|"	   		 { return (new Token(yytext(), 119, yyline));} 
<YYINITIAL> \\	   		 { return (new Token(yytext(), 120, yyline));} 
<YYINITIAL> "."	   		 { return (new Token(yytext(), 121, yyline));} 
<YYINITIAL> ">"		 	 { return (new Token(yytext(), 122, yyline));} 
<YYINITIAL> "<"		 	 { return (new Token(yytext(), 123, yyline));} 
<YYINITIAL> ":"		 	 { return (new Token(yytext(), 124, yyline));} 
<YYINITIAL> "?"		 	 { return (new Token(yytext(), 125, yyline));} 

{NONNEWLINE_WHITE_SPACE_CHAR}+   {/*do nothing*/}

<YYINITIAL,COMMENT> {newLine}	 { return (new EOLToken(yyline)); }  

<YYINITIAL> "/*" 		 { yybegin(COMMENT); comment_count = comment_count + 1;
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }

<YYINITIAL> "//"		 { yybegin(LINE_COMMENT);
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }

<COMMENT> "/*"           { comment_count = comment_count + 1; 
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }
						 
<COMMENT> "*/"			 { comment_count = comment_count - 1; 
  			       			if (comment_count == 0) {
   	                           yybegin(YYINITIAL);
						   }
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }
						 
<COMMENT> {InputCharakter}+ {return (new Token(yytext(), Token.TYPE_STRING, yyline)); }

<LINE_COMMENT> {InputCharakter}+ {return (new Token(yytext(), Token.TYPE_STRING, yyline)); }
<LINE_COMMENT> {newLine}	 { yybegin(YYINITIAL);
							  return (new EOLToken(yyline)); 
							 }  


<YYINITIAL> {STRING_TEXT} { return (new Token(yytext(), Token.TYPE_STRING, yyline)); } 

<YYINITIAL> [0-9]+                  { return (new Token(yytext(), Token.TYPE_NUMBER, yyline)); }
<YYINITIAL> [a-zA-Z_][a-zA-Z1-9_]*	{ return (new Token(yytext(), Token.TYPE_IDENT, yyline)); }
<YYINITIAL> .                     	{ return (new Token(yytext(), Token.TYPE_STRING, yyline)); }

