package com.zettelnet.whatsanalyzer.query;

import java.io.PrintStream;
import java.util.function.ToDoubleFunction;

import com.zettelnet.whatsanalyzer.ChatMessage;

public interface QueryResult {

	void print(PrintStream out);
	
	ToDoubleFunction<ChatMessage> getElementaryValue();
}
