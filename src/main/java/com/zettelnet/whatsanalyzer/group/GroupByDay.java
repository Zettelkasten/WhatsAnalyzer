package com.zettelnet.whatsanalyzer.group;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByDay implements GroupCriteria<LocalDate> {

	@Override
	public LocalDate group(ChatMessage message) {
		return message.getTime().toLocalDate();
	}

	@Override
	public Collection<LocalDate> values(LocalDate min, LocalDate max) {
		Collection<LocalDate> values = new ArrayList<>();
		do {
			values.add(min);
			min = min.plusDays(1);
		} while (min.isBefore(max));
		values.add(max);
		return values;
	}
}
