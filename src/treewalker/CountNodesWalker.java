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

public class CountNodesWalker extends TreeWalker<Integer, Object> {

	@Override
	public Integer walkAccessNode(Access node, Object arg) {
		int a = walk(node.getArray(), null);
		int i = walk(node.getIndex(), null);
		return  a+i+1;
	}

	@Override
	public Integer walkArithNode(Arith node, Object arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkUnaryNode(Unary node, Object arg) {
		int ex = walk(node.getExpr(), null);
		return  ex+1;
	}

	@Override
	public Integer walkAndNode(And node, Object arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;

	}

	@Override
	public Integer walkOrNode(Or node, Object arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkRelNode(Rel node, Object arg) {
		int ex1 = walk(node.getExpr1(), null);
		int ex2 = walk(node.getExpr2(), null);
		return  ex1+ex2+1;
	}

	@Override
	public Integer walkNotNode(Not node, Object arg) {
		int ex = walk(node.getExpr1(), null);
		return  ex+1;
	}

	@Override
	public Integer walkIdNode(Id node, Object arg) {
		return 1;
	}

	@Override
	public Integer walkConstantNode(Constant node, Object arg) {
		return 1;
	}

	@Override
	public Integer walkAssignIdNode(AssignId node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int id = walk(node.getIdent(), null);
		return  ex+id+1;
	}

	@Override
	public Integer walkAssignElemNode(AssignElem node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int acc = walk(node.getAcc(), null);
		return  ex+acc+1;
	}
	
	@Override
	public Integer walkAssignStmtNode(AssignStmt node, Object arg) {
		return walk(node.getAssign(), null) + 1;
	}

	@Override
	public Integer walkEmptyStmtNode(EmptyStmt node, Object arg) {
		return 1;
	}

	@Override
	public Integer walkWhileNode(While node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}

	@Override
	public Integer walkDoNode(Do node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}

	@Override
	public Integer walkProgramNode(Program node, Object arg) {
		return walk(node.getBlock(), null)+1;
	}

	@Override
	public Integer walkBlockNode(Block node, Object arg) {
		return walk(node.getStmts(), null)+1;
	}

	@Override
	public Integer walkIfNode(If node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+1;
	}


	@Override
	public Integer walkForNode(For node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int as1 = walk(node.getInit_ass(), null);
		int as2 = walk(node.getIter_ass(), null);
		int st = walk(node.getStmt(), null);
		return  ex+st+as1 + as2 +1;
	}

	@Override
	public Integer walkSeqNode(Seq node, Object arg) {
		int st1 = walk(node.getStmt1(), null);
		int st2 = walk(node.getStmt2(), null);
		return st1+st2+1;
	}

	@Override
	public Integer walkElseNode(Else node, Object arg) {
		int ex = walk(node.getExpr(), null);
		int st1 = walk(node.getStmt1(), null);
		int st2 = walk(node.getStmt2(), null);	
		return  ex+st1+st2+1;
	}

	@Override
	public Integer walkBreakNode(Break node, Object arg) {
		return 1;
	}

}
