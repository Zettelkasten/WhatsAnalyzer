package com.zettelnet.whatsanalyzer.group;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByDayOfWeek implements GroupCriteria<DayOfWeek> {

	@Override
	public DayOfWeek group(ChatMessage message) {
		return message.getTime().getDayOfWeek();
	}

	@Override
	public Collection<DayOfWeek> values(DayOfWeek min, DayOfWeek max) {
		// always return all values
		return Arrays.asList(DayOfWeek.values());
	}
	
	@Override
	public String name(DayOfWeek value) {
		return value.getDisplayName(TextStyle.FULL, Locale.getDefault());
	}
	
	@Override
	public String toString() {
		return "pro Wochentag";
	}
}
