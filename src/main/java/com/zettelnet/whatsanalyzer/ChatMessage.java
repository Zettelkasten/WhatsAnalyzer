package com.zettelnet.whatsanalyzer;

import java.time.LocalDateTime;

public class ChatMessage {

	private final String author;
	private final LocalDateTime time;

	private String[] content;

	public ChatMessage(final String author, final LocalDateTime time, final String... content) {
		this.author = author;
		this.time = time;
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String[] getLines() {
		return content;
	}
	
	public String getContent() {
		return String.join("\n", content);
	}
	
	@Override
	public String toString() {
		return time + " - " + author + ": " + getContent();
	}

	public void append(String line) {
		String[] oldContent = this.content;
		this.content = new String[oldContent.length + 1];
		System.arraycopy(oldContent, 0, this.content, 0, oldContent.length);
		this.content[oldContent.length] = line;
	}
}
