package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * a class for a Binary search tree. a binary tree where each node's left subtree is smaller than the node's
 * value, and the right subtree is bigger.
 */
public class BinarySearchTree implements Iterable<Integer> {
	TreeNode root;
	private int size;


	/**
	 * The default constructor.
	 */
	public BinarySearchTree() {
		setupEmptyTree();
	}

	/**
	 * A copy constructor that creates a deep copy of the given AvlTree. The new tree contains all the
	 * values of the given tree, but not necessarily in the same structure.
	 *
	 * @param tree The AVL tree to be copied.
	 */
	public BinarySearchTree(BinarySearchTree tree) {
		setupEmptyTree();
		if (tree != null) {
			for (int value : tree) {
				add(value);
			}
		}
	}

	/**
	 * A constructor that builds a new AVL tree containing all unique values in the input array.
	 *
	 * @param data the values to add to tree.
	 */
	public BinarySearchTree(int[] data) {
		setupEmptyTree();
		if (data != null) {
			for (int value : data) {
				add(value);
			}
		}
	}

	/*
	create an empty tree, for the constructors.
	 */
	private void setupEmptyTree() {
		root = null;
		size = 0;
	}

	/*
	finds the successor for given node
 	*/
	TreeNode successor(TreeNode node) {
		if (node.getRightSon() != null) {
			return findMin(node.getRightSon());
		}

		if (node.getFatherNode() == null) { // make sure we have a parent node to check...
			return null; // it turns out out node is the max node.
		}
		while (node.getFatherNode().getRightSon() == node) {
			node = node.getFatherNode();
			if (node.getFatherNode() == null) { // make sure we have a parent node to check...
				return null; // it turns out out node is the max node.
			}
		}
		return node.getFatherNode(); // this will be the 1st turn to the left, when climbing the tee.

	}

	/**
	 * Add a new node with the given key to the tree.
	 *
	 * @param newValue the value of the new node to add.
	 * @return true if the value to add is not already in the tree and it was successfully added, false
	 * otherwise.
	 */
	public boolean add(int newValue) {

		if (addAndReturnNode(newValue) != null) {
			return true;
		}
		return false;
	}



	/*
	this will try to add the value given. if added, will return the new node.
	else, will return false.
	this separate method will allow AvlTree to update the balance factors and fix the balance.
	 */
	TreeNode addAndReturnNode(int newValue) {
		if (root == null) {
			root = new TreeNode(newValue);
			size++;
			return root;
		}
		if (contains(newValue) != -1) { // check if value is in the tree already.
			return null;
		}
		TreeNode father = addHelper(root, newValue);
		TreeNode newNode = new TreeNode(newValue, father);
		if (father.getNodeData() > newValue) { // this method will only be called if father is not null
			father.setLeftSon(newNode);
		} else { // means the value is bigger
			father.setRightSon(newNode);
		}
		updateNodeHeight(newNode);
		size++;
		return newNode;

	}


	/*
	recursive helper method for add. will find the right parent for a new node.
	 */
	private TreeNode addHelper(TreeNode node, int newValue) {

		if (node.getNodeData() < newValue) {
			if (node.getRightSon() == null) {
				return node;
			}
			return this.addHelper(node.getRightSon(), newValue);
		}
		if (node.getNodeData() > newValue) {
			if (node.getLeftSon() == null) {
				return node;
			}
			return this.addHelper(node.getLeftSon(), newValue);
		} else { // shouldn't reach this!
			return null;
		}
	}

	/**
	 * Check whether the tree contains the given input value.
	 *
	 * @param searchVal value to search for
	 * @return if val is found in the tree, return the depth of the node (0 for the root) with the given
	 * value if it was found in the tree, -1 otherwise.
	 */
	public int contains(int searchVal) {
		TreeNode node = root;
		int counter = 0;
		while (node != null) {

			int nodeData = node.getNodeData();
			if (nodeData == searchVal) {
				return counter;
			}
			if (nodeData < searchVal) {
				node = node.getRightSon();
			} else {
				node = node.getLeftSon();
			}
			counter++;
		}
		return -1;

	}

	/*
	searches for a node that has the given value, and returns a pointer to it.
	if there is no such node, returns null.
	 */
	TreeNode member(int val) {
		TreeNode node = root;
		while (node != null) {
			int nodeData = node.getNodeData();
			if (nodeData == val) {
				return node;
			}
			if (nodeData < val) {
				node = node.getRightSon();
			} else {
				node = node.getLeftSon();
			}
		}

		return null;
	}

	/*
	updates the height of the subtrees, will track the nodes back to the root and update the height.
	 */
	void updateNodeHeight(TreeNode node) {
		if (node != null) { // base case - if the node is null, i.e. last node was root.
			int leftHeight = -1; // if there is no node, the height is -1.
			int rightHeight = -1;
			if (node.getRightSon() != null) {
				rightHeight = node.getRightSon().getHeight();
			}
			if (node.getLeftSon() != null) {
				leftHeight = node.getLeftSon().getHeight();
			}
			int newHeight = Math.max(leftHeight, rightHeight) + 1;
			//if (node.getHeight() != newHeight) {// no need to continue updating if the height is correct.
			node.setHeight(newHeight);
			updateNodeHeight(node.getFatherNode());
			//}
		}

	}

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 *
	 * @param toDelete the value to remove from the tree.
	 * @return true if the given value was found and deleted, false otherwise.
	 */
	public boolean delete(int toDelete) {
		TreeNode node = member(toDelete); // make sure 1st the value is in the tree, and if so, find it's node
		if (node == null) {
			return false;
		}
		deleteHelper(node); // actually delete this node.
		return true;
	}

	/*
	recursive helper function for delete - will switch the values of the nodes, until the value we need
	to delete is in a leaf, then delete this leaf.
	will return the node's parent.
	 */
	TreeNode deleteHelper(TreeNode node) {
		if ((node.getLeftSon() != null) && (node.getRightSon() != null)) {
			node = switchNode(node);

		}
		// now one  of the node's sons is null:
		if (node.getRightSon() == null) {
			if (node == root) {
				root = node.getLeftSon();

			} else {// connect the node's son to it's parent instead
				updateParentNode(node, node.getLeftSon());

			}
		} else { // means there is no left son
			if (node == root) {
				root = node.getRightSon();


			} else {// connect the node's son to it's parent instead
				updateParentNode(node, node.getRightSon());

			}

		}
		updateNodeHeight(node.getFatherNode());
		size--;
		return node.getFatherNode();
	}

	/*
	connect the node's parent to the node's given child, instead of the node
	 */
	private void updateParentNode(TreeNode node, TreeNode childNode) {
		if (node.getFatherNode() == null) {
			return;
		}
		if (node.getFatherNode().getLeftSon() == node) {
			node.getFatherNode().setLeftSon(childNode);
		} else {
			node.getFatherNode().setRightSon(childNode);
		}
		if (childNode != null) {
			childNode.setFatherNode(node.getFatherNode());
		}


	}

	/*
	helper function to delete, will switch the node value with it's successor, and return it's successor.
	 */
	private TreeNode switchNode(TreeNode node) {
		int val = node.getNodeData();
		TreeNode successorNode = successor(node);
		node.setNodeData(successorNode.getNodeData());
		successorNode.setNodeData(val);
		return successorNode;
	}

	/*
	finds the node with min value in the subtree of the given node.
	 */
	private static TreeNode findMin(TreeNode node) {
		if (node == null) {
			return null;
		}
		if (node.getLeftSon() == null) {
			return node;
		} else {
			return findMin((node.getLeftSon()));
		}
	}


	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<Integer> iterator() {
		class Iter implements Iterator<Integer> {
			private TreeNode node = findMin(root);

			/**
			 * Returns {@code true} if the iteration has more elements.
			 * (In other words, returns {@code true} if {@link #next} would
			 * return an element rather than throwing an exception.)
			 *
			 * @return {@code true} if the iteration has more elements
			 */
			@Override
			public boolean hasNext() {
				return (node != null);
			}

			/**
			 * Returns the next element in the iteration.
			 *
			 * @return the next element in the iteration
			 * @throws NoSuchElementException if the iteration has no more elements
			 */
			@Override
			public Integer next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				} else {
					int val = node.getNodeData();
					node = successor(node);
					return val;
				}
			}
		}
		return new Iter();
	}

	/**
	 * @return the number of nodes in the tree.
	 */
	public int size() {
		return size;
	}

}

