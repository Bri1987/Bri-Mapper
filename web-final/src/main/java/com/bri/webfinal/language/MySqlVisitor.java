package com.bri.webfinal.language;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class MySqlVisitor extends sqlLikeBaseVisitor<Object>{

    public static String table= "";
    public static List<String> list_meta=new ArrayList<>();
    public static List<List<String>> line_content=new ArrayList<>();     //TODO 静态的line_content，一次启动只能跑一次

    //select
    public static String comparison="";
    public static String select_content="";
    public static String line_name="";
    public static String line_content_select="";
    public static String select_table="";

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
    public Object visitSelectData(sqlLikeParser.SelectDataContext ctx) {
        boolean flag_allcols=false;
        if(ctx.ALLCOLS().getText().equals("*"))
        {
            select_content=ctx.ALLCOLS().getText();
        }
        else
        {
            flag_allcols=true;
            select_content=ctx.IDent(0).getText();
        }
        if(flag_allcols)
        {
            select_table=ctx.IDent(1).getText();
            line_name=ctx.IDent(2).getText();
            line_content_select=ctx.IDent(3).getText();
        }
        else
        {
            select_table=ctx.IDent(0).getText();
            line_name=ctx.IDent(1).getText();
            line_content_select=ctx.IDent(2).getText();
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

    @Override
    public Object visitComparison(sqlLikeParser.ComparisonContext ctx) {
        comparison=ctx.getText();
        return null;
    }
}