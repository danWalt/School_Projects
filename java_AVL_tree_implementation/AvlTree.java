package oop.ex4.data_structures;

/**
 * a class for an object of an AVL tree - a binary search tree that will always make sure the height
 * differences of any 2 subtrees of a node are not bigger than 1. this will keep the height of the tree
 * as O(log(n)), and so actions in this tree will have a good time complexity, of O(log(n)) for most of
 * the actions.
 */
public class AvlTree extends BinarySearchTree {

	private enum ViolationType {LL, RR, LR, RL}


	/**
	 * The default constructor.
	 */
	public AvlTree() {
	}

	/**
	 * A copy constructor that creates a deep copy of the given AvlTree. The new tree contains all the
	 * values of the given tree, but not necessarily in the same structure.
	 *
	 * @param tree The AVL tree to be copied.
	 */
	public AvlTree(AvlTree tree) {
		super(tree);

	}

	/**
	 * A constructor that builds a new AVL tree containing all unique values in the input array.
	 *
	 * @param data the values to add to tree.
	 */
	public AvlTree(int[] data) {
		super(data);
	}

	/**
	 * Removes the node with the given value from the tree, if it exists.
	 *
	 * @param toDelete the value to remove from the tree.
	 * @return true if the given value was found and deleted, false otherwise.
	 */
	@Override
	public boolean delete(int toDelete) {

		TreeNode node = member(toDelete); // make sure 1st the value is in the tree, and if so, find it's node
		if (node == null) {
			return false;
		}
		node = deleteHelper(node); // actually delete this node
		checkForImbalances(node);
		return true;
	}

	/**
	 * Calculates the minimum number of nodes in an AVL tree of height h.
	 *
	 * @param h the height of the tree (a non-negative number) in question.
	 * @return the minimum number of nodes in an AVL tree of the given height.
	 */
	public static int findMinNodes(int h) {
		return (int) ((1 / (Math.sqrt(5))) * ((Math.pow(((1 + Math.sqrt(5)) / 2), h + 3)) -
				(Math.pow(((1 - Math.sqrt(5)) / 2), h + 3)))) - 1;
	}

	/**
	 * Calculates the maximum number of nodes in an AVL tree of height h.
	 *
	 * @param h the height of the tree (a non-negative number) in question.
	 * @return the maximum number of nodes in an AVL tree of the given height.
	 */
	public static int findMaxNodes(int h) {
		return (int) Math.pow(2, h+1) - 1;
	}


	/**
	 * Add a new node with the given key to the tree.
	 *
	 * @param newValue the value of the new node to add.
	 * @return true if the value to add is not already in the tree and it was successfully added, false
	 * otherwise.
	 */
	@Override
	public boolean add(int newValue) {

		TreeNode newNode = addAndReturnNode(newValue);
		if (newNode == null) {
			return false;
		}
		checkForImbalances(newNode);
		return true;
	}

	/*
	balance factor should only be 1,0 or -1. if it is higher/lower there is an imbalance
	 */
	private void checkForImbalances(TreeNode currentNode) {
		if (currentNode == null) {
			return;
		}
		int balanceFactor = calculateBalanceFactor(currentNode);
		if (balanceFactor > 1 || balanceFactor < -1) {
			ViolationType currentViolation = getTypeOfImbalance(currentNode, balanceFactor);
			performRotation(currentViolation, currentNode); // this function is only called if there is a
													//violation to start with before it checks for the type.
		} else {
			checkForImbalances(currentNode.getFatherNode());
		}
	}

	/*
	given a node, by checking node's sons height it calculates its balance factor
	 */
	private int calculateBalanceFactor(TreeNode currentNode) {
		int leftSonHeight = getLeftHeight(currentNode);
		int rightSonHeight = getRightHeight(currentNode);
		return rightSonHeight - leftSonHeight;
	}

	/*
	returns node's right son height. If such a node doesn't exists, returns -1.
	 */
	private int getRightHeight(TreeNode currentNode) {
		if (currentNode.getRightSon() != null) {
			return currentNode.getRightSon().getHeight();
		} else return -1;
	}

	/*
	returns node's left son height. If such a node doesn't exists, returns -1.
	 */
	private int getLeftHeight(TreeNode currentNode) {
		if (currentNode.getLeftSon() != null) {
			return currentNode.getLeftSon().getHeight();
		} else return -1;
	}


	/*
	determines type of imbalance.
	 */
	private ViolationType getTypeOfImbalance(TreeNode currentNode, int balanceFactor) {

		if (balanceFactor > 1) {
			if (calculateBalanceFactor(currentNode.getRightSon()) >= 0) {
				return ViolationType.RR;
			} else {
				return ViolationType.RL;
			}
		} else if (balanceFactor < -1) {
			if (calculateBalanceFactor(currentNode.getLeftSon()) <= 0) {
				return ViolationType.LL;
			} else {
				return ViolationType.LR;
			}
		}
		return null; // Checks for type only if there is a violation so this return statement never happens
	}


	/*
	performs left rotation on a given node and updates current node height
	 */
	private void leftRotation(TreeNode node) {
		if (root == node) {
			root = node.getRightSon();
		}
		TreeNode newSubRoot = node.getRightSon();
		if (newSubRoot != null){

			TreeNode subRootLeftSon = newSubRoot.getLeftSon();
			newSubRoot.setLeftSon(node);
			newSubRoot.setFatherNode(node.getFatherNode());
			node.setRightSon(subRootLeftSon);
			if (node.getFatherNode() != null) {
				if (node == node.getFatherNode().getLeftSon()) {
					node.getFatherNode().setLeftSon(newSubRoot);
				}
				else {
					node.getFatherNode().setRightSon(newSubRoot);
				}
			}
			node.setFatherNode(newSubRoot);
			if (subRootLeftSon != null) {
				subRootLeftSon.setFatherNode(node);

			}
			updateNodeHeight(node);

		}



	}

	/*
	preforms right rotation on a given node and updates current node height
	 */
	private void rightRotation(TreeNode node) {
		if (root == node) {
			root = node.getLeftSon();
		}
		TreeNode newSubRoot = node.getLeftSon();
		if (node.getLeftSon() != null){
			TreeNode subRootRightSon = newSubRoot.getRightSon();
			newSubRoot.setRightSon(node);
			newSubRoot.setFatherNode(node.getFatherNode());
			node.setLeftSon(subRootRightSon);
			if (node.getFatherNode() != null) {
				if (node == node.getFatherNode().getLeftSon()) {
					node.getFatherNode().setLeftSon(newSubRoot);
				}
				else {
					node.getFatherNode().setRightSon(newSubRoot);
				}
			}
			node.setFatherNode(newSubRoot);

			if (subRootRightSon != null) {
				subRootRightSon.setFatherNode(node);
			}
			updateNodeHeight(node);

		}

	}

	/*
	based on input violation types, calls the right rotation in order to maintain a balanced tree
	 */
	private void performRotation(ViolationType violation, TreeNode node) {
		switch (violation) {
			case LL:
				rightRotation(node);
				break;
			case LR:
				leftRotation(node.getLeftSon());
				rightRotation(node);
				break;
			case RR:
				leftRotation(node);
				break;
			case RL:
				rightRotation(node.getRightSon());
				leftRotation(node);
				break;
		}
	}


}
