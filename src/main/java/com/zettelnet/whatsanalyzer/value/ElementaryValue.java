package com.zettelnet.whatsanalyzer.value;

import java.time.LocalTime;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.ChatMessageType;

@FunctionalInterface
public interface ElementaryValue {

	public static ElementaryValue TOTAL_COUNT = new NamedElementaryValue("Versandte Nachrichten",
			message -> 1);

	public static ElementaryValue MESSAGE_COUNT = new NamedElementaryValue("Versandte Nachrichten",
			message -> (message.getType() == ChatMessageType.MESSAGE) ? 1 : 0);

	public static ElementaryValue MEDIUM_COUNT = new NamedElementaryValue("Versandte Medien",
			message -> (message.getType() == ChatMessageType.MEDIUM) ? 1 : 0);

	public static ElementaryValue LOCATION_COUNT = new NamedElementaryValue("Versandte Standorte",
			message -> (message.getType() == ChatMessageType.LOCATION) ? 1 : 0);

	public static ElementaryValue CHARACTER_COUNT = new NamedElementaryValue("Versandte Zeichen",
			message -> message.getContent().length());

	public static ElementaryValue MESSAGES_SENT_MIDNIGHT_COUNT = (ChatMessage message) -> {
		LocalTime time = message.getTime().toLocalTime();
		return (time.getHour() <= 6 && time.getHour() >= 0) ? 1 : 0;
	};

	public static ElementaryValue FML = (ChatMessage message) -> {
		return message.getContent().toLowerCase().contains("fml") ? 1 : 0;
	};

	double count(ChatMessage message);

}
