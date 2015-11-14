package inter;

import lexer.Token;

/*
 * Singleton ist eine abstrakte Unterklasse von Expr und beschreibt
 * elementare Ausdrücke wie Identifier oder Konstanten. 
 */

public abstract class Singleton extends Expr {

	Singleton(Token t) {
		super(t);
	}

	public String toString(int k) {
		return super.toString(k) + " (" + op.toString() + ")";
	}
}

