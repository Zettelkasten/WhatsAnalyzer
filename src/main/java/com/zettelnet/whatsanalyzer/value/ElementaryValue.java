package com.zettelnet.whatsanalyzer.value;

import java.time.LocalTime;
import java.util.function.ToDoubleFunction;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.ChatMessageType;

public class ElementaryValue {

	public static ToDoubleFunction<ChatMessage> MESSAGE_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.MESSAGE) ? 1 : 0;
	};
	
	public static ToDoubleFunction<ChatMessage> MEDIUM_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.MEDIUM) ? 1 : 0;
	};
	
	public static ToDoubleFunction<ChatMessage> LOCATION_COUNT = (ChatMessage message) -> {
		return (message.getType() == ChatMessageType.LOCATION) ? 1 : 0;
	};

	public static ToDoubleFunction<ChatMessage> CHARACTER_COUNT = (ChatMessage message) -> {
		return message.getContent().length();
	};

	public static ToDoubleFunction<ChatMessage> MESSAGES_SENT_MIDNIGHT_COUNT = (ChatMessage message) -> {
		LocalTime time = message.getTime().toLocalTime();
		return (time.getHour() <= 6 && time.getHour() >= 0) ? 1 : 0;
	};
	
	public static ToDoubleFunction<ChatMessage> FML = (ChatMessage message) -> {
		return message.getContent().toLowerCase().contains("fml") ? 1 : 0;
	};
}
