package com.mmk.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {  
	
	private String id;
	private String parentId;
	private String text;
	private List<TreeNode> children;
	
	
	public TreeNode(String parentId,String id,String text) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.children = new ArrayList<TreeNode>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return children.isEmpty();
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	/**
	 * 认领自己的孩子
	 * @param node
	 */
	public void addChildNode(TreeNode node){
		getChildren().add(node);
	} 
	
}  