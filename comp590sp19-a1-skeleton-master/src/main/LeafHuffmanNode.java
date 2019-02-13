package main;

public class LeafHuffmanNode implements HuffmanNode {
	
	private int count;
	private int symbol;
	
	public LeafHuffmanNode(int symbol) {
		this.symbol = symbol;
		this.count = 0;
	}
	
	public LeafHuffmanNode(int count, int symbol) {
		this.count = count;
		this.symbol = symbol;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// TODO Auto-generated method stub
		if (this.isLeaf()) {
			throw new RuntimeException("Can't add symbol to leaf node");
		}
		return false;
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		if (this.isLeaf()) {
			throw new RuntimeException("no children for leaf");
		}
		return null;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		if (this.isLeaf()) {
			throw new RuntimeException("no children for leaf");
		}
		return null;
	}

}
