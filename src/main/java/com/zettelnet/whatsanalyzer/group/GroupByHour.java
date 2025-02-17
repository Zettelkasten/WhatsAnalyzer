package com.zettelnet.whatsanalyzer.group;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByHour implements GroupCriteria<LocalDateTime> {

	@Override
	public LocalDateTime group(ChatMessage message) {
		return message.getTime().withMinute(0).withSecond(0).withNano(0);
	}

	@Override
	public Collection<LocalDateTime> values(LocalDateTime min, LocalDateTime max) {
		Collection<LocalDateTime> values = new ArrayList<>();
		do {
			values.add(min);
			min = min.plusHours(1);
		} while (min.isBefore(max));
		values.add(max);
		return values;
	}
	
	@Override
	public String name(LocalDateTime value) {
		return value.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
	}
	
	@Override
	public String toString() {
		return "pro Stunde";
	}
}
