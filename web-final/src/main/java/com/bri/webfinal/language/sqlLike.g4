grammar sqlLike;

addData : 'insert into' IDent line  'values' content (',' content)* ';'          #insert
        ;

selectData : 'select' (ALLCOLS|IDent) 'from' IDent ('where' IDent comparison IDent)?
           ;

line :       LPAREN IDent (',' IDent)* RPAREN        #meta_line
     ;
content :  LPAREN '\'' (IDent) '\'' (',' '\'' (IDent) '\'')* RPAREN        #line_content
        ;

comparison : GT | GE | LT | LE | EQ | NE            #comparison
           ;

//语法分析器的规则必须以小写字母开头，词法分析器的规则必须用大写字母开头
IDent :      [\u4e00-\u9fa5_a-zA-Z0-9-]+ ;
ALLCOLS : '*';
LPAREN : '( ';
RPAREN : ')' ;
GT  :   '>';
GE  :   '>=';
LT  :   '<';
LE  :   '<=';
EQ  :   '=';
NE  :   '!=';
WS  :	[ \t\r\n]+ -> skip ;	//定义语法规则“空白符号”，skip


