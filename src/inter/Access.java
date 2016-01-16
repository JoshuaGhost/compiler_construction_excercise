package inter;

import lexer.*;
import symbols.Type;
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
	
	public void setArray(Expr array) {
		this.array = array;
	}

	public Expr getIndex() {
		return index;
	}

	public void setIndex(Expr index) {
		this.index = index;
	}

	public Access(Expr a, Expr i) {
		this(a, i, null);
	}
	
	public Access(Expr a, Expr i, Type p) {
		super(new Word("[]", Tag.INDEX));
		array = a;
		index = i;
		type = p;
	}
	
	public String toString() {
		return array.toString() + " [ " + index.toString() + " ] ";
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkAccessNode(this, arg);
	}
	
}
