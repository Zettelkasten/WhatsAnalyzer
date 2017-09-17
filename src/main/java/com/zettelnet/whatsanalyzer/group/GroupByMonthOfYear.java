package com.zettelnet.whatsanalyzer.group;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByMonthOfYear implements GroupCriteria<Month> {

	@Override
	public Month group(ChatMessage message) {
		return message.getTime().getMonth();
	}

	@Override
	public Collection<Month> values(Month min, Month max) {
		// always return all values
		return Arrays.asList(Month.values());
	}
	
	@Override
	public String name(Month value) {
		return value.getDisplayName(TextStyle.FULL, Locale.getDefault());
	}
	
	@Override
	public String toString() {
		return "pro Monat";
	}
}
