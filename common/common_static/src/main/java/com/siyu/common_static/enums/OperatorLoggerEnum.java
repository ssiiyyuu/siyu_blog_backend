package com.siyu.common_static.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OperatorLoggerEnum {
    DEFAULT("DEFAULT"), 

    EDIT_BLOG("编辑博客"), 
    
    DELETE_BLOG("删除博客"), 
    
    EDIT_TAG("编辑标签"),
     
    DELETE_TAG("删除标签"),
    
    EDIT_TYPE("编辑分类"),
    
    DELETE_TYPE("删除分类"),

    EDIT_USER("编辑用户"),
     
    DELETE_USER("删除用户"), 
    
    DELETE_COMMENT("删除评论"), 
    
    EXCEL("导出EXCEL")
	;

    private String behavior;

	public String getBehavior() {
		return this.behavior;
	}

}
