public class RBTree<T extends Comparable<T>> {
	private RBNode<T> root;
	private static final boolean RED = false; 
	private static final boolean BLACK = true;
	
	
	public class RBNode<T extends Comparable<T>>{
		boolean color; 
		T key; 
		RBNode<T> left; 
		RBNode<T> right; 
		RBNode<T> parent; 
		
		public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
			this.key = key;
			this.color = color;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
		public T getKey() {
			return key;
		}
		
		public String toString() {
			return "" + key + (this.color == RED? "R" : "B");
		}
	}
	
	public RBTree() {
		root = null;
	}
	
	public RBNode<T> parentOf(RBNode<T> node) {
		return node != null? node.parent : null;
	}
	
	public void setParent(RBNode<T> node, RBNode<T> parent) { 
		if(node != null) 
			node.parent = parent;
	}
	
	public boolean colorOf(RBNode<T> node) { 
		return node != null? node.color : BLACK;
	}
	
	public boolean isRed(RBNode<T> node) { 
		return (node != null)&&(node.color == RED)? true : false;
	}
	
	public boolean isBlack(RBNode<T> node) {
		return !isRed(node);
	}
	
	public void setRed(RBNode<T> node) { 
		if(node != null) 
			node.color = RED;
	}
	
	public void setBlack(RBNode<T> node) {
		if(node != null) {
			node.color = BLACK;
		}
	}
	 
	public void setColor(RBNode<T> node, boolean color) {
		if(node != null)
			node.color = color;
	}

	private void leftRotate(RBNode<T> x) {
		RBNode<T> y = x.right;
		x.right = y.left;
		
		if(y.left != null) 
			y.left.parent = x;
		y.parent = x.parent;
		
		if(x.parent == null) {
			this.root = y; 
		} else {
			if(x == x.parent.left) 
				x.parent.left = y; 
			else
				x.parent.right = y;
		}
		y.left = x;
		x.parent = y;		
	}
	
	private void rightRotate(RBNode<T> y) {
		RBNode<T> x = y.left;
		y.left = x.right;
		if(x.right != null) 
			x.right.parent = y;
		x.parent = y.parent;
		if(y.parent == null) {
			this.root = x; 
		} else {
			if(y == y.parent.right) 
				y.parent.right = x; 
			else
				y.parent.left = x;
		}
		x.right = y;
		y.parent = x;		
	}	
	public void insert(T key) {//≤Â»Î
		RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
		if(node != null) 
			insert(node);
	}
	private void insert(RBNode<T> node) {
		RBNode<T> current = null; 
		RBNode<T> x = this.root; 

		while(x != null) {
			current = x;
			int cmp = node.key.compareTo(x.key);
			if(cmp < 0) 
				x = x.left;
			else
				x = x.right;
		}
		node.parent = current; 
		if(current != null) {
			int cmp = node.key.compareTo(current.key);
			if(cmp < 0)
				current.left = node;
			else
				current.right = node;
		} else {
			this.root = node;
		}
		insertFixUp(node);
	}

	private void insertFixUp(RBNode<T> node) {
		RBNode<T> parent, gparent; 
		while(((parent = parentOf(node)) != null) && isRed(parent)) {
			gparent = parentOf(parent);
			if(parent == gparent.left) {				
				RBNode<T> uncle = gparent.right; 
				if(uncle != null && isRed(uncle)) {
					setBlack(parent); 
					setBlack(uncle);
					setRed(gparent); 
					node = gparent; 
					continue; 
				}
				
				if(node == parent.right) {
					leftRotate(parent); 
					RBNode<T> tmp = parent; 
					parent = node;
					node = tmp;
				}
				setBlack(parent);
				setRed(gparent);
				rightRotate(gparent);
			} 
			else { 
				RBNode<T> uncle = gparent.left;	
				if(uncle != null & isRed(uncle)) {
					setBlack(parent);
					setBlack(uncle);
					setRed(gparent);
					node = gparent;
					continue;
				}	
				if(node == parent.left) {
					rightRotate(parent);
					RBNode<T> tmp = parent;
					parent = node;
					node = tmp;
				}
				setBlack(parent);
				setRed(gparent);
				leftRotate(gparent);
			}
		}
		setBlack(this.root);
	}
}