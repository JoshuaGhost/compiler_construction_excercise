package lexer;

/*
 * Die Klasse beschreibt Eigenschaften von reservierten Wörtern.
 * Jedes reservierte Wort stellt ein Token dar. Zusätzlich zur Tokenklasse, 
 * (dargestellt durch tag) hat so ein Token auch einen Tokenwert, der 
 * das zugehörige Lexeme ist. In Form von Klassenvariablen werden diese 
 * Token zur Verfügung gestellt. 
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
