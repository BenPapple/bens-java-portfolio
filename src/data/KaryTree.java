package data;

/**
 * Implements a k-ary tree structure with two or three leaves, each node has a
 * data field for strings which holds the name of a math formula.
 *
 * @author BenGe47
 */
public class KaryTree {

	private String cargoFormula;
	private KaryTree left;
	private KaryTree center;
	private KaryTree right;

	/**
	 * Constructor for a binary tree.
	 *
	 * @param cargo name of a math formula
	 * @param left first child node
	 * @param right second child node
	 */
	public KaryTree(String cargo, KaryTree left, KaryTree right) {
		this.cargoFormula = cargo;
		this.left = left;
		this.right = right;
	}

	/**
	 * Constructor for a trinary tree.
	 *
	 * @param cargo name of a math formula
	 * @param left first child node
	 * @param right third child node
	 * @param center second child node
	 */
	public KaryTree(String cargo, KaryTree left, KaryTree right, KaryTree center) {
		this.cargoFormula = cargo;
		this.left = left;
		this.center = center;
		this.right = right;
	}

	/**
	 * Returns the center child node of parent node.
	 *
	 * @return center child node
	 */
	public KaryTree getCenterChild() {
		return center;
	}

	/**
	 * Returns the name of a math formula as a string.
	 *
	 * @return name of math formula
	 */
	public String getFormula() {
		return cargoFormula;
	}

	/**
	 * Returns the left child node of parent node.
	 *
	 * @return left child node
	 */
	public KaryTree getLeftChild() {
		return left;
	}

	/**
	 * Returns the right child node of parent node.
	 *
	 * @return right child node
	 */
	public KaryTree getRightChild() {
		return right;
	}

	/**
	 * Returns true if node has no childs.
	 *
	 * @return true if node is leaf
	 */
	public boolean isBinaryLeaf() {
		if (left == null && right == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds two different nodes as childs of parent node.
	 *
	 * @param left is first new child node
	 * @param right is second new child node
	 */
	public void setBothChilds(KaryTree left, KaryTree right) {
		this.right = right;
		this.left = left;
	}

	/**
	 * Saves name of math formula into node.
	 *
	 * @param inFormula name of math formula
	 */
	public void setFormula(String inFormula) {
		cargoFormula = inFormula;
	}

	/**
	 * Adds node to the left side of parent node.
	 *
	 * @param left is new child node
	 */
	public void setLeftChild(KaryTree left) {
		this.left = left;
	}

	/**
	 * Adds node to the right side of parent node.
	 *
	 * @param right is new child node
	 */
	public void setRightChild(KaryTree right) {
		this.right = right;
	}

	/**
	 * Adds three different nodes as childs of parent node.
	 *
	 * @param left is first new child node
	 * @param center is third new child node
	 * @param right is second new child node
	 */
	public void setThreeChilds(KaryTree left, KaryTree center, KaryTree right) {
		this.right = right;
		this.center = center;
		this.left = left;
	}

}
