package inter;

import lexer.*;

/*
 * Logical ist eine Unterklasse von Expr und beschreibt logische Ausdrücke. 
 * In den Instanzenvariablen expr1 und expr2 werden die beiden Operanden 
 * abgelegt
 */

public abstract class Logical extends Expr {
	Expr expr1, expr2;

	Logical(Token tok, Expr x1, Expr x2) {
		super(tok);
		expr1 = x1;
		expr2 = x2;
	}
	
	@Override
	public String toString() {
		return (expr1.toString()+super.toString()+expr2.toString());
	}

	public String toString(int k) {
		String ret_op    = super.toString(k) + " ("+super.toString()+")\n";
		String ret_expr1 = expr1.toString(k+1)+"\n";
		String ret_expr2 = expr2.toString(k+1);
		return ret_op+ret_expr1+ret_expr2;
	}
}
