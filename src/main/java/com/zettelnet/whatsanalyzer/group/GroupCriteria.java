package com.zettelnet.whatsanalyzer.group;

import java.time.LocalDate;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;

public interface GroupCriteria<T> {

	public static final GroupCriteria<LocalDate> BY_DAY = new GroupByDay();
	public static final GroupCriteria<LocalDate> BY_MONTH = new GroupByMonth();
	public static final GroupCriteria<LocalDate> BY_YEAR = new GroupByYear();

	public static final GroupCriteria<String> BY_AUTHOR = new GroupByAuthor();

	T group(ChatMessage message);
	
	Collection<T> values(T min, T max);
}
