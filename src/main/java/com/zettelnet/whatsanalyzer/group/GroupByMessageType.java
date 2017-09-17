package com.zettelnet.whatsanalyzer.group;

import java.util.Arrays;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.ChatMessageType;

public class GroupByMessageType implements GroupCriteria<ChatMessageType> {

	@Override
	public ChatMessageType group(ChatMessage message) {
		return message.getType();
	}

	@Override
	public Collection<ChatMessageType> values(ChatMessageType min, ChatMessageType max) {
		return Arrays.asList(ChatMessageType.values());
	}
}
