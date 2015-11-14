package inter;

import lexer.*;

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

	public String toString(int k) {
		StringBuffer pads = new StringBuffer();
		for (int i = 0; i < k; i++){
			pads.append("| ");
		}
		String[] parts = this.getClass().toString().split("\\.");
		return pads.toString()+parts[1];
	}
}
