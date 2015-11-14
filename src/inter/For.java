package inter;

public class For extends Stmt {

	/*
	 * For ist eine Unterklasse von Stmt. In der Instanzenvariable init_ass wird
	 * die Initialisierungs-Anweisung, in expr die Abbruchbedingung, in iter_ass
	 * die Iterationsanweisung und in stmt der Rumpf abgelegt.
	 */

	Expr expr;
	Assignment init_ass;
	Assignment iter_ass;
	Stmt stmt;

	public For() {
		expr = null;
		stmt = null;
		init_ass = null;
		iter_ass = null;
	}

	public void init(Assignment a1, Expr x, Assignment a2, Stmt s) {
		expr = x;
		init_ass = a1;
		iter_ass = a2;
		stmt = s;
	}

	@Override
	public String toString() {
		return ("for ("+init_ass.toString()+";"+
						expr.toString()+";"+
						iter_ass.toString()+")"+
						stmt.toString());
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +
			   init_ass.toString(k+1) + "\n" +
			   expr.toString(k+1) + "\n" +
			   iter_ass.toString(k+1) + "\n" +
			   stmt.toString(k+1);
	}
}
