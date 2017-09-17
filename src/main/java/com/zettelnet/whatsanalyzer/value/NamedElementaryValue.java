package com.zettelnet.whatsanalyzer.value;

import java.util.function.ToDoubleFunction;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class NamedElementaryValue implements ElementaryValue {

	private final String name;
	private final ToDoubleFunction<ChatMessage> function;
	
	public NamedElementaryValue(final String name, final ToDoubleFunction<ChatMessage> function) {
		this.name = name;
		this.function = function;
	}
	
	@Override
	public double count(ChatMessage message) {
		return function.applyAsDouble(message);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
