package com.zettelnet.whatsanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ChatParser {

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy, H:m");
	private static final String AUTHOR_PREFIX = " - ";
	private static final String AUTHOR_MESSAGE_SPLIT = ": ";

	public Chat parseChat(InputStream in) throws IOException, ParseException {
		return parseChat(in, Charset.forName("UTF-8"));
	}

	public Chat parseChat(InputStream in, Charset charset) throws IOException {
		final Chat chat = new Chat();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = null;
		while ((line = reader.readLine()) != null) {
			parseMessage(line, chat);
		}

		return chat;
	}

	public ChatMessage parseMessage(String line, Chat chat) {
		ParsePosition pos = new ParsePosition(0);
		LocalDateTime time;
		try {
			time = LocalDateTime.from(TIME_FORMAT.parse(line, pos));
		} catch (DateTimeParseException e) {
			return appendMessage(line, chat);
		}
		String nonTimeString = line.substring(pos.getIndex());
		if (!nonTimeString.startsWith(AUTHOR_PREFIX)) {
			return appendMessage(line, chat);
		}
		String[] authorAndMessage = nonTimeString.substring(AUTHOR_PREFIX.length()).split(AUTHOR_MESSAGE_SPLIT);
		if (authorAndMessage.length != 2) {
			return appendMessage(line, chat);
		}
		String author = authorAndMessage[0];
		String content = authorAndMessage[1];

		ChatMessage message = new ChatMessage(author, time, content);
		chat.appendMessage(message);
		
		return message;
	}

	private ChatMessage appendMessage(String line, Chat chat) {
		ChatMessage last = chat.getLastMessage();
		if (last != null) {
			last.append(line);
			return last;
		} else {
			System.err.println(String.format("Failing to parse message '%s'. Ignoring.", line));
			return null;
		}
	}
}
