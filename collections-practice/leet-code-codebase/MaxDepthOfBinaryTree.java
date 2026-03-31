
public class MaxDepthOfBinaryTree {
	public static void main(String[] args) {
		TreeNode root =new TreeNode(1);
		root.left =new TreeNode(2);
		root.right =new TreeNode(3);
		root.left.left =new TreeNode(4);
		root.left.right =new TreeNode(5);

		int depth =maxDepth(root);
		System.out.println("Maximum depth of tree- "+depth);
	}

	public static int maxDepth(TreeNode root) {
		if (root==null) {
			return 0;
		}
		int left =maxDepth(root.left);
		int right = maxDepth(root.right);
		return Math.max(left, right) + 1;
	}
}

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode() {
	}

	TreeNode(int val) {
		this.val = val;
	}

	TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}
