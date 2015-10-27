package lexer;

import java.io.*;
import java.util.*;
import symbols.*;

/*
 * Diese Klasse implementiert einen lexikalen Scanner für die
 * Beispielsprache. Die Instanzenvariablen sind
 * 		line für die momentane Zeilennummer der Eingabe
 * 		peek für das lookahead-Zeichen
 * 		words für eine Tabelle der reservierten Wörter und aller 
 * 			  im Programm auftretenden Identifier.
 */

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	/*
	 * words ist eine Tabelle aller reservierten Wörter und aller im Program auftretenden Identifier.
	 * Durch diese Tabelle ist gewährleistet, dass Identifier-Tokens eindeutig sind.
	 */


	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	public Lexer() {
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("for", Tag.FOR));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.False);
		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}
	
	/*
	 * readch() liest das nächste Zeichen der Eingabe und speichert es in peek
	 */
	void readch() throws IOException {
		peek = (char) System.in.read();
	}
	
	/*
	 * readch(char c) liest das nächste Zeichen der Eingabe und prüft, 
	 * ob es mit c übereinstimmt.
	 */
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;
		peek = ' ';
		return true;
	}
	
	/*
	 * rmComments(): full name remove comments
	 */
	void rmComments() throws IOException {
		for (;; readch()) {
			if (peek == '\n') {
				line = line + 1;
				break;
			}
		}
	}
	
	public Token scan() throws IOException {

		for (;; readch()) {
			if (peek == ' ' || peek == '\t' || peek == '\r')
				continue;
			if (peek == '/')
				if (!readch('/'))
					return (new Token('/'));
				else
					do readch();
					while(peek != '\n');
			if (peek == '\n')
				line = line + 1;
			else break;
		}

		/* hier werden Token erkannt, die aus mehr als einem Zeichen bestehen */

		switch (peek) {
		case '&':
			if (readch('&'))
				return Word.and;
			else
				return new Token('&');
		case '|':
			if (readch('|'))
				return Word.or;
			else
				return new Token('|');
		case '=':
			if (readch('='))
				return Word.eq;
			else
				return new Token('=');
		case '!':
			if (readch('='))
				return Word.ne;
			else
				return new Token('!');
		case '<':
			if (readch('='))
				return Word.le;
			else
				return new Token('<');
		case '>':
			if (readch('='))
				return Word.ge;
			else
				return new Token('>');
		}

		/* hier werden Zahlen erkannt */

		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			if (peek != '.')
				return new Num(v);
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek))
					break;
				x = x + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			return new Real(x);
		}

		/* hier werden Symbole erkannt */

		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);  // Suche Wort in Tabelle
			if (w != null)
				return w;				   // Wort gefunden
			w = new Word(s, Tag.ID);
			words.put(s, w);				// Wort eintragen
			return w;
		}

		/* jedes andere Zeichen bildet ein eigenes Token */

		Token tok = new Token(peek);
		peek = ' ';
		return tok;

	}
}
