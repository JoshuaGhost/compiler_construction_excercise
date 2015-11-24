package inter;

import lexer.Token;
import symbols.*;

	/*
	 * Singleton ist eine abstrakte Unterklasse von Expr und beschreibt
	 * elementare Ausdrücke wie Identifier oder Konstanten. 
	 */

public abstract class Singleton extends Expr {

		Singleton(Token t, Type p) {
			super(t, p);
		}
		
	}
	
