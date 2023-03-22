grammar sqlLike;

addData : 'insert into' IDent line  'values' content (',' content)* ';'          #insert
        ;
line :       '(' IDent (',' IDent)* ')'        #meta_line
     ;
content :  '(' '\'' (IDent) '\'' (',' '\'' (IDent) '\'')* ')'        #line_content
        ;

//语法分析器的规则必须以小写字母开头，词法分析器的规则必须用大写字母开头
IDent :      [\u4e00-\u9fa5_a-zA-Z0-9-]+ ;
WS  :	[ \t\r\n]+ -> skip ;	//定义语法规则“空白符号”，skip


