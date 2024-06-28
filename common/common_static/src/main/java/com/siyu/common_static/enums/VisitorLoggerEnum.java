package com.siyu.common_static.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VisitorLoggerEnum {
    DEFAULT("DEFAULT", "DEFAULT"),

	HOME("访问页面", "首页"),
	REPOSITORY("访问页面", "归档"),
	MAP("访问页面", "访客地图"),

	BLOG("查看博客", ""),
	TYPE("查看分类", ""),
	TAG("查看标签", ""),
	SEARCH("搜索博客", "")
	;

    private String behavior;
    private String content;

	public String getBehavior() {
		return this.behavior;
	}

	public String getContent() {
		return this.content;
	}

}
