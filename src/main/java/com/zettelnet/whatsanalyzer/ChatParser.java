package com.zettelnet.whatsanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a parser that takes raw WhatsApp message dump to generate a
 * {@link Chat} from it.
 * <p>
 * WhatsApp message dumps are generated by the "backup chat via e-mail" feature
 * of WhatsApp. Note that WhatsApp will only export the last 40.000 messages (or
 * 10.000 when attaching media). Multiple chat dumps can be concatenated.
 * 
 * @author Zettelkasten
 * @see https://faq.whatsapp.com/en/android/23756533
 *
 */
public class ChatParser {

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy, H:m");
	private static final String AUTHOR_PREFIX = " - ";
	private static final String AUTHOR_MESSAGE_SPLIT = ": ";

	public Chat parseChat(InputStream in) throws IOException {
		return parseChat(in, Charset.forName("UTF-8"));
	}

	/**
	 * Parses an entire chat history.
	 * <p>
	 * This will read all remaining lines of the given input stream.
	 * 
	 * @param in
	 *            the input stream to read
	 * @param charset
	 *            the charset of the stream
	 * @return a parsed chat
	 * @throws IOException
	 *             when reading from the given stream fails
	 */
	public Chat parseChat(InputStream in, Charset charset) throws IOException {
		final Chat chat = new Chat();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line = null;
		while ((line = reader.readLine()) != null) {
			parseMessage(line, chat);
		}

		return chat;
	}

	/**
	 * Parses one line of a chat history and appends it to the given
	 * {@link Chat}.
	 * 
	 * @param line
	 *            the line to parse
	 * @param chat
	 *            the chat this next line is part of
	 * @return a newly generated chat message if this line is a new message, or
	 *         the appended message if this line appends the last message of
	 *         this chat
	 */
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
		String[] authorAndMessage = nonTimeString.substring(AUTHOR_PREFIX.length()).split(AUTHOR_MESSAGE_SPLIT, 2);
		if (authorAndMessage.length != 2) {
			return appendMessage(line, chat);
		}
		String author = authorAndMessage[0];
		String content = authorAndMessage[1];

		ChatMessageType type = parseMessageType(content);
		ChatMessage message = new ChatMessage(author, time, type, content);
		chat.appendMessage(message);

		return message;
	}

	private ChatMessageType parseMessageType(String content) {
		if (content.equals("<Medien weggelassen>")) {
			return ChatMessageType.MEDIUM;
		} else if (content.startsWith("Standort: https://maps.google.com/?q=")) {
			return ChatMessageType.LOCATION;
		} else {
			return ChatMessageType.MESSAGE;
		}
	}

	private ChatMessage appendMessage(String line, Chat chat) {
		ChatMessage last = chat.getLastMessage();
		if (last != null) {
			last.append(line);
			return last;
		} else {
			System.err.println(String.format("Failed to parse message '%s'. Ignoring it.", line));
			return null;
		}
	}
}
