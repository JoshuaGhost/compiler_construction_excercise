package inter;

import lexer.*;
import symbols.*;


/*
 * Expr ist eine Unterklasse von Node und beschreibt Ausdrücke. 
 * In der Instanzenvariable op wird die jeweilige Operation 
 * oder der jeweilige Operand abgelegt
 */

public abstract class Expr extends Node {
	Token op;
	Type type;

	public Token getOp() {
		return op;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	Expr(Token tok, Type p) {
		op = tok;
		type = p;
	}
	
}
