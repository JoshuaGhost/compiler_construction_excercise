package inter;

import lexer.*;
import treewalker.TreeWalker;

/*
 * Access ist eine Unterklasse von Op und beschreibt einen Array-Zugriff. 
 * In den Instanzenvariablen array und index werden die beiden Operanden 
 * abgelegt. Die Instanzenvariablen enthalten den Ausdruck für das Array
 * und für den Index.
 */

public class Access extends Op {
	Expr array;
	Expr index;

	public Expr getArray() {
		return array;
	}

	public Expr getIndex() {
		return index;
	}

	public Access(Expr a, Expr i) {
		super(new Word("[]", Tag.INDEX));
		array = a;
		index = i;
	}
	
	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkAccessNode(this, arg);
	}
	
}
