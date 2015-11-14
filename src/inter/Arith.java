package inter;

import lexer.*;

/*
 * Arith ist eine Unterklasse von Op und beschreibt arithmetische Ausdrücke. 
 * In den Instanzenvariablen expr1 und expr2 werden die beiden Operanden 
 * abgelegt
 */

public class Arith extends Op {
	Expr expr1, expr2;

	public Arith(Token tok, Expr x1, Expr x2) {
		super(tok);
		expr1 = x1;
		expr2 = x2;
	}
	
	@Override
	public String toString() {
		return expr1.toString() +
			   super.toString() + 
			   expr2.toString();
	}
	
	public String toString(int k) {
		return super.toString(k) + " (" +op.toString()+")\n" +
			   expr1.toString(k+1) + "\n" +
			   expr2.toString(k+1);
			   
	}
}
