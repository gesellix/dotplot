package org.dotplot.tokenizer.scanner;

import org.dotplot.tokenizer.*;

%%

%class PHPScanner
%extends BaseScanner
%public
%{

  protected static TokenType[] tokenTypes = {
  	new TokenType(Token.TYPE_STRING, "comment", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_NUMBER, "number", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_IDENT, "identifier", TokenType.KIND_OTHER),
  	
    new TokenType( 0, "and", TokenType.KIND_KEYWORD),
    new TokenType( 1, "E_PARSE", TokenType.KIND_KEYWORD),
    new TokenType( 2, "old_function", TokenType.KIND_KEYWORD),
    new TokenType( 3, "$argv", TokenType.KIND_KEYWORD),
    new TokenType( 4, "E_ERROR", TokenType.KIND_KEYWORD),
    new TokenType( 5, "or", TokenType.KIND_KEYWORD),
    new TokenType( 6, "as", TokenType.KIND_KEYWORD),
    new TokenType( 7, "E_WARNING", TokenType.KIND_KEYWORD),
    new TokenType( 8, "parent", TokenType.KIND_KEYWORD),
    new TokenType( 9, "argc", TokenType.KIND_KEYWORD),
    new TokenType(10, "eval", TokenType.KIND_KEYWORD),
    new TokenType(11, "PHP_OS", TokenType.KIND_KEYWORD),
    new TokenType(12, "break", TokenType.KIND_KEYWORD),
    new TokenType(13, "exit()", TokenType.KIND_KEYWORD),
    new TokenType(14, "$PHP_SELF", TokenType.KIND_KEYWORD),
    new TokenType(15, "case", TokenType.KIND_KEYWORD),
    new TokenType(16, "PHP_VERSION", TokenType.KIND_KEYWORD),
    new TokenType(17, "cfunction", TokenType.KIND_KEYWORD),
    new TokenType(18, "FALSE", TokenType.KIND_KEYWORD),
    new TokenType(19, "print()", TokenType.KIND_KEYWORD),
    new TokenType(20, "class", TokenType.KIND_KEYWORD),
    new TokenType(21, "for", TokenType.KIND_KEYWORD),
    new TokenType(22, "require()", TokenType.KIND_KEYWORD),
    new TokenType(23, "continue", TokenType.KIND_KEYWORD),
    new TokenType(24, "foreach", TokenType.KIND_KEYWORD),
    new TokenType(25, "require_once()", TokenType.KIND_KEYWORD),
    new TokenType(26, "declare", TokenType.KIND_KEYWORD),
    new TokenType(27, "function", TokenType.KIND_KEYWORD),
    new TokenType(28, "return()", TokenType.KIND_KEYWORD),
    new TokenType(29, "default", TokenType.KIND_KEYWORD),
    new TokenType(30, "$HTTP_COOKIE_VARS", TokenType.KIND_KEYWORD),
    new TokenType(31, "static", TokenType.KIND_KEYWORD),
    new TokenType(32, "do", TokenType.KIND_KEYWORD),
    new TokenType(33, "$HTTP_GET_VARS", TokenType.KIND_KEYWORD),
    new TokenType(34, "switch", TokenType.KIND_KEYWORD),
    new TokenType(35, "die()", TokenType.KIND_KEYWORD),
    new TokenType(36, "$HTTP_POST_VARS", TokenType.KIND_KEYWORD),
    new TokenType(37, "stdClass", TokenType.KIND_KEYWORD),
    new TokenType(38, "echo()", TokenType.KIND_KEYWORD),
    new TokenType(39, "$HTTP_POST_FILES", TokenType.KIND_KEYWORD),
    new TokenType(40, "$this", TokenType.KIND_KEYWORD),
    new TokenType(41, "else", TokenType.KIND_KEYWORD),
    new TokenType(42, "$HTTP_ENV_VARS", TokenType.KIND_KEYWORD),
    new TokenType(43, "TRUE", TokenType.KIND_KEYWORD),
    new TokenType(44, "elseif", TokenType.KIND_KEYWORD),
    new TokenType(45, "$HTTP_SERVER_VARS", TokenType.KIND_KEYWORD),
    new TokenType(46, "var", TokenType.KIND_KEYWORD),
    new TokenType(47, "empty()", TokenType.KIND_KEYWORD),
    new TokenType(48, "if", TokenType.KIND_KEYWORD),
    new TokenType(49, "xor", TokenType.KIND_KEYWORD),
    new TokenType(50, "enddeclare", TokenType.KIND_KEYWORD),
    new TokenType(51, "include()", TokenType.KIND_KEYWORD),
    new TokenType(52, "virtual()", TokenType.KIND_KEYWORD),
    new TokenType(53, "endfor", TokenType.KIND_KEYWORD),
    new TokenType(54, "include_once", TokenType.KIND_KEYWORD),
    new TokenType(55, "while", TokenType.KIND_KEYWORD),
    new TokenType(56, "endforeach", TokenType.KIND_KEYWORD),
    new TokenType(57, "global", TokenType.KIND_KEYWORD),
    new TokenType(58, "__FILE__", TokenType.KIND_KEYWORD),
    new TokenType(59, "endif", TokenType.KIND_KEYWORD),
    new TokenType(60, "list()", TokenType.KIND_KEYWORD),
    new TokenType(61, "__LINE__", TokenType.KIND_KEYWORD),
    new TokenType(62, "endswitch", TokenType.KIND_KEYWORD),
    new TokenType(63, "new", TokenType.KIND_KEYWORD),
    new TokenType(64, "__sleep", TokenType.KIND_KEYWORD),
    new TokenType(65, "endwhile", TokenType.KIND_KEYWORD),
    new TokenType(66, "not", TokenType.KIND_KEYWORD),
    new TokenType(67, "__wakeup", TokenType.KIND_KEYWORD),
    new TokenType(68, "E_ALL", TokenType.KIND_KEYWORD),
    new TokenType(69, "NULL", TokenType.KIND_KEYWORD),
    new TokenType(70, "extends", TokenType.KIND_KEYWORD),
  	
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
  	new TokenType(125, "?", TokenType.KIND_OTHER),
  	new TokenType(126, "===", TokenType.KIND_OPERATOR),

  	new TokenType(126, "HtmL", TokenType.KIND_OTHER)
  	
  };

  private int comment_count;

  /** 
  * Defaultconstructor : erzeugt einen neuen PHPScanner
  */
  public PHPScanner () {
    comment_count = 0;
  }
  
  public TokenType[] getTokenTypes(){
  	return tokenTypes;
  }

  public ISourceType getSourceType() {
	return PHPFileType.type;
  }
  
  
%}
%type Token
%line
%state YYINITIAL
%state COMMENT 
%state LINE_COMMENT
%state PHP
%unicode

InputCharakter=[^\r\n\t\ ]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
newLine=[\u2028\u2029\u000A\u000B\u000C\u000D\u0085] | \u000D\u000A | \n | \r\n
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
STRING_TEXT=(\".+\")

%% 


<PHP> and 				{ return (new KeyWordToken(yytext(),  0, yyline));} 
<PHP> E_PARSE 			{ return (new KeyWordToken(yytext(),  1, yyline));} 
<PHP> old_function	 	{ return (new KeyWordToken(yytext(),  2, yyline));} 
<PHP> \$argv 				{ return (new KeyWordToken(yytext(),  3, yyline));} 
<PHP> E_ERROR 			{ return (new KeyWordToken(yytext(),  4, yyline));} 
<PHP> or 					{ return (new KeyWordToken(yytext(),  5, yyline));} 
<PHP> as 					{ return (new KeyWordToken(yytext(),  6, yyline));} 
<PHP> E_WARNING	 		{ return (new KeyWordToken(yytext(),  7, yyline));} 
<PHP> parent 				{ return (new KeyWordToken(yytext(),  8, yyline));} 
<PHP> \$argc 				{ return (new KeyWordToken(yytext(),  9, yyline));} 
<PHP> eval 				{ return (new KeyWordToken(yytext(), 10, yyline));} 
<PHP> PHP_OS 				{ return (new KeyWordToken(yytext(), 11, yyline));} 
<PHP> break 				{ return (new KeyWordToken(yytext(), 12, yyline));} 
<PHP> exit     			{ return (new KeyWordToken(yytext(), 13, yyline));} 
<PHP> \$PHP_SELF	 		{ return (new KeyWordToken(yytext(), 14, yyline));} 
<PHP> case 				{ return (new KeyWordToken(yytext(), 15, yyline));} 
<PHP> PHP_VERSION 		{ return (new KeyWordToken(yytext(), 16, yyline));} 
<PHP> cfunction	 		{ return (new KeyWordToken(yytext(), 17, yyline));} 
<PHP> FALSE 				{ return (new KeyWordToken(yytext(), 18, yyline));} 
<PHP> print     			{ return (new KeyWordToken(yytext(), 19, yyline));} 
<PHP> class 				{ return (new KeyWordToken(yytext(), 20, yyline));} 
<PHP> for 				{ return (new KeyWordToken(yytext(), 21, yyline));} 
<PHP> require     		{ return (new KeyWordToken(yytext(), 22, yyline));} 
<PHP> continue 			{ return (new KeyWordToken(yytext(), 23, yyline));} 
<PHP> foreach 			{ return (new KeyWordToken(yytext(), 24, yyline));} 
<PHP> require_once    	{ return (new KeyWordToken(yytext(), 25, yyline));} 
<PHP> declare 			{ return (new KeyWordToken(yytext(), 26, yyline));} 
<PHP> function 			{ return (new KeyWordToken(yytext(), 27, yyline));} 
<PHP> return     			{ return (new KeyWordToken(yytext(), 28, yyline));} 
<PHP> default 			{ return (new KeyWordToken(yytext(), 29, yyline));} 
<PHP> \$HTTP_COOKIE_VARS 	{ return (new KeyWordToken(yytext(), 30, yyline));} 
<PHP> static 				{ return (new KeyWordToken(yytext(), 31, yyline));} 
<PHP> do 					{ return (new KeyWordToken(yytext(), 32, yyline));} 
<PHP> \$HTTP_GET_VARS 	{ return (new KeyWordToken(yytext(), 33, yyline));} 
<PHP> switch 				{ return (new KeyWordToken(yytext(), 34, yyline));} 
<PHP> die     			{ return (new KeyWordToken(yytext(), 35, yyline));} 
<PHP> \$HTTP_POST_VARS	{ return (new KeyWordToken(yytext(), 36, yyline));} 
<PHP> stdClass 			{ return (new KeyWordToken(yytext(), 37, yyline));} 
<PHP> echo     	 		{ return (new KeyWordToken(yytext(), 38, yyline));} 
<PHP> \$HTTP_POST_FILES	{ return (new KeyWordToken(yytext(), 39, yyline));} 
<PHP> \$this 				{ return (new KeyWordToken(yytext(), 40, yyline));} 
<PHP> else 				{ return (new KeyWordToken(yytext(), 41, yyline));} 
<PHP> \$HTTP_ENV_VARS 	{ return (new KeyWordToken(yytext(), 42, yyline));} 
<PHP> TRUE 				{ return (new KeyWordToken(yytext(), 43, yyline));} 
<PHP> elseif 				{ return (new KeyWordToken(yytext(), 44, yyline));} 
<PHP> \$HTTP_SERVER_VARS 	{ return (new KeyWordToken(yytext(), 45, yyline));} 
<PHP> var 				{ return (new KeyWordToken(yytext(), 46, yyline));} 
<PHP> empty     			{ return (new KeyWordToken(yytext(), 47, yyline));} 
<PHP> if 					{ return (new KeyWordToken(yytext(), 48, yyline));} 
<PHP> xor 				{ return (new KeyWordToken(yytext(), 49, yyline));} 
<PHP> enddeclare 			{ return (new KeyWordToken(yytext(), 50, yyline));} 
<PHP> include     		{ return (new KeyWordToken(yytext(), 51, yyline));} 
<PHP> virtual     		{ return (new KeyWordToken(yytext(), 52, yyline));} 
<PHP> endfor 				{ return (new KeyWordToken(yytext(), 53, yyline));} 
<PHP> include_once     	{ return (new KeyWordToken(yytext(), 54, yyline));} 
<PHP> while 				{ return (new KeyWordToken(yytext(), 55, yyline));} 
<PHP> endforeach 			{ return (new KeyWordToken(yytext(), 56, yyline));} 
<PHP> global 				{ return (new KeyWordToken(yytext(), 57, yyline));} 
<PHP> __FILE__	 		{ return (new KeyWordToken(yytext(), 58, yyline));} 
<PHP> endif	 			{ return (new KeyWordToken(yytext(), 59, yyline));} 
<PHP> list     			{ return (new KeyWordToken(yytext(), 60, yyline));} 
<PHP> __LINE__ 			{ return (new KeyWordToken(yytext(), 61, yyline));} 
<PHP> endswitch	 		{ return (new KeyWordToken(yytext(), 62, yyline));} 
<PHP> new 				{ return (new KeyWordToken(yytext(), 63, yyline));} 
<PHP> __sleep 			{ return (new KeyWordToken(yytext(), 64, yyline));} 
<PHP> endwhile	 		{ return (new KeyWordToken(yytext(), 65, yyline));} 
<PHP> not 				{ return (new KeyWordToken(yytext(), 66, yyline));} 
<PHP> __wakeup 			{ return (new KeyWordToken(yytext(), 67, yyline));} 
<PHP> E_ALL 				{ return (new KeyWordToken(yytext(), 68, yyline));} 
<PHP> NULL				{ return (new KeyWordToken(yytext(), 69, yyline));} 
<PHP> extends 			{ return (new KeyWordToken(yytext(), 70, yyline));} 

<PHP> ";"		 		 { return (new Token(yytext(), 100, yyline));} 
<PHP> "=="			 { return (new Token(yytext(), 101, yyline));} 
<PHP> "+"		 		 { return (new Token(yytext(), 102, yyline));} 
<PHP> "-"			 	 { return (new Token(yytext(), 103, yyline));} 
<PHP> "/"			 	 { return (new Token(yytext(), 104, yyline));} 
<PHP> "*"			 	 { return (new Token(yytext(), 105, yyline));} 
<PHP> ","		 		 { return (new Token(yytext(), 106, yyline));} 
<PHP> ">="			 { return (new Token(yytext(), 107, yyline));} 
<PHP> "<="			 { return (new Token(yytext(), 108, yyline));} 
<PHP> "="		 		 { return (new Token(yytext(), 109, yyline));} 
<PHP> "!"			 	 { return (new Token(yytext(), 110, yyline));} 
<PHP> "\""		 	 { return (new Token(yytext(), 111, yyline));} 
<PHP> "&"			 	 { return (new Token(yytext(), 112, yyline));} 
<PHP> "{"		 		 { return (new Token(yytext(), 113, yyline));} 
<PHP> "}"			 	 { return (new Token(yytext(), 114, yyline));} 
<PHP> "("			 	 { return (new Token(yytext(), 115, yyline));} 
<PHP> ")"		 		 { return (new Token(yytext(), 116, yyline));} 
<PHP> "["			 	 { return (new Token(yytext(), 117, yyline));} 
<PHP> "]"			 	 { return (new Token(yytext(), 118, yyline));} 
<PHP> "|"		   		 { return (new Token(yytext(), 119, yyline));} 
<PHP> \\	   			 { return (new Token(yytext(), 120, yyline));} 
<PHP> "."	   			 { return (new Token(yytext(), 121, yyline));} 
<PHP> ">"			 	 { return (new Token(yytext(), 122, yyline));} 
<PHP> "<"			 	 { return (new Token(yytext(), 123, yyline));} 
<PHP> ":"		 	 	 { return (new Token(yytext(), 124, yyline));} 
<PHP> "?"		 		 { return (new Token(yytext(), 125, yyline));} 
<PHP> "==="		 	 { return (new Token(yytext(), 126, yyline));} 

<YYINITIAL> "<?"		 {yybegin(PHP); return (new Token(yytext(),200,yyline));}

<YYINITIAL> {InputCharakter}+ {return (new Token(yytext(), 200, yyline)); }

{NONNEWLINE_WHITE_SPACE_CHAR}+   {/*do nothing*/}

<YYINITIAL,COMMENT, PHP> {newLine}	 { return (new EOLToken(yyline)); }  

<PHP>		"?>"		 {yybegin(YYINITIAL);return(new Token(yytext(),200,yyline));}

<PHP> "/*" 		 { yybegin(COMMENT); comment_count = comment_count + 1;
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }

<PHP> "//"		 { yybegin(LINE_COMMENT);
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }

<COMMENT> "/*"           { comment_count = comment_count + 1; 
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }
						 
<COMMENT> "*/"			 { comment_count = comment_count - 1; 
  			       			if (comment_count == 0) {
   	                           yybegin(PHP);
						   }
						   return (new Token(yytext(), Token.TYPE_STRING, yyline));
						 }
						 
<COMMENT> {InputCharakter}+ {return (new Token(yytext(), Token.TYPE_STRING, yyline)); }

<LINE_COMMENT> {InputCharakter}+ {return (new Token(yytext(), Token.TYPE_STRING, yyline)); }
<LINE_COMMENT> {newLine}	 { yybegin(PHP);
							  return (new EOLToken(yyline)); 
							 }  

<PHP> {STRING_TEXT} { return (new Token(yytext(), Token.TYPE_STRING, yyline)); } 


<PHP> [0-9]+                  { return (new Token(yytext(), Token.TYPE_NUMBER, yyline)); }
<PHP> [a-zA-Z_][a-zA-Z1-9_]*	{ return (new Token(yytext(), Token.TYPE_IDENT, yyline)); }

<YYINITIAL,PHP> .                     	{ return (new Token(yytext(), Token.TYPE_STRING, yyline)); }

