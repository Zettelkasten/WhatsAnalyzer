package com.zettelnet.whatsanalyzer.group;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupBySecondOfDay implements GroupCriteria<LocalTime> {

	@Override
	public LocalTime group(ChatMessage message) {
		return message.getTime().toLocalTime().withNano(0);
	}

	@Override
	public Collection<LocalTime> values(LocalTime min, LocalTime max) {
		Collection<LocalTime> values = new ArrayList<>();
		do {
			values.add(min);
			min = min.plusSeconds(1);
		} while (min.isBefore(max));
		values.add(max);
		return values;
	}
	
	@Override
	public String name(LocalTime value) {
		return value.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
	}
	
	@Override
	public String toString() {
		return "pro Sekunde des Tages";
	}
}
