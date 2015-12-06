package treewalker;
import inter.Access;
import inter.And;
import inter.Arith;
import inter.AssignElem;
import inter.AssignId;
import inter.AssignStmt;
import inter.Block;
import inter.Break;
import inter.Constant;
import inter.Do;
import inter.Else;
import inter.EmptyStmt;
import inter.For;
import inter.Id;
import inter.If;
import inter.Not;
import inter.Or;
import inter.Program;
import inter.Rel;
import inter.Seq;
import inter.Unary;
import inter.While;

/*
 * Dies ist eine Unterklasse der Klasse TreeWalker, die den Syntaxbaum 
 * durchläuft und die Zahl der Knoten zählt.
 * 
 * Jede Methode gibt die Zahl der Knoten des jeweiligen Teilbaums zurück.
 */

public class CountNodesWalker extends TreeWalker<Integer, Void> {

	@Override
	public Integer walkAccessNode(Access node, Void arg) {
		int a = walk(node.getArray(), null);
		int i = walk(node.getIndex(), null);
		return  a+i+1;
	}

	@Override
	public Integer walkArithNode(Arith node, Void arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkUnaryNode(Unary node, Void arg) {
		int ex = walk(node.getExpr(), null);
		return  ex+1;
	}

	@Override
	public Integer walkAndNode(And node, Void arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;

	}

	@Override
	public Integer walkOrNode(Or node, Void arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkRelNode(Rel node, Void arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkNotNode(Not node, Void arg) {
		int ex = walk(node.getExpr1(), null);
		return  ex+1;
	}

	@Override
	public Integer walkIdNode(Id node, Void arg) {
		return 1;
	}

	@Override
	public Integer walkConstantNode(Constant node, Void arg) {
		return 1;
	}

	@Override
	public Integer walkAssignIdNode(AssignId node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int id = walk(node.getIdent(), null);
		return  ex+id+1;
	}

	@Override
	public Integer walkAssignElemNode(AssignElem node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int acc = walk(node.getAcc(), null);
		return  ex+acc+1;
	}
	
	@Override
	public Integer walkAssignStmtNode(AssignStmt node, Void arg) {
		return walk(node.getAssign(), null) + 1;
	}

	@Override
	public Integer walkEmptyStmtNode(EmptyStmt node, Void arg) {
		return 1;
	}

	@Override
	public Integer walkWhileNode(While node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}

	@Override
	public Integer walkDoNode(Do node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}

	@Override
	public Integer walkProgramNode(Program node, Void arg) {
		return walk(node.getBlock(), null)+1;
	}

	@Override
	public Integer walkBlockNode(Block node, Void arg) {
		return walk(node.getStmts(), null)+1;
	}

	@Override
	public Integer walkIfNode(If node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}


	@Override
	public Integer walkForNode(For node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int as1 = walk(node.getInit_ass(), null);
		int as2 = walk(node.getIter_ass(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+as1 + as2 +1;
	}

	@Override
	public Integer walkSeqNode(Seq node, Void arg) {
		int st1 = walk(node.getStmt1(), null);
		int st2 = walk(node.getStmt2(), null);
		return st1+st2+1;
	}

	@Override
	public Integer walkElseNode(Else node, Void arg) {
		int ex = walk(node.getExpr(), null);
		int st1 = walk(node.getStmt1(), null);
		int st2 = walk(node.getStmt2(), null);	
		return  ex+st1+st2+1;
	}

	@Override
	public Integer walkBreakNode(Break node, Void arg) {
		return 1;
	}

}
