import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrlsm
 * 例子展示
 */
public class Demo {

    private static class InstanceHolder {
        private static final Demo INSTANCE = new Demo();
    }

    public static Demo getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 461. 汉明距离
     */
    public int hammingDistance(int x, int y) {
        int result = (x ^ y);
        int nums = 0;
        while (result != 0) {
            nums += result & 1;
            result = result >> 1;
        }
        return nums;
    }

    /**
     * 1. 两数之和
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 104. 二叉树的最大深度
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int pStart, int pEnd, int[] inorder, int iStart, int iEnd) {
        //递归的第一步：递归终止条件，避免死循环
        if (pStart > pEnd || iStart > iEnd) {
            return null;
        }
        //重建根节点
        TreeNode treeNode = new TreeNode(preorder[pStart]);
        //index找到根节点在中序遍历的位置
        int index = 0;
        while (inorder[iStart + index] != preorder[pStart]) {
            index++;
        }
        //重建左子树
        treeNode.left = buildTree(preorder, pStart + 1, pStart + index, inorder, iStart, iStart + index - 1);
        //重建右子树
        treeNode.right = buildTree(preorder, pStart + index + 1, pEnd, inorder, iStart + index + 1, iEnd);
        return treeNode;
    }
}
