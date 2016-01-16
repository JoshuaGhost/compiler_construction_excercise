package code;

/*
 * Diese Klasse repräsentiert die Paare (trueLabel, falseLabel) und
 * (beginLabel, endLabel), die zur Übersetzung von Steuerstrukturen 
 * in der Beispiel- Programmiersprache benötigt werden.
 */

public class LabelPair {
	int first;
	int second;
	
	public LabelPair(int i, int j) {
		first = i;
		second = j;
	}
	
	public int trueLabel() {
		return first;
	}
	
	public int falseLabel() {
		return second;
	}

//	int beginLabel() {
//		return first;
//	}
//	
//	int endLabel() {
//		return second;
//	}
	public int nextLabel() {
		return first;
	}

}
