package com.zettelnet.whatsanalyzer.query;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.group.GroupCriteria;
import com.zettelnet.whatsanalyzer.value.ElementaryValue;

public class QueryTable<A, B> implements QueryResult {

	private final ElementaryValue elementaryValue;

	private final GroupCriteria<A> firstCriteria;
	private final GroupCriteria<B> secondCriteria;

	private final SortedMap<A, SortedMap<B, List<ChatMessage>>> values;

	private boolean baked = true;

	public QueryTable(ElementaryValue elementaryValue, GroupCriteria<A> firstCriteria, GroupCriteria<B> secondCriteria) {
		this.elementaryValue = elementaryValue;

		this.firstCriteria = firstCriteria;
		this.secondCriteria = secondCriteria;

		this.values = new TreeMap<>();
	}

	public void insert(ChatMessage message) {
		A firstCategory = firstCriteria.group(message);
		B secondCategory = secondCriteria.group(message);

		if (!values.containsKey(firstCategory)) {
			values.put(firstCategory, new TreeMap<>());
		}

		Map<B, List<ChatMessage>> secondGrouped = values.get(firstCategory);
		if (!secondGrouped.containsKey(secondCategory)) {
			secondGrouped.put(secondCategory, new ArrayList<>());
		}
		secondGrouped.get(secondCategory).add(message);

		this.baked = false;
	}

	public void bake() {
		if (this.baked) {
			return;
		}

		for (A firstCategory : firstCriteria.values(values.firstKey(), values.lastKey())) {
			values.computeIfAbsent(firstCategory, (A c) -> new TreeMap<>());
		}

		// cannot join loops: values.keySet() might contain keys that
		// firstCriteria.values(first, last) doesn't!
		for (A firstCategory : values.keySet()) {
			SortedMap<B, List<ChatMessage>> secondValues = values.get(firstCategory);
			for (B secondCategory : secondCriteria.values(secondValues.firstKey(), secondValues.lastKey())) {
				secondValues.computeIfAbsent(secondCategory, (B c) -> new ArrayList<>());

			}
		}

		this.baked = true;
	}

	public Set<A> getFirstCategories() {
		bake();
		return values.keySet();
	}

	public Set<B> getSecondCategories() {
		bake();
		return values.get(values.firstKey()).keySet();
	}

	public List<ChatMessage> getMessages(A firstCategory, B secondCategory) {
		return values.getOrDefault(firstCategory, Collections.emptySortedMap()).getOrDefault(secondCategory, Collections.emptyList());
	}

	public double get(A firstCategory, B secondCategory) {
		return getMessages(firstCategory, secondCategory).stream().mapToDouble(elementaryValue::count).sum();
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
	public ElementaryValue getElementaryValue() {
		return elementaryValue;
	}

	public GroupCriteria<A> getFirstGroupCriteria() {
		return firstCriteria;
	}

	public GroupCriteria<B> getSecondGroupCriteria() {
		return secondCriteria;
	}
}
