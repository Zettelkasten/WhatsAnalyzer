package com.zettelnet.whatsanalyzer.group;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.ChatMessageType;

public interface GroupCriteria<T> {

	public static final GroupCriteria<LocalDateTime> BY_SECOND = new GroupBySecond();
	public static final GroupCriteria<LocalDateTime> BY_MINUTE = new GroupByMinute();
	public static final GroupCriteria<LocalDateTime> BY_HOUR = new GroupByHour();

	public static final GroupCriteria<LocalDate> BY_DAY = new GroupByDay();
	public static final GroupCriteria<LocalDate> BY_MONTH = new GroupByMonth();
	public static final GroupCriteria<LocalDate> BY_YEAR = new GroupByYear();

	public static final GroupCriteria<LocalTime> BY_SECOND_OF_DAY = new GroupBySecondOfDay();
	public static final GroupCriteria<LocalTime> BY_MINUTE_OF_DAY = new GroupByMinuteOfDay();
	public static final GroupCriteria<LocalTime> BY_HOUR_OF_DAY = new GroupByHourOfDay();

	public static final GroupCriteria<DayOfWeek> BY_DAY_OF_WEEK = new GroupByDayOfWeek();
	public static final GroupCriteria<Integer> BY_DAY_OF_MONTH = new GroupByDayOfMonth();
	public static final GroupCriteria<Integer> BY_DAY_OF_YEAR = new GroupByDayOfYear();

	public static final GroupCriteria<Month> BY_MONTH_OF_YEAR = new GroupByMonthOfYear();

	public static final GroupCriteria<String> BY_AUTHOR = new GroupByAuthor();

	public static final GroupCriteria<ChatMessageType> BY_MESSAGE_TYPE = new GroupByMessageType();

	T group(ChatMessage message);

	Collection<T> values(T min, T max);
	
	default String name(T value) {
		return String.valueOf(value);
	}
}
