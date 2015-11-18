package inter;

import treewalker.TreeWalker;


public class Program extends Stmt {
	Block block;

	public Block getBlock() {
		return block;
	}

	public Program(Block b) {
		 block = b;
	}
	
	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkProgramNode(this, arg);
	}

}


	
