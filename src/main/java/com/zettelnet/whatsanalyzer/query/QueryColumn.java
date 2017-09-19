package com.zettelnet.whatsanalyzer.query;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.zettelnet.whatsanalyzer.ChatMessage;
import com.zettelnet.whatsanalyzer.group.GroupCriteria;
import com.zettelnet.whatsanalyzer.value.ElementaryValue;

public class QueryColumn<T> implements QueryResult {

	private final ElementaryValue elementaryValue;
	private final GroupCriteria<T> criteria;

	private final SortedMap<T, List<ChatMessage>> groupedData;

	private boolean baked = true;

	public QueryColumn(ElementaryValue elementaryValue, GroupCriteria<T> criteria) {
		this.criteria = criteria;
		this.elementaryValue = elementaryValue;
		this.groupedData = new TreeMap<>();
	}

	public void insert(ChatMessage message) {
		T category = criteria.group(message);
		if (!groupedData.containsKey(category)) {
			groupedData.put(category, new ArrayList<>());
		}
		groupedData.get(category).add(message);
		this.baked = false;
	}

	public void bake() {
		if (this.baked) {
			return;
		}

		for (T category : criteria.values(groupedData.firstKey(), groupedData.lastKey())) {
			groupedData.computeIfAbsent(category, (T c) -> new ArrayList<>());
		}

		this.baked = true;
	}

	public Set<T> getCategories() {
		bake();
		return groupedData.keySet();
	}

	public List<ChatMessage> getMessages(T category) {
		return groupedData.get(category);
	}

	public double get(T category) {
		return getMessages(category).stream().mapToDouble(elementaryValue::count).sum();
	}

	@Override
	public void print(PrintStream out) {
		bake();

		for (T category : getCategories()) {
			out.print(criteria.name(category));
			out.print('\t');
			out.print((int) get(category));
			out.println();
		}
	}
	
	@Override
	public ElementaryValue getElementaryValue() {
		return elementaryValue;
	}
	
	public GroupCriteria<T> getGroupCriteria() {
		return criteria;
	}
}
