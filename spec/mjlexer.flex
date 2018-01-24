package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
    private Symbol create(int type) {
        return new Symbol(type, yyline + 1, yycolumn);
    }

    private Symbol create(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn, value);
    }
%}

%cup

%xstate COMMENT

%eofval{
    return create(sym.EOF);
%eofval}

%line
%column

%%

" " {}
"\b" {}
"\t"  {}
"\n" {}
"\r\n" {}
"\f" {}
"program" { return create(sym.PROGRAM, yytext()); }
"void" { return create(sym.VOID, yytext()); }
"break" { return create(sym.BREAK, yytext()); }
"class" { return create(sym.CLASS, yytext()); }
"if" { return create(sym.IF, yytext()); }
"else" { return create(sym.ELSE, yytext()); }
"new" { return create(sym.NEW, yytext()); }
"return" { return create(sym.RETURN, yytext()); }
"do" { return create(sym.DO, yytext()); }
"while" { return create(sym.WHILE, yytext()); }
"extends" { return create(sym.EXTENDS, yytext()); }
"continue" { return create(sym.CONTINUE, yytext()); }
"print" { return create(sym.PRINT, yytext()); }
"read" { return create(sym.READ, yytext()); }
"const" { return create(sym.CONST, yytext()); }
"+" { return create(sym.PLUS, yytext()); }
"-" { return create(sym.MINUS, yytext()); }
"*" { return create(sym.MUL, yytext()); }
"/" { return create(sym.DIV, yytext()); }
"%" { return create(sym.MOD, yytext()); }
"==" { return create(sym.EQ, yytext()); }
"!=" { return create(sym.NEQ, yytext()); }
">" { return create(sym.GRT, yytext()); }
">=" { return create(sym.GRTEQ, yytext()); }
"<" { return create(sym.LSS, yytext()); }
"<=" { return create(sym.LEQ, yytext()); }
"&&" { return create(sym.AND, yytext()); }
"||" { return create(sym.OR, yytext()); }
"=" { return create(sym.ASSIGN, yytext()); }
"++" { return create(sym.INC, yytext()); }
"--" { return create(sym.DEC, yytext()); }
";" { return create(sym.SEMI, yytext()); }
"," { return create(sym.COMMA, yytext()); }
"." { return create(sym.DOT, yytext()); }
"(" { return create(sym.LPAREN, yytext()); }
")" { return create(sym.RPAREN, yytext()); }
"{" { return create(sym.LBRACE, yytext()); }
"}" { return create(sym.RBRACE, yytext()); }
"[" { return create(sym.LSQUARE, yytext()); }
"]" { return create(sym.RSQUARE, yytext()); }
"//" { yybegin(COMMENT); }

<COMMENT>"\n" { yybegin(YYINITIAL); }
<COMMENT>. { yybegin(COMMENT); }
<COMMENT>"\r\n" { yybegin(YYINITIAL); }

[0-9]+ { return create(sym.NUMBER, new Integer(yytext())); }
"true"|"false" { return create(sym.BOOL, new Boolean(yytext().equals("true"))); }
([a-zA-Z])[a-zA-Z0-9_]* { return create(sym.IDENT, yytext()); }
"'"[ -~]"'" { return create(sym.CHAR, new Character(yytext().charAt(1))); }
. { System.err.println("Lexical error on (" + yytext() + ") line " + yyline + 1 + " col " + yycolumn + 1); }
