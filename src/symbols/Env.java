/*
 * Dies ist die Klasse Env, die die Symboltabelle implementiert.
 * Dargestellt wird sie als eine verkettete Liste von Hash-Tabellen,
 * auf die jeweils mit einem Token als Schlüssel zugegeriffen wird.
 * Als Werte hat man Objekte der Klasse Id.
 * @author rp
 */

package symbols;
import java.util.*;
import lexer.*;
import inter.*;

public class Env {
	private Hashtable<Token,Id> table;
	protected Env prev;
	
	public Env(Env prevEnv) {
		table = new Hashtable<Token, Id>();
		prev = prevEnv;
	}
	
	public void put(Token w, Id i) {
		table.put(w, i);
	}
	
	public Id get (Token w) {
		for (Env e = this; e != null; e = e.prev ) {
			Id found = (Id) (e.table.get(w));
			if (found != null)
				return found;
		}
	return null;	
	}

}
