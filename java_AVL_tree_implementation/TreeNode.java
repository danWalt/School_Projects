package oop.ex4.data_structures;

/**
 * a class implementing a Node object, representing a node in a binary tree. used as a Node in a binary
 * search tree, and other types of tree extending BST.
 */
public class TreeNode {

	private int height; // this is not needed in a basic BST, but it is useful in many other types of
							// BST, such as AVL tree
	private int nodeData;

	private TreeNode leftSon;

	private TreeNode rightSon;

	private TreeNode fatherNode;



	/**
	 * constructor for a node that is not a root.
	 *
	 * @param nodeData   the	data the node will contain.
	 * @param fatherNode the father of current node.
	 */
	public TreeNode(int nodeData, TreeNode fatherNode) {
		setupNode(nodeData);
		this.fatherNode = fatherNode;

	}

	/**
	 * constructor for a root, won't get father's node and data.
	 * @param nodeData: tha data for the given node.
	 */
	public TreeNode(int nodeData) {
		setupNode(nodeData);
		fatherNode = null;
	}

	/*
	sets up the node with the given data, with the basic parameters (i.e. no sons and no height)
	 */
	private void setupNode(int data){
		this.nodeData = data;
		rightSon = null;
		leftSon = null;
		height = 0;
	}
	/**
	 * @return current node's data.
	 */
	public int getNodeData() {
		return nodeData;
	}


	/**
	 * @return current node's left son
	 */
	public TreeNode getLeftSon() {
		return this.leftSon;
	}

	/**
	 * @param leftSon Sets a new node as current node left son
	 */
	public void setLeftSon(TreeNode leftSon) {
		this.leftSon = leftSon;
	}

	/**
	 * @return current node's right son
	 */
	public TreeNode getRightSon() {
		return this.rightSon;
	}


	/**
	 * @param rightSon Sets a new node as current node right son.
	 */
	public void setRightSon(TreeNode rightSon) {
		this.rightSon = rightSon;
	}

	/**
	 * @return current node's father node
	 */
	public TreeNode getFatherNode() {
		return this.fatherNode;
	}

	/**
	 * updates current node's father node
	 *
	 * @param fatherNode new father node to assign to current node.
	 */
	public void setFatherNode(TreeNode fatherNode) {
		this.fatherNode = fatherNode;
	}

	/**
	 * setter for the node data
	 *
	 * @param val value to set.
	 */
	public void setNodeData(int val) {
		nodeData = val;
	}

	/**
	 * getter method for height
	 * @return the height of the tree.
	 */
	public int getHeight(){
		return height;
	}

	/**
	 *	setter function for height
	 * @param newHeight  the new height
	 */
	public void setHeight(int newHeight){height = newHeight;}
}


