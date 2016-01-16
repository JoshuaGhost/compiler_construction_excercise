package inter;

import lexer.Word;
import symbols.Type;
import treewalker.TreeWalker;

public class Temp extends Id {
	static int count = 0;

	int number; // temporäre Variable haben die lexikale Darstellung
				// "t <number>"

	public Temp(Type p) {
		super(Word.temp, p, 0);
		number = ++count;
	}

	public String toString() {
		return "t" + number;
	}

	@Override
	public <ReturnType, ArgumentType> ReturnType walk(
			TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkTempNode(this, arg);
	}

}
