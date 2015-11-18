package inter;

import lexer.*;

/*
 * Expr ist eine abstrakte Unterklasse von Node und beschreibt Ausdrücke. 
 * In der Instanzenvariable op wird die jeweilige Operation 
 * oder der jeweilige Operand abgelegt
 */

public abstract class Expr extends Node {
	Token op;

	Expr(Token tok) {
		op = tok;
	}
	
}
