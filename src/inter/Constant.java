package inter;

import lexer.*;
import symbols.*;
import treewalker.TreeWalker;

/*
 * Constant ist eine Unterklasse von Singleton und beschreibt Konstante. 
 * Die beiden Konstanten True und False sind hier als Klassenvariablen
 * definiert.
 */

public class Constant extends Singleton {

	public Constant(Token tok, Type p) {
		super(tok, p);
	}

	public Constant(int i, Type p) {
		super(new Num(i),p);
	}
	public static final Constant True = new Constant(Word.True, Type.Bool),
			False = new Constant(Word.False, Type.Bool);

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkConstantNode(this, arg);
	}

}
