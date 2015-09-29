package com.turvo.rules.knowledgebase.model;

public class KnowledgeContent {
	private String contentId;

	private byte[] content;

	public KnowledgeContent(String contentId, byte[] content) {
		super();
		this.contentId = contentId;
		this.content = content;
	}

	public String getContentId() {
		return contentId;
	}

	public byte[] getContent() {
		return content;
	}
}
