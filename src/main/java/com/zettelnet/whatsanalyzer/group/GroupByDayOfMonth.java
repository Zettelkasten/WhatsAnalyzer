package com.zettelnet.whatsanalyzer.group;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.zettelnet.whatsanalyzer.ChatMessage;

public class GroupByDayOfMonth implements GroupCriteria<Integer> {

	@Override
	public Integer group(ChatMessage message) {
		return message.getTime().getDayOfMonth();
	}

	@Override
	public Collection<Integer> values(Integer min, Integer max) {
		// always return all values
		return IntStream.range(1, 31 + 1).boxed().collect(Collectors.toList());
	}
}
