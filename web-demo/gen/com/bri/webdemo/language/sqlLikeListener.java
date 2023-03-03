// Generated from java-escape by ANTLR 4.11.1
package com.bri.webdemo.language;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link sqlLikeParser}.
 */
public interface sqlLikeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code insert}
	 * labeled alternative in {@link sqlLikeParser#addData}.
	 * @param ctx the parse tree
	 */
	void enterInsert(sqlLikeParser.InsertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insert}
	 * labeled alternative in {@link sqlLikeParser#addData}.
	 * @param ctx the parse tree
	 */
	void exitInsert(sqlLikeParser.InsertContext ctx);
	/**
	 * Enter a parse tree produced by the {@code meta_line}
	 * labeled alternative in {@link sqlLikeParser#line}.
	 * @param ctx the parse tree
	 */
	void enterMeta_line(sqlLikeParser.Meta_lineContext ctx);
	/**
	 * Exit a parse tree produced by the {@code meta_line}
	 * labeled alternative in {@link sqlLikeParser#line}.
	 * @param ctx the parse tree
	 */
	void exitMeta_line(sqlLikeParser.Meta_lineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code line_content}
	 * labeled alternative in {@link sqlLikeParser#content}.
	 * @param ctx the parse tree
	 */
	void enterLine_content(sqlLikeParser.Line_contentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code line_content}
	 * labeled alternative in {@link sqlLikeParser#content}.
	 * @param ctx the parse tree
	 */
	void exitLine_content(sqlLikeParser.Line_contentContext ctx);
}