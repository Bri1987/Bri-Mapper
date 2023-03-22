// Generated from sqlLike.g4 by ANTLR 4.12.0
package com.bri.webfinal.language;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link sqlLikeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface sqlLikeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code insert}
	 * labeled alternative in {@link sqlLikeParser#addData}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert(sqlLikeParser.InsertContext ctx);
	/**
	 * Visit a parse tree produced by the {@code meta_line}
	 * labeled alternative in {@link sqlLikeParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeta_line(sqlLikeParser.Meta_lineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code line_content}
	 * labeled alternative in {@link sqlLikeParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine_content(sqlLikeParser.Line_contentContext ctx);
}