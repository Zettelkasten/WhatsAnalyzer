package com.zettelnet.whatsanalyzer.query;

import java.io.PrintStream;

import com.zettelnet.whatsanalyzer.value.ElementaryValue;

public interface QueryResult {

	void print(PrintStream out);
	
	ElementaryValue getElementaryValue();
}
