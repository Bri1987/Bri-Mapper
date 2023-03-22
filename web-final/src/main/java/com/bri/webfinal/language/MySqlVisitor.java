package com.bri.webfinal.language;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class MySqlVisitor extends sqlLikeBaseVisitor<Object>{

    public static String table= "";
    public static List<String> list_meta=new ArrayList<>();
    public static List<List<String>> line_content=new ArrayList<>();     //TODO 静态的line_content，一次启动只能跑一次

    @Override
    public Object visitInsert(sqlLikeParser.InsertContext ctx) {
        table=ctx.IDent().getText();
        visit(ctx.line());
        for(sqlLikeParser.ContentContext c: ctx.content())
        {
            visit(c);
        }
        return null;
    }

    @Override
    public Object visitMeta_line(sqlLikeParser.Meta_lineContext ctx) {
        List<TerminalNode> list = ctx.IDent();
        for(TerminalNode id:list)
        {
            list_meta.add(id.getText());
        }
        return null;
    }

    @Override
    public Object visitLine_content(sqlLikeParser.Line_contentContext ctx) {
        List<TerminalNode> list_ident=ctx.IDent();
        List<String> one_list=new ArrayList<>();
        for(TerminalNode id:list_ident)
        {
            one_list.add(id.getText());
        }
        line_content.add(one_list);
        return null;
    }
}