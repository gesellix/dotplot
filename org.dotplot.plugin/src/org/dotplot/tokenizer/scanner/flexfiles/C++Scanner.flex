package org.dotplot.tokenizer.scanner;
import org.dotplot.tokenizer.*;


%%

%class CPlusPlusScanner
%extends BaseScanner
%public
%{

  private static TokenType[] tokenTypes = {
  	new TokenType(Token.TYPE_STRING, "comment", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_IDENT, "identifier", TokenType.KIND_OTHER),
  	new TokenType(Token.TYPE_NUMBER, "number", TokenType.KIND_OTHER),
  	
  	new TokenType(0, "and", TokenType.KIND_KEYWORD),
  	new TokenType(1, "and_eq", TokenType.KIND_KEYWORD),
  	new TokenType(2, "asm", TokenType.KIND_KEYWORD),
  	new TokenType(3, "auto", TokenType.KIND_KEYWORD),
  	new TokenType(4, "bitand", TokenType.KIND_KEYWORD),
  	new TokenType(5, "bitor", TokenType.KIND_KEYWORD),
  	new TokenType(6, "bool", TokenType.KIND_KEYWORD),
  	new TokenType(7, "break", TokenType.KIND_KEYWORD),
  	new TokenType(8, "case", TokenType.KIND_KEYWORD),
  	new TokenType(9, "catch", TokenType.KIND_KEYWORD),
  	new TokenType(10, "char", TokenType.KIND_KEYWORD),
  	new TokenType(11, "class", TokenType.KIND_KEYWORD),
  	new TokenType(12, "compl", TokenType.KIND_KEYWORD),
  	new TokenType(13, "const", TokenType.KIND_KEYWORD),
  	new TokenType(14, "const_cast", TokenType.KIND_KEYWORD),
  	new TokenType(15, "continue", TokenType.KIND_KEYWORD),
  	new TokenType(16, "default", TokenType.KIND_KEYWORD),
  	new TokenType(17, "delete", TokenType.KIND_KEYWORD),
  	new TokenType(18, "do", TokenType.KIND_KEYWORD),
  	new TokenType(19, "double", TokenType.KIND_KEYWORD),
  	new TokenType(20, "dynamic_cast", TokenType.KIND_KEYWORD),
  	new TokenType(21, "else", TokenType.KIND_KEYWORD),
  	new TokenType(22, "enum", TokenType.KIND_KEYWORD),
  	new TokenType(23, "explicit", TokenType.KIND_KEYWORD),
  	new TokenType(24, "export", TokenType.KIND_KEYWORD),
  	new TokenType(25, "extern", TokenType.KIND_KEYWORD),
  	new TokenType(26, "false", TokenType.KIND_KEYWORD),
  	new TokenType(27, "float", TokenType.KIND_KEYWORD),
  	new TokenType(28, "for", TokenType.KIND_KEYWORD),
  	new TokenType(29, "friend", TokenType.KIND_KEYWORD),
  	new TokenType(30, "goto", TokenType.KIND_KEYWORD),
  	new TokenType(31, "if", TokenType.KIND_KEYWORD),
  	new TokenType(32, "inline", TokenType.KIND_KEYWORD),
  	new TokenType(33, "int", TokenType.KIND_KEYWORD),
  	new TokenType(34, "long", TokenType.KIND_KEYWORD),
  	new TokenType(35, "mutable", TokenType.KIND_KEYWORD),
  	new TokenType(36, "namespace", TokenType.KIND_KEYWORD),
  	new TokenType(37, "new", TokenType.KIND_KEYWORD),
  	new TokenType(38, "not", TokenType.KIND_KEYWORD),
  	new TokenType(39, "not_eq", TokenType.KIND_KEYWORD),
  	new TokenType(40, "operator", TokenType.KIND_KEYWORD),
  	new TokenType(41, "or", TokenType.KIND_KEYWORD),
  	new TokenType(42, "or_eq", TokenType.KIND_KEYWORD),
  	new TokenType(43, "private", TokenType.KIND_KEYWORD),
  	new TokenType(44, "protected", TokenType.KIND_KEYWORD),
  	new TokenType(45, "public", TokenType.KIND_KEYWORD),
  	new TokenType(46, "register", TokenType.KIND_KEYWORD),
  	new TokenType(47, "reinterpret_cast", TokenType.KIND_KEYWORD),
  	new TokenType(48, "return", TokenType.KIND_KEYWORD),
  	new TokenType(49, "short", TokenType.KIND_KEYWORD),
  	new TokenType(50, "signed", TokenType.KIND_KEYWORD),
  	new TokenType(51, "sizeof", TokenType.KIND_KEYWORD),
  	new TokenType(52, "static", TokenType.KIND_KEYWORD),
  	new TokenType(53, "static_cast", TokenType.KIND_KEYWORD),
  	new TokenType(54, "struct", TokenType.KIND_KEYWORD),
  	new TokenType(55, "switch", TokenType.KIND_KEYWORD),
  	new TokenType(56, "template", TokenType.KIND_KEYWORD),
  	new TokenType(57, "this", TokenType.KIND_KEYWORD),
  	new TokenType(58, "throw", TokenType.KIND_KEYWORD),
  	new TokenType(59, "true", TokenType.KIND_KEYWORD),
  	new TokenType(60, "try", TokenType.KIND_KEYWORD),
  	new TokenType(61, "typedef", TokenType.KIND_KEYWORD),
  	new TokenType(62, "typeid", TokenType.KIND_KEYWORD),
  	new TokenType(63, "typename", TokenType.KIND_KEYWORD),
  	new TokenType(64, "union", TokenType.KIND_KEYWORD),
  	new TokenType(65, "unsigned", TokenType.KIND_KEYWORD),
  	new TokenType(66, "using", TokenType.KIND_KEYWORD),
  	new TokenType(67, "virtual", TokenType.KIND_KEYWORD),
  	new TokenType(68, "void", TokenType.KIND_KEYWORD),
  	new TokenType(69, "volatile", TokenType.KIND_KEYWORD),
  	new TokenType(70, "wchar_t", TokenType.KIND_KEYWORD),
  	new TokenType(71, "while", TokenType.KIND_KEYWORD),
  	new TokenType(72, "xor", TokenType.KIND_KEYWORD),
  	new TokenType(73, "xor_eq", TokenType.KIND_KEYWORD),
  	
  	
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
  * Defaultconstructor : erzeugt einen neuen CPlusPlusScanner
  */
  public CPlusPlusScanner () {
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
newLine=[\u2028\u2029\u000A\u000B\u000C\u000D\u0085] | \u000D\u000A | \n | \r\n
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
STRING_TEXT=(\".+\")

%%

<YYINITIAL> and 			 { return (new KeyWordToken(yytext(),  0, yyline));} 
<YYINITIAL> and_eq	 	     { return (new KeyWordToken(yytext(),  1, yyline));} 
<YYINITIAL> asm		   	  	 { return (new KeyWordToken(yytext(),  2, yyline));} 
<YYINITIAL> auto 			 { return (new KeyWordToken(yytext(),  3, yyline));} 
<YYINITIAL> bitand 			 { return (new KeyWordToken(yytext(),  4, yyline));} 
<YYINITIAL> bitor			 { return (new KeyWordToken(yytext(),  5, yyline));} 
<YYINITIAL> bool			 { return (new KeyWordToken(yytext(),  6, yyline));} 
<YYINITIAL> break  			 { return (new KeyWordToken(yytext(),  7, yyline));} 
<YYINITIAL> case 			 { return (new KeyWordToken(yytext(),  8, yyline));} 
<YYINITIAL> catch 			 { return (new KeyWordToken(yytext(),  9, yyline));} 
<YYINITIAL> char       	  	 { return (new KeyWordToken(yytext(), 10, yyline));} 
<YYINITIAL> class    		 { return (new KeyWordToken(yytext(), 11, yyline));} 
<YYINITIAL> compl 			 { return (new KeyWordToken(yytext(), 12, yyline));} 
<YYINITIAL> const			 { return (new KeyWordToken(yytext(), 13, yyline));} 
<YYINITIAL> const_cast  	 { return (new KeyWordToken(yytext(), 14, yyline));} 
<YYINITIAL> continue   		 { return (new KeyWordToken(yytext(), 15, yyline));} 
<YYINITIAL> default			 { return (new KeyWordToken(yytext(), 16, yyline));} 
<YYINITIAL> delete  		 { return (new KeyWordToken(yytext(), 17, yyline));} 
<YYINITIAL> do				 { return (new KeyWordToken(yytext(), 18, yyline));} 
<YYINITIAL> double			 { return (new KeyWordToken(yytext(), 19, yyline));} 
<YYINITIAL> dynamic_cast	 { return (new KeyWordToken(yytext(), 20, yyline));} 
<YYINITIAL> else			 { return (new KeyWordToken(yytext(), 21, yyline));} 
<YYINITIAL> enum			 { return (new KeyWordToken(yytext(), 22, yyline));} 
<YYINITIAL> explicit		 { return (new KeyWordToken(yytext(), 23, yyline));} 
<YYINITIAL> export			 { return (new KeyWordToken(yytext(), 24, yyline));} 
<YYINITIAL> extern			 { return (new KeyWordToken(yytext(), 25, yyline));} 
<YYINITIAL> false			 { return (new KeyWordToken(yytext(), 26, yyline));} 
<YYINITIAL> float			 { return (new KeyWordToken(yytext(), 27, yyline));} 
<YYINITIAL> for				 { return (new KeyWordToken(yytext(), 28, yyline));} 
<YYINITIAL> friend			 { return (new KeyWordToken(yytext(), 29, yyline));} 
<YYINITIAL> goto			 { return (new KeyWordToken(yytext(), 30, yyline));} 
<YYINITIAL> if				 { return (new KeyWordToken(yytext(), 31, yyline));} 
<YYINITIAL> inline  		 { return (new KeyWordToken(yytext(), 32, yyline));} 
<YYINITIAL> int 			 { return (new KeyWordToken(yytext(), 33, yyline));} 
<YYINITIAL> long			 { return (new KeyWordToken(yytext(), 34, yyline));} 
<YYINITIAL> mutable		     { return (new KeyWordToken(yytext(), 35, yyline));} 
<YYINITIAL> namespace   	 { return (new KeyWordToken(yytext(), 36, yyline));} 
<YYINITIAL> new      	     { return (new KeyWordToken(yytext(), 37, yyline));} 
<YYINITIAL> not         	 { return (new KeyWordToken(yytext(), 38, yyline));} 
<YYINITIAL> not_eq   	     { return (new KeyWordToken(yytext(), 39, yyline));} 
<YYINITIAL> operator    	 { return (new KeyWordToken(yytext(), 40, yyline));} 
<YYINITIAL> or      	     { return (new KeyWordToken(yytext(), 41, yyline));} 
<YYINITIAL> or_eq       	 { return (new KeyWordToken(yytext(), 42, yyline));} 
<YYINITIAL> private  	     { return (new KeyWordToken(yytext(), 43, yyline));} 
<YYINITIAL> protected    	 { return (new KeyWordToken(yytext(), 44, yyline));} 
<YYINITIAL> public      	 { return (new KeyWordToken(yytext(), 45, yyline));} 
<YYINITIAL> register     	 { return (new KeyWordToken(yytext(), 46, yyline));} 
<YYINITIAL> reinterpret_cast { return (new KeyWordToken(yytext(), 47, yyline));} 
<YYINITIAL> return     		 { return (new KeyWordToken(yytext(), 48, yyline));} 
<YYINITIAL> short      	  	 { return (new KeyWordToken(yytext(), 49, yyline));} 
<YYINITIAL> signed     	  	 { return (new KeyWordToken(yytext(), 50, yyline));} 
<YYINITIAL> sizeof     		 { return (new KeyWordToken(yytext(), 51, yyline));} 
<YYINITIAL> static     		 { return (new KeyWordToken(yytext(), 52, yyline));} 
<YYINITIAL> static_cast		 { return (new KeyWordToken(yytext(), 53, yyline));} 
<YYINITIAL> struct     		 { return (new KeyWordToken(yytext(), 54, yyline));} 
<YYINITIAL> switch     	 	 { return (new KeyWordToken(yytext(), 55, yyline));} 
<YYINITIAL> template   		 { return (new KeyWordToken(yytext(), 56, yyline));} 
<YYINITIAL> this       		 { return (new KeyWordToken(yytext(), 57, yyline));} 
<YYINITIAL> throw      	 	 { return (new KeyWordToken(yytext(), 58, yyline));} 
<YYINITIAL> true      	     { return (new KeyWordToken(yytext(), 59, yyline));} 
<YYINITIAL> try       		 { return (new KeyWordToken(yytext(), 60, yyline));} 
<YYINITIAL> typedef    	  	 { return (new KeyWordToken(yytext(), 61, yyline));} 
<YYINITIAL> typeid     	  	 { return (new KeyWordToken(yytext(), 62, yyline));} 
<YYINITIAL> typename  	   	 { return (new KeyWordToken(yytext(), 63, yyline));} 
<YYINITIAL> union       	 { return (new KeyWordToken(yytext(), 64, yyline));} 
<YYINITIAL> unsigned 	     { return (new KeyWordToken(yytext(), 65, yyline));} 
<YYINITIAL> using       	 { return (new KeyWordToken(yytext(), 66, yyline));} 
<YYINITIAL> virtual 	     { return (new KeyWordToken(yytext(), 67, yyline));} 
<YYINITIAL> void   	      	 { return (new KeyWordToken(yytext(), 68, yyline));} 
<YYINITIAL> volatile    	 { return (new KeyWordToken(yytext(), 69, yyline));} 
<YYINITIAL> wchar_t 	     { return (new KeyWordToken(yytext(), 70, yyline));} 
<YYINITIAL> while  	      	 { return (new KeyWordToken(yytext(), 71, yyline));} 
<YYINITIAL> xor         	 { return (new KeyWordToken(yytext(), 72, yyline));} 
<YYINITIAL> xor_eq  	     { return (new KeyWordToken(yytext(), 73, yyline));} 


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

<YYINITIAL> [0-9]+                      { return (new Token(yytext(), Token.TYPE_NUMBER, yyline)); }
<YYINITIAL> [a-zA-Z_][a-zA-Z1-9_]*	{ return (new Token(yytext(), Token.TYPE_IDENT, yyline)); }
<YYINITIAL> .                     	{ return (new Token(yytext(), Token.TYPE_STRING, yyline)); }
