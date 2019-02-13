package main;

public class InternalHuffmanNode implements HuffmanNode {

	private HuffmanNode left;
	private HuffmanNode right;
	
	public InternalHuffmanNode() {
		this.left = null;
		this.right = null;
	}
	
	public InternalHuffmanNode(HuffmanNode lNode, HuffmanNode rNode) {
		this.left = lNode;
		this.right = rNode;
	}
	
	@Override
	public int count() {
		return this.left.count() + this.right.count();
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int symbol() {
		if (!this.isLeaf()) {
			throw new RuntimeException("Internal nodes dont have symbols");
		}
		return 0;
	}

	@Override
	public int height() {
		int lheight = 0;
		int rheight = 0;
		if (this.left() != null) {
			lheight = this.left().height();
		}
		if (this.right() != null) {
			rheight = this.right().height();
		}
		
		if (lheight > rheight) { return lheight + 1;}
		else { return rheight + 1;}
		
	}

	@Override
	public boolean isFull() {
		if (this.left() != null && this.right() != null && this.left().isFull() && this.right().isFull()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// insert symbol at given length
		// try left first, then right, then false
		// true if inserted
		
		if (length == 1) {
			if (this.left() == null) {
				this.left = new LeafHuffmanNode(symbol);
				return true;
			} else if (this.left() != null && this.right() == null) {
				this.right = new LeafHuffmanNode(symbol);
				return true;
			} else {
				return false;
			}
		}
		
		//when length != 1
		if (this.left() == null) {
			left = new InternalHuffmanNode();
			left.insertSymbol(length-1, symbol);
		} else if (!this.left().isFull()) {
			left().insertSymbol(length-1, symbol);
		} else if (this.right() == null) {
			right = new InternalHuffmanNode();
			right.insertSymbol(length-1, symbol);
		} else if (!this.right().isFull()) {
			right().insertSymbol(length-1, symbol);
		} else {
			return false;
		}		
		return false;
	}

	@Override
	public HuffmanNode left() {
		return left;
	}

	@Override
	public HuffmanNode right() {
		return right;
	}

}