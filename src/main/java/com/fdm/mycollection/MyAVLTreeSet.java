package com.fdm.mycollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyAVLTreeSet<T extends Comparable<T>> implements Iterable<T> {

	// AVL Node class
	private class Node {
		private T key; 
		private Node left, right;
		private int height;

		public Node(T key) {
			this.key = key;
			this.height = 1;
		}
	}

	private Node root;
	private List<T> nodes;

	public MyAVLTreeSet() {
		root = null;
		nodes = new ArrayList<T>();
	}



	/* public methods */

	public void insert(T key) {
		root = insert(root, key);

	}
	
	
	public void delete(T key) {
		root = deleteNode(root, key);
	}
	
	
	// check if key exists in the set
	public boolean contains(T key) {
		Iterator<T> it = this.iterator();
		while(it.hasNext()) {
			if(it.next().equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public Iterator<T> iterator() {
		nodes.clear();
		inOrder(root);
		Iterator<T> it = new Iterator<T>() {
			private int currentIndex = 0;
			@Override
			public boolean hasNext() {
				return currentIndex < nodes.size();
			}

			@Override
			public T next() {
				return nodes.get(currentIndex++);
			}

		};
		return it;
	}


	public List<T> getNodes() {
		updateNodesList();
		return nodes;
	}


	public Node getRoot() {
		return root;
	}

	public int size() {
		updateNodesList();
		return nodes.size();
	}

	
	
	/* private util methods */
	
	private Node insert(Node node, T key) {

		// Perform Binary Search Tree insertion
		if (node == null)
			return (new Node(key));

		if (key.compareTo(node.key) < 0)
			node.left = insert(node.left, key);
		else if (key.compareTo(node.key) > 0)
			node.right = insert(node.right, key);
		else // Duplicate keys not allowed
			return node;

		// Update height of this ancestor node
		node.height = 1 + max(height(node.left),
				height(node.right));

		// Get the balance factor of this ancestor node to check whether this node became  unbalanced
		int balance = getBalance(node);

		// If this node becomes unbalanced, then there 4 cases 
		// Left Left Case
		if (balance > 1 && key.compareTo(node.left.key) < 0)
			return rightRotate(node);

		// Right Right Case
		if (balance < -1 && key.compareTo(node.right.key) > 0)
			return leftRotate(node);

		// Left Right Case
		if (balance > 1 && key.compareTo(node.left.key) > 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		// Right Left Case
		if (balance < -1 && key.compareTo(node.right.key) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		// return the node pointer
		return node;
	}


	Node deleteNode(Node root, T key) {
		// Binary Search Tree DELETE
		if (root == null)
			return root;

		// If the key to be deleted is smaller, then it lies in left subtree
		if (key.compareTo(root.key) < 0)
			root.left = deleteNode(root.left, key);

		// If the key to be deleted is greater, then it lies in right subtree
		else if (key.compareTo(root.key) > 0)
			root.right = deleteNode(root.right, key);

		// if key is same as root's key, then this is the node to be deleted
		else {
			// node with only one child or no child
			if ((root.left == null) || (root.right == null)) {
				Node temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				// No child case
				if (temp == null) {
					temp = root;
					root = null;
				}
				else   // One child case
				root = temp; // Copy the contents of the non-empty child
			} else {
				// node with two children: Get the inorder successor (smallest in the right subtree)
				Node temp = minValueNode(root.right);

				// Copy the inorder successor's data to this node
				root.key = temp.key;

				// Delete the inorder successor
				root.right = deleteNode(root.right, temp.key);
			}
		}

		// If the tree had only one node then return
		if (root == null)
			return root;

		// UPDATE HEIGHT OF THE CURRENT NODE
		root.height = max(height(root.left), height(root.right)) + 1;

		// check whether this node became unbalanced
		int balance = getBalance(root);

		// If this node becomes unbalanced, then there are 4 cases Left Left Case
		if (balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);

		// Left Right Case
		if (balance > 1 && getBalance(root.left) < 0) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}

		// Right Right Case
		if (balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);

		// Right Left Case
		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}

		return root;
	}
	
	
	// return height of the node
	private int height(Node t) {
		return t == null ? 0 : t.height;
	}

	
	private void updateNodesList() {
		nodes.clear();
		inOrder(root);
	}

	// return max number of two integer
	private int max(int a, int b) {
		return (a > b) ? a : b;
	}

	// right rotate subtree rooted at y
	private Node rightRotate(Node y) {
		Node x = y.left;
		Node T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;

		// Return new root
		return x;
	}

	// left rotate subtree rooted at x
	private Node leftRotate(Node x) {
		Node y = x.right;
		Node T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		//  Update heights
		x.height = max(height(x.left), height(x.right)) + 1;
		y.height = max(height(y.left), height(y.right)) + 1;

		// Return new root
		return y;
	}

	// Get Balance factor of node N
	private int getBalance(Node N) {
		if (N == null)
			return 0;

		return height(N.left) - height(N.right);
	}


	// return the node with minimum key value
	private Node minValueNode(Node node)
	{
		Node current = node;

		// loop down to find the leftmost leaf
		while (current.left != null)
			current = current.left;

		return current;
	}

	
	// Inorder tree travasal 
	private void inOrder(Node node) {
		if(node != null) {
			inOrder(node.left);
			//System.out.print(node.key + " ");
			nodes.add(node.key);
			inOrder(node.right);
		}
	}



	public static void main(String[] args) {
		MyAVLTreeSet<Integer> tree = new MyAVLTreeSet<>();

		// Constructing tree
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		tree.insert(6);
		tree.insert(11);
		tree.insert(-1);
		tree.insert(1);
		tree.insert(2);

		System.out.println("iterating:");
		Iterator<Integer> it = tree.iterator();
		while(it.hasNext()) {
			System.out.print(it.next() + " ");
		}

		tree.root = tree.deleteNode(tree.root, 10);
		
		System.out.println();
		it = tree.iterator();
		while(it.hasNext()) {
			System.out.print(it.next() + " ");
		}
	}


}
