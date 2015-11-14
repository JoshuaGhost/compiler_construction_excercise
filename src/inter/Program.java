package inter;


public class Program extends Stmt {
	Block block;

	public Program(Block b) {
		 block = b;
	}
	
	@Override
	public String toString() {
		return block.toString();
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" + block.toString(k)+"\n";
	}
}

	
