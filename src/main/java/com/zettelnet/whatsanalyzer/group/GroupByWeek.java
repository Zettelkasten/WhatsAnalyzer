package com.zettelnet.whatsanalyzer.group;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByWeek implements GroupCriteria<LocalDate> {

	@Override
	public LocalDate group(ChatMessage message) {
		return message.getTime().toLocalDate().with(DayOfWeek.MONDAY);
	}

	@Override
	public Collection<LocalDate> values(LocalDate min, LocalDate max) {
		Collection<LocalDate> values = new ArrayList<>();
		do {
			values.add(min);
			min = min.plusWeeks(1);
		} while (min.isBefore(max));
		values.add(max);
		return values;
	}
	
	@Override
	public String name(LocalDate value) {
		return value.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}
	
	@Override
	public String toString() {
		return "pro Woche";
	}
}
