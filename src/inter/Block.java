package inter;

/*
 * Block ist eine Unterklasse von Stmt. In der Instanzenvariable stmts
 * werden die Anweisungen des Blocks abgelegt.
 */

public class Block extends Stmt {
	Stmt stmts;

	public Block(Stmt s) {
		stmts = s;
	}
	
	@Override
	public String toString() {
		return("{"+stmts.toString()+"}");
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +stmts.toString(k+1);
	}

}
