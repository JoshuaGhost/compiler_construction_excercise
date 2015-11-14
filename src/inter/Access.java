package inter;

import lexer.*;

/*
 * Access ist eine Unterklasse von Op und beschreibt einen Array-Zugriff. 
 * In den Instanzenvariablen array und index werden die beiden Operanden 
 * abgelegt. Die Instanzenvariablen enthalten den Ausdruck für das Array
 * und für den Index.
 */

public class Access extends Op {
	Expr array;
	Expr index;

	public Access(Expr a, Expr i) {
		super(new Word("[]", Tag.INDEX));
		array = a;
		index = i;
	}
	
	@Override
	public String toString() {
		return array.toString()+"["+index.toString()+"]";
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +
			   array.toString(k+1) + "\n" +
			   index.toString(k+1);
	}

}
