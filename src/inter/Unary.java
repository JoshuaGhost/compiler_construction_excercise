package inter;

import lexer.*;

/*
 * Unary ist eine Unterklasse von Op und beschreibt unäre arithmetische Ausdrücke. 
 * In der Instanzenvariablen expr ist der Operand abgelegt
 */

public class Unary extends Op {
	Expr expr;

	public Unary(Token tok, Expr x) { // behandelt unäres minus, für ! siehe Not
		super(tok);
		expr = x;
	}

}
