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

}
