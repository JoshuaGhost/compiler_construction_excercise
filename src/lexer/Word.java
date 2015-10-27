package lexer;

/*
 * Die Klasse Word beschreibt Eigenschaften von Identifier, reservierten Wörtern
 * und weiteren speziellen Tokenklassen.
 *
 * Zusätzlich zur Tokenklasse (codiert durch tag)
 * hat so ein Token auch einen Tokenwert, der 
 * das zugehörige Lexeme ist. 
 * In Form von Klassenvariablen werden einige dieser Token
 * zut Verfügung gestellt.
 * 
 * @author rp
 *
 */

public class Word extends Token {
	public String lexeme = "";

	public Word(String s, int tag) {
		super(tag);
		lexeme = s;
	}

	public static final Word
		and = new Word("&&", Tag.AND),
		or = new Word("||", Tag.OR),
		eq = new Word("==", Tag.EQ),
		ne = new Word("!=", Tag.NE),
		le = new Word("<=", Tag.LE),
		ge = new Word(">=", Tag.GE),
		minus = new Word("minus", Tag.MINUS),
		True = new Word("true", Tag.TRUE),
		False = new Word("false", Tag.FALSE);

}
