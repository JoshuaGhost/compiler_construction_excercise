package inter;

import lexer.*;

/*
 * Constant ist eine Unterklasse von Singleton und beschreibt Konstante. 
 * Die beiden Konstanten True und False sind hier als Klassenvariablen
 * definiert.
 */

public class Constant extends Singleton {

	public Constant(Token tok) {
		super(tok);
	}

	public Constant(int i) {
		super(new Num(i));
	}

	public static final Constant True = new Constant(Word.True),
								False = new Constant(Word.False);

}
