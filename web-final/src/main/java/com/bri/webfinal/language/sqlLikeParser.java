// Generated from sqlLike.g4 by ANTLR 4.12.0
package com.bri.webfinal.language;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class sqlLikeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, IDent=9, 
		ALLCOLS=10, LPAREN=11, RPAREN=12, GT=13, GE=14, LT=15, LE=16, EQ=17, NE=18, 
		WS=19;
	public static final int
		RULE_addData = 0, RULE_selectData = 1, RULE_line = 2, RULE_content = 3, 
		RULE_comparison = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"addData", "selectData", "line", "content", "comparison"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'insert into'", "'values'", "','", "';'", "'select'", "'from'", 
			"'where'", "'''", null, "'*'", "'( '", "')'", "'>'", "'>='", "'<'", "'<='", 
			"'='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "IDent", "ALLCOLS", 
			"LPAREN", "RPAREN", "GT", "GE", "LT", "LE", "EQ", "NE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "sqlLike.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public sqlLikeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AddDataContext extends ParserRuleContext {
		public AddDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addData; }
	 
		public AddDataContext() { }
		public void copyFrom(AddDataContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InsertContext extends AddDataContext {
		public TerminalNode IDent() { return getToken(sqlLikeParser.IDent, 0); }
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public List<ContentContext> content() {
			return getRuleContexts(ContentContext.class);
		}
		public ContentContext content(int i) {
			return getRuleContext(ContentContext.class,i);
		}
		public InsertContext(AddDataContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof sqlLikeVisitor ) return ((sqlLikeVisitor<? extends T>)visitor).visitInsert(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddDataContext addData() throws RecognitionException {
		AddDataContext _localctx = new AddDataContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_addData);
		int _la;
		try {
			_localctx = new InsertContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			match(T__0);
			setState(11);
			match(IDent);
			setState(12);
			line();
			setState(13);
			match(T__1);
			setState(14);
			content();
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(15);
				match(T__2);
				setState(16);
				content();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectDataContext extends ParserRuleContext {
		public List<TerminalNode> IDent() { return getTokens(sqlLikeParser.IDent); }
		public TerminalNode IDent(int i) {
			return getToken(sqlLikeParser.IDent, i);
		}
		public TerminalNode ALLCOLS() { return getToken(sqlLikeParser.ALLCOLS, 0); }
		public ComparisonContext comparison() {
			return getRuleContext(ComparisonContext.class,0);
		}
		public SelectDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectData; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof sqlLikeVisitor ) return ((sqlLikeVisitor<? extends T>)visitor).visitSelectData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectDataContext selectData() throws RecognitionException {
		SelectDataContext _localctx = new SelectDataContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selectData);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(T__4);
			setState(25);
			_la = _input.LA(1);
			if ( !(_la==IDent || _la==ALLCOLS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(26);
			match(T__5);
			setState(27);
			match(IDent);
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(28);
				match(T__6);
				setState(29);
				match(IDent);
				setState(30);
				comparison();
				setState(31);
				match(IDent);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LineContext extends ParserRuleContext {
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
	 
		public LineContext() { }
		public void copyFrom(LineContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Meta_lineContext extends LineContext {
		public TerminalNode LPAREN() { return getToken(sqlLikeParser.LPAREN, 0); }
		public List<TerminalNode> IDent() { return getTokens(sqlLikeParser.IDent); }
		public TerminalNode IDent(int i) {
			return getToken(sqlLikeParser.IDent, i);
		}
		public TerminalNode RPAREN() { return getToken(sqlLikeParser.RPAREN, 0); }
		public Meta_lineContext(LineContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof sqlLikeVisitor ) return ((sqlLikeVisitor<? extends T>)visitor).visitMeta_line(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_line);
		int _la;
		try {
			_localctx = new Meta_lineContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			match(LPAREN);
			setState(36);
			match(IDent);
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(37);
				match(T__2);
				setState(38);
				match(IDent);
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContentContext extends ParserRuleContext {
		public ContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_content; }
	 
		public ContentContext() { }
		public void copyFrom(ContentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Line_contentContext extends ContentContext {
		public TerminalNode LPAREN() { return getToken(sqlLikeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(sqlLikeParser.RPAREN, 0); }
		public List<TerminalNode> IDent() { return getTokens(sqlLikeParser.IDent); }
		public TerminalNode IDent(int i) {
			return getToken(sqlLikeParser.IDent, i);
		}
		public Line_contentContext(ContentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof sqlLikeVisitor ) return ((sqlLikeVisitor<? extends T>)visitor).visitLine_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContentContext content() throws RecognitionException {
		ContentContext _localctx = new ContentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_content);
		int _la;
		try {
			_localctx = new Line_contentContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(LPAREN);
			setState(47);
			match(T__7);
			{
			setState(48);
			match(IDent);
			}
			setState(49);
			match(T__7);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(50);
				match(T__2);
				setState(51);
				match(T__7);
				{
				setState(52);
				match(IDent);
				}
				setState(53);
				match(T__7);
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(sqlLikeParser.GT, 0); }
		public TerminalNode GE() { return getToken(sqlLikeParser.GE, 0); }
		public TerminalNode LT() { return getToken(sqlLikeParser.LT, 0); }
		public TerminalNode LE() { return getToken(sqlLikeParser.LE, 0); }
		public TerminalNode EQ() { return getToken(sqlLikeParser.EQ, 0); }
		public TerminalNode NE() { return getToken(sqlLikeParser.NE, 0); }
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof sqlLikeVisitor ) return ((sqlLikeVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_comparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 516096L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0013@\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0005\u0000\u0012\b\u0000\n\u0000\f\u0000\u0015\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\"\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002(\b\u0002"+
		"\n\u0002\f\u0002+\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u00037\b\u0003\n\u0003\f\u0003:\t\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0000\u0000\u0005\u0000\u0002\u0004"+
		"\u0006\b\u0000\u0002\u0001\u0000\t\n\u0001\u0000\r\u0012>\u0000\n\u0001"+
		"\u0000\u0000\u0000\u0002\u0018\u0001\u0000\u0000\u0000\u0004#\u0001\u0000"+
		"\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b=\u0001\u0000\u0000\u0000"+
		"\n\u000b\u0005\u0001\u0000\u0000\u000b\f\u0005\t\u0000\u0000\f\r\u0003"+
		"\u0004\u0002\u0000\r\u000e\u0005\u0002\u0000\u0000\u000e\u0013\u0003\u0006"+
		"\u0003\u0000\u000f\u0010\u0005\u0003\u0000\u0000\u0010\u0012\u0003\u0006"+
		"\u0003\u0000\u0011\u000f\u0001\u0000\u0000\u0000\u0012\u0015\u0001\u0000"+
		"\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000"+
		"\u0000\u0000\u0014\u0016\u0001\u0000\u0000\u0000\u0015\u0013\u0001\u0000"+
		"\u0000\u0000\u0016\u0017\u0005\u0004\u0000\u0000\u0017\u0001\u0001\u0000"+
		"\u0000\u0000\u0018\u0019\u0005\u0005\u0000\u0000\u0019\u001a\u0007\u0000"+
		"\u0000\u0000\u001a\u001b\u0005\u0006\u0000\u0000\u001b!\u0005\t\u0000"+
		"\u0000\u001c\u001d\u0005\u0007\u0000\u0000\u001d\u001e\u0005\t\u0000\u0000"+
		"\u001e\u001f\u0003\b\u0004\u0000\u001f \u0005\t\u0000\u0000 \"\u0001\u0000"+
		"\u0000\u0000!\u001c\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000"+
		"\"\u0003\u0001\u0000\u0000\u0000#$\u0005\u000b\u0000\u0000$)\u0005\t\u0000"+
		"\u0000%&\u0005\u0003\u0000\u0000&(\u0005\t\u0000\u0000\'%\u0001\u0000"+
		"\u0000\u0000(+\u0001\u0000\u0000\u0000)\'\u0001\u0000\u0000\u0000)*\u0001"+
		"\u0000\u0000\u0000*,\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000"+
		",-\u0005\f\u0000\u0000-\u0005\u0001\u0000\u0000\u0000./\u0005\u000b\u0000"+
		"\u0000/0\u0005\b\u0000\u000001\u0005\t\u0000\u000018\u0005\b\u0000\u0000"+
		"23\u0005\u0003\u0000\u000034\u0005\b\u0000\u000045\u0005\t\u0000\u0000"+
		"57\u0005\b\u0000\u000062\u0001\u0000\u0000\u00007:\u0001\u0000\u0000\u0000"+
		"86\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009;\u0001\u0000\u0000"+
		"\u0000:8\u0001\u0000\u0000\u0000;<\u0005\f\u0000\u0000<\u0007\u0001\u0000"+
		"\u0000\u0000=>\u0007\u0001\u0000\u0000>\t\u0001\u0000\u0000\u0000\u0004"+
		"\u0013!)8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}