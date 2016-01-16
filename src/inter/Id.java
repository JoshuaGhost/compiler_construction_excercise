package inter;

import lexer.*;
import symbols.Type;
import treewalker.TreeWalker;

/*
 * Id ist eine Unterklasse von Singleton und beschreibt Identifier. 
 */

public class Id extends Singleton {
	int offset;					// offset gibt die relative Speicheradresse des Identifiers an

	public int getOffset() {
		return offset;
	}

	public Id(Word id, Type p, int b) {
		super(id, p);
		offset = b;
	}

	public String toString() {
		return op.toString();
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkIdNode(this, arg);
	}

}
