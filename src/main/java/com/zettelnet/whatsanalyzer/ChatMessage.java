package com.zettelnet.whatsanalyzer;

import java.time.LocalDateTime;

/**
 * Represents a chat message in a {@link Chat} sent at a given time by a given
 * author. Chat messages have a {@link ChatMessageType} and consist out of one
 * or multiple lines.
 * 
 * @author Zettelkasten
 *
 */
public class ChatMessage {

	private final String author;
	private final LocalDateTime time;

	private final ChatMessageType type;

	private String[] content;

	public ChatMessage(final String author, final LocalDateTime time, final ChatMessageType type, final String... content) {
		this.author = author;
		this.time = time;
		this.type = type;
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public ChatMessageType getType() {
		return type;
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

	/**
	 * Adds a line to this chat message.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void append(String line) {
		String[] oldContent = this.content;
		this.content = new String[oldContent.length + 1];
		System.arraycopy(oldContent, 0, this.content, 0, oldContent.length);
		this.content[oldContent.length] = line;
	}
}
