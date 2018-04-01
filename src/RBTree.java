public class RBTree<T extends Comparable<T>> {
	private RBNode<T> root; //���ڵ�
	private static final boolean RED = false; //����������־
	private static final boolean BLACK = true;
	
	//�ڲ��ࣺ�ڵ���
	public class RBNode<T extends Comparable<T>>{
		boolean color; //��ɫ
		T key; //�ؼ���(��ֵ)
		RBNode<T> left; //���ӽڵ�
		RBNode<T> right; //���ӽڵ�
		RBNode<T> parent; //���ڵ�
		
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
	
	public RBNode<T> parentOf(RBNode<T> node) { //��ø��ڵ�
		return node != null? node.parent : null;
	}
	
	public void setParent(RBNode<T> node, RBNode<T> parent) { //���ø��ڵ�
		if(node != null) 
			node.parent = parent;
	}
	
	public boolean colorOf(RBNode<T> node) { //��ýڵ����ɫ
		return node != null? node.color : BLACK;
	}
	
	public boolean isRed(RBNode<T> node) { //�жϽڵ����ɫ
		return (node != null)&&(node.color == RED)? true : false;
	}
	
	public boolean isBlack(RBNode<T> node) {
		return !isRed(node);
	}
	
	public void setRed(RBNode<T> node) { //���ýڵ����ɫ
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
	 
	/***************** ǰ���������� *********************/
	public void preOrder() {
		preOrder(root);
	}

	private void preOrder(RBNode<T> tree) {
		if(tree != null) {
			System.out.print(tree.key + " ");
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	 
	/***************** ������������ *********************/
	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(RBNode<T> tree) {
		if(tree != null) {
			 preOrder(tree.left);
			 System.out.print(tree.key + " ");
			 preOrder(tree.right);
		 }
	}
	
	/***************** ������������ *********************/
	public void postOrder() {
		postOrder(root);
	}

	private void postOrder(RBNode<T> tree) {
		if(tree != null) {
			 preOrder(tree.left);
			 preOrder(tree.right);
			 System.out.print(tree.key + " ");
		 }
	}
	
	/**************** ���Һ�����м�ֵΪkey�Ľڵ� ***************/
	public RBNode<T> search(T key) {
		return search(root, key);
//		return search2(root, key); //ʹ�õݹ�ķ���������һ����
	}

	private RBNode<T> search(RBNode<T> x, T key) {
		while(x != null) {
			int cmp = key.compareTo(x.key);
			if(cmp < 0) 
				x = x.left;
			else if(cmp > 0) 
				x = x.right;
			else 
				return x;
		}
		return x;
	}
	//ʹ�õݹ�
	private RBNode<T> search2(RBNode<T> x, T key) {
		if(x == null)
			return x;
		int cmp = key.compareTo(x.key);
		if(cmp < 0)
			return search2(x.left, key);
		else if(cmp > 0) 
			return search2(x.right, key);
		else
			return x;
	}
	
	/**************** ������С�ڵ��ֵ  **********************/
	public T minValue() {
		RBNode<T> node = minNode(root);
		if(node != null)
			return node.key;
		return null;
	}

	private RBNode<T> minNode(RBNode<T> tree) {
		if(tree == null)
			return null;
		while(tree.left != null) {
			tree = tree.left;
		}
		return tree;
	}
	
	/******************** �������ڵ��ֵ *******************/
	public T maxValue() {
		RBNode<T> node = maxNode(root);
		if(node != null)
			return node.key;
		return null;
	}

	private RBNode<T> maxNode(RBNode<T> tree) {
		if(tree == null)
			return null;
		while(tree.right != null)
			tree = tree.right;
		return tree;
	}
	
	/********* ���ҽڵ�x�ĺ�̽ڵ�,�����ڽڵ�x����С�ڵ� ***********/
	public RBNode<T> successor(RBNode<T> x) {
		//���x�����ӽڵ㣬��ô��̽ڵ�Ϊ�������ӽڵ�Ϊ������������С�ڵ㡱
		if(x.right != null) 
			return minNode(x.right);
		//���xû�����ӽڵ㣬������������������
		//1. x���丸�ڵ�����ӽڵ㣬��x�ĺ�̽ڵ�Ϊ���ĸ��ڵ�
		//2. x���丸�ڵ�����ӽڵ㣬���Ȳ���x�ĸ��ڵ�p��Ȼ���p�ٴν����������������ж�
		RBNode<T> p = x.parent;
		while((p != null) && (x == p.right)) { //��Ӧ���2
			x = p;
			p = x.parent;
		}
		return p; //��Ӧ���1
		
	}
	
	/********* ���ҽڵ�x��ǰ���ڵ㣬��С�ڽڵ�x�����ڵ� ************/
	public RBNode<T> predecessor(RBNode<T> x) {
		//���x�����ӽڵ㣬��ôǰ�����Ϊ�����ӽڵ�Ϊ�������������ڵ㡱
		if(x.left != null) 
			return maxNode(x.left);
		//���xû�����ӽڵ㣬������������������
		//1. x���丸�ڵ�����ӽڵ㣬��x��ǰ���ڵ������ĸ��ڵ�
		//2. x���丸�ڵ�����ӽڵ㣬���Ȳ���x�ĸ��ڵ�p��Ȼ���p�ٴν����������������ж�
		RBNode<T> p = x.parent;
		while((p != null) && (x == p.left)) { //��Ӧ���2
			x = p;
			p = x.parent;
		}
		return p; //��Ӧ���1
	}
	
	/*************�Ժ�����ڵ�x������������ ******************/
	/*
	 * ����ʾ��ͼ���Խڵ�x��������
	 *     p                       p
	 *    /                       /
	 *   x                       y
	 *  / \                     / \
	 * lx  y      ----->       x  ry
	 *    / \                 / \
	 *   ly ry               lx ly
	 * �������������£�
	 * 1. ��y�����ӽڵ㸳��x�����ӽڵ�,����x����y���ӽڵ�ĸ��ڵ�(y���ӽڵ�ǿ�ʱ)
	 * 2. ��x�ĸ��ڵ�p(�ǿ�ʱ)����y�ĸ��ڵ㣬ͬʱ����p���ӽڵ�Ϊy(�����)
	 * 3. ��y�����ӽڵ���Ϊx����x�ĸ��ڵ���Ϊy
	 */
	private void leftRotate(RBNode<T> x) {
		//1. ��y�����ӽڵ㸳��x�����ӽڵ㣬����x����y���ӽڵ�ĸ��ڵ�(y���ӽڵ�ǿ�ʱ)
		RBNode<T> y = x.right;
		x.right = y.left;
		
		if(y.left != null) 
			y.left.parent = x;
		
		//2. ��x�ĸ��ڵ�p(�ǿ�ʱ)����y�ĸ��ڵ㣬ͬʱ����p���ӽڵ�Ϊy(�����)
		y.parent = x.parent;
		
		if(x.parent == null) {
			this.root = y; //���x�ĸ��ڵ�Ϊ�գ���y��Ϊ���ڵ�
		} else {
			if(x == x.parent.left) //���x�����ӽڵ�
				x.parent.left = y; //��Ҳ��y��Ϊ���ӽڵ�
			else
				x.parent.right = y;//����y��Ϊ���ӽڵ�
		}
		
		//3. ��y�����ӽڵ���Ϊx����x�ĸ��ڵ���Ϊy
		y.left = x;
		x.parent = y;		
	}
	
	/*************�Ժ�����ڵ�y������������ ******************/
	/*
	 * ����ʾ��ͼ���Խڵ�y��������
	 *        p                   p
	 *       /                   /
	 *      y                   x
	 *     / \                 / \
	 *    x  ry   ----->      lx  y
	 *   / \                     / \
	 * lx  rx                   rx ry
	 * �������������£�
	 * 1. ��x�����ӽڵ㸳��y�����ӽڵ�,����y����x���ӽڵ�ĸ��ڵ�(x���ӽڵ�ǿ�ʱ)
	 * 2. ��y�ĸ��ڵ�p(�ǿ�ʱ)����x�ĸ��ڵ㣬ͬʱ����p���ӽڵ�Ϊx(�����)
	 * 3. ��x�����ӽڵ���Ϊy����y�ĸ��ڵ���Ϊx
	 */
	private void rightRotate(RBNode<T> y) {
		//1. ��y�����ӽڵ㸳��x�����ӽڵ㣬����x����y���ӽڵ�ĸ��ڵ�(y���ӽڵ�ǿ�ʱ)
		RBNode<T> x = y.left;
		y.left = x.right;
		
		if(x.right != null) 
			x.right.parent = y;
		
		//2. ��x�ĸ��ڵ�p(�ǿ�ʱ)����y�ĸ��ڵ㣬ͬʱ����p���ӽڵ�Ϊy(�����)
		x.parent = y.parent;
		
		if(y.parent == null) {
			this.root = x; //���x�ĸ��ڵ�Ϊ�գ���y��Ϊ���ڵ�
		} else {
			if(y == y.parent.right) //���x�����ӽڵ�
				y.parent.right = x; //��Ҳ��y��Ϊ���ӽڵ�
			else
				y.parent.left = x;//����y��Ϊ���ӽڵ�
		}
		
		//3. ��y�����ӽڵ���Ϊx����x�ĸ��ڵ���Ϊy
		x.right = y;
		y.parent = x;		
	}
	
	/*********************** �������в���ڵ� **********************/
	public void insert(T key) {
		RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
		if(node != null) 
			insert(node);
	}
	
	//���ڵ���뵽������У���������������������һ����
	private void insert(RBNode<T> node) {
		RBNode<T> current = null; //��ʾ���node�ĸ��ڵ�
		RBNode<T> x = this.root; //�������������õ�
		
		//1. �ҵ������λ��
		while(x != null) {
			current = x;
			int cmp = node.key.compareTo(x.key);
			if(cmp < 0) 
				x = x.left;
			else
				x = x.right;
		}
		node.parent = current; //�ҵ���λ�ã�����ǰcurrent��Ϊnode�ĸ��ڵ�
		
		//2. �������ж�node�ǲ������ӽڵ㻹�����ӽڵ�
		if(current != null) {
			int cmp = node.key.compareTo(current.key);
			if(cmp < 0)
				current.left = node;
			else
				current.right = node;
		} else {
			this.root = node;
		}
		
		//3. ������������Ϊһ�ź����
		insertFixUp(node);
	}

	private void insertFixUp(RBNode<T> node) {
		RBNode<T> parent, gparent; //���常�ڵ���游�ڵ�
		
		//��Ҫ���������������ڵ���ڣ��Ҹ��ڵ����ɫ�Ǻ�ɫ
		while(((parent = parentOf(node)) != null) && isRed(parent)) {
			gparent = parentOf(parent);//����游�ڵ�
			
			//�����ڵ����游�ڵ�����ӽڵ㣬����else�����෴
			if(parent == gparent.left) {				
				RBNode<T> uncle = gparent.right; //�������ڵ�
				
				//case1: ����ڵ�Ҳ�Ǻ�ɫ
				if(uncle != null && isRed(uncle)) {
					setBlack(parent); //�Ѹ��ڵ������ڵ�Ϳ��
					setBlack(uncle);
					setRed(gparent); //���游�ڵ�Ϳ��
					node = gparent; //��λ�÷ŵ��游�ڵ㴦
					continue; //����while�������ж�
				}
				
				//case2: ����ڵ��Ǻ�ɫ���ҵ�ǰ�ڵ������ӽڵ�
				if(node == parent.right) {
					leftRotate(parent); //�Ӹ��ڵ㴦����
					RBNode<T> tmp = parent; //Ȼ�󽫸��ڵ���Լ�����һ�£�Ϊ����������׼��
					parent = node;
					node = tmp;
				}
				
				//case3: ����ڵ��Ǻ�ɫ���ҵ�ǰ�ڵ������ӽڵ�
				setBlack(parent);
				setRed(gparent);
				rightRotate(gparent);
			} else { //�����ڵ����游�ڵ�����ӽڵ�,���������ȫ�෴������һ����
				RBNode<T> uncle = gparent.left;
				
				//case1: ����ڵ�Ҳ�Ǻ�ɫ
				if(uncle != null & isRed(uncle)) {
					setBlack(parent);
					setBlack(uncle);
					setRed(gparent);
					node = gparent;
					continue;
				}
				
				//case2: ����ڵ��Ǻ�ɫ�ģ��ҵ�ǰ�ڵ������ӽڵ�
				if(node == parent.left) {
					rightRotate(parent);
					RBNode<T> tmp = parent;
					parent = node;
					node = tmp;
				}
				
				//case3: ����ڵ��Ǻ�ɫ�ģ��ҵ�ǰ�ڵ������ӽڵ�
				setBlack(parent);
				setRed(gparent);
				leftRotate(gparent);
			}
		}
		
		//�����ڵ�����Ϊ��ɫ
		setBlack(this.root);
	}
	
	/*********************** ɾ��������еĽڵ� **********************/
	public void remove(T key) {
		RBNode<T> node;
		if((node = search(root, key)) != null)
			remove(node);
	}
	
	private void remove(RBNode<T> node) {
		RBNode<T> child, parent;
		boolean color;
		
		//1. ��ɾ���Ľڵ㡰�����ӽڵ㶼��Ϊ�ա������
		if((node.left != null) && (node.right != null)) {
			//���ҵ���ɾ���ڵ�ĺ�̽ڵ㣬������ȡ����ɾ���ڵ��λ��
			RBNode<T> replace = node;
			//  1). ��ȡ��̽ڵ�
			replace = replace.right;
			while(replace.left != null) 
				replace = replace.left;
			
			//  2). ������̽ڵ㡱�͡���ɾ���ڵ�ĸ��ڵ㡱֮��Ĺ�ϵ
			if(parentOf(node) != null) { //Ҫɾ���Ľڵ㲻�Ǹ��ڵ�
				if(node == parentOf(node).left) 
					parentOf(node).left = replace;
				else
					parentOf(node).right = replace;
			} else { //����
				this.root = replace;
			}
			
			//  3). ������̽ڵ���ӽڵ㡱�͡���ɾ���ڵ���ӽڵ㡱֮��Ĺ�ϵ
			child = replace.right; //��̽ڵ�϶����������ӽڵ㣡
			parent = parentOf(replace);
			color = colorOf(replace);//�����̽ڵ����ɫ
			if(parent == node) { //��̽ڵ��Ǳ�ɾ���ڵ���ӽڵ�
				parent = replace;
			} else { //����
				if(child != null) 
					setParent(child, parent);
				parent.left = child;
				replace.right = node.right;
				setParent(node.right, replace);
			}
			replace.parent = node.parent;
			replace.color = node.color; //����ԭ��λ�õ���ɫ
			replace.left = node.left;
			node.left.parent = replace;
			
			if(color == BLACK) { //4. ������ߵĺ�̽ڵ���ɫ�Ǻ�ɫ���������������
				removeFixUp(child, parent);//����̽ڵ��child��parent����ȥ
			}
			node = null;
			return;
		}
	}
	//node��ʾ�������Ľڵ㣬����̽ڵ���ӽڵ㣨��Ϊ��̽ڵ㱻Ų��ɾ���ڵ��λ��ȥ�ˣ�
	private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
		RBNode<T> other;
		
		while((node == null || isBlack(node)) && (node != this.root)) {
			if(parent.left == node) { //node�����ӽڵ㣬����else������ĸպ��෴
				other = parent.right; //node���ֵܽڵ�
				if(isRed(other)) { //case1: node���ֵܽڵ�other�Ǻ�ɫ��
					setBlack(other);
					setRed(parent);
					leftRotate(parent);
					other = parent.right;
				}
				
				//case2: node���ֵܽڵ�other�Ǻ�ɫ�ģ���other�������ӽڵ�Ҳ���Ǻ�ɫ��
				if((other.left == null || isBlack(other.left)) && 
						(other.right == null || isBlack(other.right))) {
					setRed(other);
					node = parent;
					parent = parentOf(node);
				} else {
					//case3: node���ֵܽڵ�other�Ǻ�ɫ�ģ���other�����ӽڵ��Ǻ�ɫ�����ӽڵ��Ǻ�ɫ
					if(other.right == null || isBlack(other.right)) {
						setBlack(other.left);
						setRed(other);
						rightRotate(other);
						other = parent.right;
					}
					
					//case4: node���ֵܽڵ�other�Ǻ�ɫ�ģ���other�����ӽڵ��Ǻ�ɫ�����ӽڵ�������ɫ
					setColor(other, colorOf(parent));
					setBlack(parent);
					setBlack(other.right);
					leftRotate(parent);
					node = this.root;
					break;
				}
			} else { //������ĶԳ�
				other = parent.left;
				
	            if (isRed(other)) {
	                // Case 1: node���ֵ�other�Ǻ�ɫ��  
	                setBlack(other);
	                setRed(parent);
	                rightRotate(parent);
	                other = parent.left;
	            }

	            if ((other.left==null || isBlack(other.left)) &&
	                (other.right==null || isBlack(other.right))) {
	                // Case 2: node���ֵ�other�Ǻ�ɫ����other�������ӽڵ㶼�Ǻ�ɫ��  
	                setRed(other);
	                node = parent;
	                parent = parentOf(node);
	            } else {

	                if (other.left==null || isBlack(other.left)) {
	                    // Case 3: node���ֵ�other�Ǻ�ɫ�ģ�����other�����ӽڵ��Ǻ�ɫ�����ӽڵ�Ϊ��ɫ��  
	                    setBlack(other.right);
	                    setRed(other);
	                    leftRotate(other);
	                    other = parent.left;
	                }

	                // Case 4: node���ֵ�other�Ǻ�ɫ�ģ�����other�����ӽڵ��Ǻ�ɫ�ģ����ӽڵ�������ɫ
	                setColor(other, colorOf(parent));
	                setBlack(parent);
	                setBlack(other.left);
	                rightRotate(parent);
	                node = this.root;
	                break;
	            }
			}
		}
		if (node!=null)
	        setBlack(node);
	}
	
	/****************** ���ٺ���� *********************/
	public void clear() {
		destroy(root);
		root = null;
	}

	private void destroy(RBNode<T> tree) {
		if(tree == null) 
			return;
		if(tree.left != null) 
			destroy(tree.left);
		if(tree.right != null) 
			destroy(tree.right);
		tree = null;
	}

	/******************* ��ӡ����� *********************/
	public void print() {
		if(root != null) {
			print(root, root.key, 0);
		}
	}
	/*
	 * key---�ڵ�ļ�ֵ
	 * direction--- 0:��ʾ�ýڵ��Ǹ��ڵ�
	 *              1:��ʾ�ýڵ������ĸ��ڵ�����ӽڵ�
	 *              2:��ʾ�ýڵ������ĸ��ڵ�����ӽڵ�
	 */
	private void print(RBNode<T> tree, T key, int direction) {
		if(tree != null) {
			if(0 == direction) 
				System.out.printf("%2d(B) is root\n", tree.key);
			else
				System.out.printf("%2d(%s) is %2d's %6s child\n", 
						tree.key, isRed(tree)?"R":"b", key, direction == 1?"right":"left");
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
}