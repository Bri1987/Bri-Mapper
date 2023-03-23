// Generated from sqlLike.g4 by ANTLR 4.12.0

package com.bri.webfinal.language;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class sqlLikeLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, IDent=9, 
		ALLCOLS=10, LPAREN=11, RPAREN=12, GT=13, GE=14, LT=15, LE=16, EQ=17, NE=18, 
		WS=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "IDent", 
			"ALLCOLS", "LPAREN", "RPAREN", "GT", "GE", "LT", "LE", "EQ", "NE", "WS"
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


	public sqlLikeLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "sqlLike.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0013t\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0004\bT\b\b\u000b\b\f\bU\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0004\u0012o\b\u0012\u000b\u0012\f\u0012p\u0001\u0012\u0001"+
		"\u0012\u0000\u0000\u0013\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\u0001"+
		"\u0000\u0002\u0006\u0000--09AZ__az\u4e00\u8000\u9fa5\u0003\u0000\t\n\r"+
		"\r  u\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000"+
		"\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000"+
		"!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001"+
		"\u0000\u0000\u0000\u0001\'\u0001\u0000\u0000\u0000\u00033\u0001\u0000"+
		"\u0000\u0000\u0005:\u0001\u0000\u0000\u0000\u0007<\u0001\u0000\u0000\u0000"+
		"\t>\u0001\u0000\u0000\u0000\u000bE\u0001\u0000\u0000\u0000\rJ\u0001\u0000"+
		"\u0000\u0000\u000fP\u0001\u0000\u0000\u0000\u0011S\u0001\u0000\u0000\u0000"+
		"\u0013W\u0001\u0000\u0000\u0000\u0015Y\u0001\u0000\u0000\u0000\u0017\\"+
		"\u0001\u0000\u0000\u0000\u0019^\u0001\u0000\u0000\u0000\u001b`\u0001\u0000"+
		"\u0000\u0000\u001dc\u0001\u0000\u0000\u0000\u001fe\u0001\u0000\u0000\u0000"+
		"!h\u0001\u0000\u0000\u0000#j\u0001\u0000\u0000\u0000%n\u0001\u0000\u0000"+
		"\u0000\'(\u0005i\u0000\u0000()\u0005n\u0000\u0000)*\u0005s\u0000\u0000"+
		"*+\u0005e\u0000\u0000+,\u0005r\u0000\u0000,-\u0005t\u0000\u0000-.\u0005"+
		" \u0000\u0000./\u0005i\u0000\u0000/0\u0005n\u0000\u000001\u0005t\u0000"+
		"\u000012\u0005o\u0000\u00002\u0002\u0001\u0000\u0000\u000034\u0005v\u0000"+
		"\u000045\u0005a\u0000\u000056\u0005l\u0000\u000067\u0005u\u0000\u0000"+
		"78\u0005e\u0000\u000089\u0005s\u0000\u00009\u0004\u0001\u0000\u0000\u0000"+
		":;\u0005,\u0000\u0000;\u0006\u0001\u0000\u0000\u0000<=\u0005;\u0000\u0000"+
		"=\b\u0001\u0000\u0000\u0000>?\u0005s\u0000\u0000?@\u0005e\u0000\u0000"+
		"@A\u0005l\u0000\u0000AB\u0005e\u0000\u0000BC\u0005c\u0000\u0000CD\u0005"+
		"t\u0000\u0000D\n\u0001\u0000\u0000\u0000EF\u0005f\u0000\u0000FG\u0005"+
		"r\u0000\u0000GH\u0005o\u0000\u0000HI\u0005m\u0000\u0000I\f\u0001\u0000"+
		"\u0000\u0000JK\u0005w\u0000\u0000KL\u0005h\u0000\u0000LM\u0005e\u0000"+
		"\u0000MN\u0005r\u0000\u0000NO\u0005e\u0000\u0000O\u000e\u0001\u0000\u0000"+
		"\u0000PQ\u0005\'\u0000\u0000Q\u0010\u0001\u0000\u0000\u0000RT\u0007\u0000"+
		"\u0000\u0000SR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000US\u0001"+
		"\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000V\u0012\u0001\u0000\u0000"+
		"\u0000WX\u0005*\u0000\u0000X\u0014\u0001\u0000\u0000\u0000YZ\u0005(\u0000"+
		"\u0000Z[\u0005 \u0000\u0000[\u0016\u0001\u0000\u0000\u0000\\]\u0005)\u0000"+
		"\u0000]\u0018\u0001\u0000\u0000\u0000^_\u0005>\u0000\u0000_\u001a\u0001"+
		"\u0000\u0000\u0000`a\u0005>\u0000\u0000ab\u0005=\u0000\u0000b\u001c\u0001"+
		"\u0000\u0000\u0000cd\u0005<\u0000\u0000d\u001e\u0001\u0000\u0000\u0000"+
		"ef\u0005<\u0000\u0000fg\u0005=\u0000\u0000g \u0001\u0000\u0000\u0000h"+
		"i\u0005=\u0000\u0000i\"\u0001\u0000\u0000\u0000jk\u0005!\u0000\u0000k"+
		"l\u0005=\u0000\u0000l$\u0001\u0000\u0000\u0000mo\u0007\u0001\u0000\u0000"+
		"nm\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000"+
		"\u0000pq\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000rs\u0006\u0012"+
		"\u0000\u0000s&\u0001\u0000\u0000\u0000\u0003\u0000Up\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}