package com.zettelnet.whatsanalyzer.group;

import java.util.Collection;
import java.util.Collections;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByAuthor implements GroupCriteria<String> {

	@Override
	public String group(ChatMessage message) {
		return message.getAuthor();
	}

	@Override
	public Collection<String> values(String min, String max) {
		return Collections.emptyList();
	}
}
