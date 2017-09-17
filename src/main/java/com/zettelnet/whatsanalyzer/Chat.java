package com.zettelnet.whatsanalyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zettelnet.whatsanalyzer.group.GroupCriteria;
import com.zettelnet.whatsanalyzer.query.QueryColumn;
import com.zettelnet.whatsanalyzer.query.QueryTable;
import com.zettelnet.whatsanalyzer.value.ElementaryValue;

/**
 * Represents a list of {@link ChatMessage}s.
 * 
 * @author Zettelkasten
 *
 */
public class Chat implements Iterable<ChatMessage> {

	private final List<ChatMessage> messages;

	public Chat() {
		this.messages = new ArrayList<>(1024);
	}

	@Override
	public Iterator<ChatMessage> iterator() {
		return messages.iterator();
	}

	public ChatMessage getLastMessage() {
		if (messages.isEmpty()) {
			return null;
		} else {
			return messages.get(messages.size() - 1);
		}
	}

	public void appendMessage(ChatMessage message) {
		messages.add(message);
	}

	public <T> QueryColumn<T> query(ElementaryValue elementaryValue, GroupCriteria<T> criteria) {
		QueryColumn<T> result = new QueryColumn<>(elementaryValue, criteria);
		for (ChatMessage message : this) {
			result.insert(message);
		}
		return result;
	}

	public <A, B> QueryTable<A, B> query(ElementaryValue elementaryValue, GroupCriteria<A> firstCriteria, GroupCriteria<B> secondCriteria) {
		QueryTable<A, B> result = new QueryTable<>(elementaryValue, firstCriteria, secondCriteria);
		for (ChatMessage message : this) {
			result.insert(message);
		}
		return result;
	}
}
