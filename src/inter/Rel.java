package inter;

import lexer.*;

/*
 * Rel ist eine Unterklasse von Logical und beschreibt 
 * relationale Ausdrücke
 */

public class Rel extends Logical {

	public Rel(Token tok, Expr x1, Expr x2) {
		super(tok, x1, x2);
	}

}
