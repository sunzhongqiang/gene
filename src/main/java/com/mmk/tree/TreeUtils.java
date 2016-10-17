package com.mmk.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;  
  
public class TreeUtils {  
    /**
     * 将list转换为tree
     * @param list
     * @return
     */
	public static TreeNode changeToTree(List<TreeNode> list){
		if(list == null || list.isEmpty()){
			TreeNode root = new TreeNode("root","1","根目录");
			return root;
		}
		HashMap<String,TreeNode> nodeMap = putNodeInMap(list);  
		nodeMap = parentAndChildren(nodeMap);
		return findRoot(nodeMap);
	}  
	
	

	/**
	 * 如果是多条tree，合并为一条tree返回
	 * @param nodeMap 
	 * @return 一个tree
	 */
	private static TreeNode findRoot(HashMap<String, TreeNode> nodeMap) {
		Iterator<TreeNode> roots = nodeMap.values().iterator(); //所有的都为根
		TreeNode root = null;
		if(roots.hasNext()){ //获得根，追根溯源
			root = roots.next();
			while(true){
				TreeNode node = nodeMap.get(root.getParentId());
				if(node==null||node.getId()==root.getId()){
					break;
				}
				root = node;
			}
		}
		return root;
	}






	/**
	 * 把node放到map中，方便处理
	 * @param list
	 * @return
	 */
	
	private static HashMap<String,TreeNode> putNodeInMap(List<TreeNode> list){
		 HashMap<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();  
	        Iterator<TreeNode> it = list.iterator();  
	        while (it.hasNext()) {  
	            TreeNode treeNode = (TreeNode) it.next();  
	            nodeMap.put(treeNode.getId(), treeNode);  
	        }  
	        return nodeMap;  
	}
	/**
	 * 确定父子关系
	 * @param nodeMap
	 * @return
	 */
	private static HashMap<String,TreeNode> parentAndChildren(HashMap<String,TreeNode> nodeMap) {
		Iterator<TreeNode> it = nodeMap.values().iterator();  
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();  
            String parentId = treeNode.getParentId();  
            if (nodeMap.containsKey(parentId)) {  
                TreeNode parentNode = nodeMap.get(parentId);  
                if (parentNode == null) {  
                    return null;  
                } else {  
                   if(!treeNode.getId().equals("0")){
                	   parentNode.addChildNode(treeNode);
                   }
                }  
            }  
        }
        return nodeMap;
	}
}  
