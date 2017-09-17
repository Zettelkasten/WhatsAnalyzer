package com.zettelnet.whatsanalyzer.query;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.ToDoubleFunction;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.group.GroupCriteria;

public class QueryTable<A, B> implements QueryResult {

	private final ToDoubleFunction<ChatMessage> elementaryValue;

	private final GroupCriteria<A> firstCriteria;
	private final GroupCriteria<B> secondCriteria;

	private final SortedSet<A> firstCategories;
	private final SortedSet<B> secondCategories;

	private final SortedMap<A, SortedMap<B, List<ChatMessage>>> values;

	public QueryTable(ToDoubleFunction<ChatMessage> elementaryValue, GroupCriteria<A> firstCriteria, GroupCriteria<B> secondCriteria) {
		this.elementaryValue = elementaryValue;

		this.firstCriteria = firstCriteria;
		this.secondCriteria = secondCriteria;

		this.firstCategories = new TreeSet<>();
		this.secondCategories = new TreeSet<>();
		this.values = new TreeMap<>();
	}

	public void insert(ChatMessage message) {
		A firstCategory = firstCriteria.group(message);
		B secondCategory = secondCriteria.group(message);

		if (!values.containsKey(firstCategory)) {
			values.put(firstCategory, new TreeMap<>());
			firstCategories.add(firstCategory);

		}
		Map<B, List<ChatMessage>> secondGrouped = values.get(firstCategory);
		if (!secondGrouped.containsKey(secondCategory)) {
			secondGrouped.put(secondCategory, new ArrayList<>());
			secondCategories.add(secondCategory);
		}
		secondGrouped.get(secondCategory).add(message);
	}

	public Set<A> getFirstCategories() {
		return firstCategories;
	}

	public Set<B> getSecondCategories() {
		return secondCategories;
	}

	public List<ChatMessage> getMessages(A firstCategory, B secondCategory) {
		return values.getOrDefault(firstCategory, Collections.emptySortedMap()).getOrDefault(secondCategory, Collections.emptyList());
	}

	public double get(A firstCategory, B secondCategory) {
		return getMessages(firstCategory, secondCategory).stream().mapToDouble(elementaryValue).sum();
	}

	@Override
	public void print(PrintStream out) {
		// header
		out.print('\t');
		for (A firstCategory : getFirstCategories()) {
			out.print(firstCriteria.name(firstCategory));
			out.print('\t');
		}
		out.println();
		for (B secondCategory : getSecondCategories()) {
			out.print(secondCriteria.name(secondCategory));
			out.print('\t');
			for (A firstCategory : getFirstCategories()) {
				out.print((int) get(firstCategory, secondCategory));
				out.print('\t');
			}
			out.println();
		}
	}

	@Override
	public ToDoubleFunction<ChatMessage> getElementaryValue() {
		return elementaryValue;
	}

	public GroupCriteria<A> getFirstGroupCriteria() {
		return firstCriteria;
	}

	public GroupCriteria<B> getSecondGroupCriteria() {
		return secondCriteria;
	}
}
