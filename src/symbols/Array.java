package symbols;
import lexer.*;

public class Array extends Type {
	Type of;
	int size = 1;
	
	public Array (int sz, Type p) {
		super("[]", Tag.INDEX, sz*p.width);
		size = sz;
		of = p;
	}
	

	public String toString() {
		return "[" + size + "] " + of.toString();
	}

}
