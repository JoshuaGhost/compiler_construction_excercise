package lexer;

/* 
 * Die Klasse Token beschreibt allgemeine Eigenschaften eines Tokens.
 * Jedes Token hat ein tag (eine integer-Zahl), das die Tokenklasse
 * codiert. Für viele Zeichen wird als tag einfach die Ascii-Codierung verwendet
 * Daher haben spezielle Token tags mit Werten >= 256 (siehe Klasse Tag)
 * @author rp
 */

public class Token {
	public final int tag;

	public Token(int t) {
		tag = t;
	}

}
