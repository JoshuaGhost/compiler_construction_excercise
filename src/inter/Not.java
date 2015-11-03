package inter;

import lexer.*;

/*
 * Not ist eine Unterklasse von Logical und beschreibt 
 * die logische nicht-Operation. Beide von Logical geerbten
 * Instanzenvariablen werden auf den gleichen Ausdruck gesetzt. 
 * Darum muss die toString-Methode überschrieben werden.
 */

public class Not extends Logical {

	public Not(Token tok, Expr x2) {
		super(tok, x2, x2);
	}

}
