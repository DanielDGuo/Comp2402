package comp2402a5;
// Thanks to Pat Morin for the skeleton of this file!

import java.util.Random;

public class AVLTree<T> extends
		BinarySearchTree<AVLTree.Node<T>, T> implements SSet<T> {
	Random rand;

	public static class Node<T> extends BSTNode<Node<T>, T> {
		public int h; // the height of the node
	}

	/*public static void main(String args[]) {
		AVLTree<Integer> tree = new AVLTree<>();
		Random rand = new Random();
		for (int i = 0; i < 100; i++) {
			tree.add(rand.nextInt(100000));
			tree.checkHeights(tree.r);
			System.out.println(tree.r.x);
		}
	}*/

	public AVLTree() {
		sampleNode = new Node<T>();
		rand = new Random();
		c = new DefaultComparator<T>();
	}

	public int height(Node<T> u) {
		return (u == null) ? 0 : u.h;
	}

	public boolean add(T x) {
		Node<T> u = new Node<T>();
		u.x = x;
		if (super.add(u)) {
			for (Node<T> w = u; w != nil; w = w.parent) {
				// walk back up to the root adjusting heights
				w.h = Math.max(height(w.left), height(w.right)) + 1;
			}
			fixup(u);
			return true;
		}
		return false;
	}

	public void splice(Node<T> u) {
		Node<T> w = u.parent;
		super.splice(u);
		for (Node<T> z = u; z != nil; z = z.parent)
			z.h = Math.max(height(z.left), height(z.right)) + 1;
		fixup(w);
	}

	public void checkHeights(Node<T> u) {
		if (u == nil)
			return;
		checkHeights(u.left);
		checkHeights(u.right);
		if (height(u) != 1 + Math.max(height(u.left), height(u.right)))
			throw new RuntimeException("Check heights shows incorrect heights");
		int dif = height(u.left) - height(u.right);
		if (dif < -1 || dif > 1)
			throw new RuntimeException("Check heights found height difference of " + dif);
	}

	/*
	 * 
	 * @param u
	 */
	public void fixup(Node<T> u) {
		while (u != nil) {
			int dif = height(u.left) - height(u.right);
			if (dif > 1) {// left leaning
				if (height(u.left.left) - height(u.left.right) == -1) {//checks if a rotation on the left child is needed
					// System.out.println("rotating left on " + u.left.x);
					rotateLeft(u.left);
				}
				// System.out.println("rotating right on " + u.x);
				rotateRight(u);
			} else if (dif < -1) {// right leaning
				if (height(u.right.left) - height(u.right.right) == 1) {//checks if a rotation on the right child is needed
					// System.out.println("rotating right on " + u.right.x);
					rotateRight(u.right);
				}
				// System.out.println("rotating left on " + u.x);
				rotateLeft(u);
			}
			u = u.parent;
		}
	}

	public void rotateLeft(Node<T> u) {
		super.rotateLeft(u);
		// u moves down, child moves up`
		while (u != null) {
			//u.left and u,right heights should not have changed; can be assumed they are accurate
			u.h = Math.max(height(u.left), height(u.right)) + 1;
			u = u.parent;
		}
		//checkHeights(u);
	}

	public void rotateRight(Node<T> u) {
		super.rotateRight(u);
		// u moves down, child moves up
		while (u != null) {
			//u.left and u,right heights should not have changed; can be assumed they are accurate
			u.h = Math.max(height(u.left), height(u.right)) + 1;
			u = u.parent;
		}
		//checkHeights(u);
	}
}