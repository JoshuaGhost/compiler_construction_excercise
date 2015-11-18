package inter;

import lexer.*;
import treewalker.TreeWalker;

/*
 * Id ist eine Unterklasse von Singleton und beschreibt Identifier. 
 */

public class Id extends Singleton {

	public Id(Word id) {
		super(id);
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkIdNode(this, arg);
	}

}
