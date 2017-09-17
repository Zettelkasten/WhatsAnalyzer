package com.zettelnet.whatsanalyzer.value;

import java.time.LocalTime;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.ChatMessageType;

@FunctionalInterface
public interface ElementaryValue {

	public static ElementaryValue TOTAL_COUNT = (ChatMessage message) -> {
		return 1;
	};

	public static ElementaryValue MESSAGE_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.MESSAGE) ? 1 : 0;
	};

	public static ElementaryValue MEDIUM_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.MEDIUM) ? 1 : 0;
	};

	public static ElementaryValue LOCATION_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.LOCATION) ? 1 : 0;
	};

	public static ElementaryValue CHARACTER_COUNT = (ChatMessage message) -> {
		return message.getContent().length();
	};

	public static ElementaryValue MESSAGES_SENT_MIDNIGHT_COUNT = (ChatMessage message) -> {
		LocalTime time = message.getTime().toLocalTime();
		return (time.getHour() <= 6 && time.getHour() >= 0) ? 1 : 0;
	};

	public static ElementaryValue FML = (ChatMessage message) -> {
		return message.getContent().toLowerCase().contains("fml") ? 1 : 0;
	};

	double count(ChatMessage message);

}
