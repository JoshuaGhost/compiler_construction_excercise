package inter;

/*
 * Die Unterklasse Stmt von Node fasst alle Statements zusammen.
 * next speichert das Sprungziel nach dem Statement,
 * enclosing verweist auf das umfassende Statement, das
 * beim break-Statement angesprungen wird.
 */

public abstract class Stmt extends Node {
	int next = 0;
	private static Stmt enclosing = null;

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public Stmt() {
	}

	public static Stmt getEnclosing() {
		return enclosing;
	}

	public static void setEnclosing(Stmt encl) {
		enclosing = encl;
	};

}
