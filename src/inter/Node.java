package inter;

import lexer.*;
import treewalker.TreeWalker;
/*
 *  Die Klasse beschreibt Eigenschaften von Knoten im Syntaxbaum.
 *  Jeder Knoten hat eine Instanzenvariable lexline, die auf die
 *  aktuelle Zeilennnummer der Eingabe gesetzt wird. 
 */

public abstract class Node {
	int lexline = 0;

	Node() {
		lexline = Lexer.line;
	}

	void error(String s) {
		throw new Error("near line " + lexline + ": " + s);
	}
	
	public abstract <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg); 

}
