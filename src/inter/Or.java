package inter;

import lexer.*;

/*
 * Or ist eine Unterklasse von Logical und beschreibt 
 * die logische oder-Operation
 */

public class Or extends Logical {

	public Or(Token tok, Expr x1, Expr x2) {
		super(tok, x1, x2);
	}

}
