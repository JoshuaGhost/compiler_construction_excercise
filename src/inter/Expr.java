package inter;

import lexer.*;


/*
 * Expr ist eine Unterklasse von Node und beschreibt Ausdrücke. 
 * In der Instanzenvariable op wird die jeweilige Operation 
 * oder der jeweilige Operand abgelegt
 */

public abstract class Expr extends Node {
	Token op;

	public Token getOp() {
		return op;
	}

	Expr(Token tok) {
		op = tok;
	}
	
}
