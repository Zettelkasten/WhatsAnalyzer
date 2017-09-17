package com.zettelnet.whatsanalyzer.group;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collection;

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
}
