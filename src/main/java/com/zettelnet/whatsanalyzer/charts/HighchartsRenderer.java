package com.zettelnet.whatsanalyzer.charts;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zettelnet.whatsanalyzer.query.QueryColumn;
import com.zettelnet.whatsanalyzer.query.QueryTable;

public class HighchartsRenderer {

	protected static final Map<String, Object> STACKED = new HashMap<>();
	static {
		STACKED.put("stacking", "normal");
	}

	private final ChartType chartType;

	public HighchartsRenderer(final ChartType chartType) {
		this.chartType = chartType;
	}

	public <B> void printChart(PrintStream out, QueryColumn<B> result) {
		final Set<B> xCategories = result.getCategories();
		Object[] xCategoryNames = xCategories.stream().map(result.getGroupCriteria()::name).toArray();

		JSONObject data = new JSONObject();

		JSONObject chart = new JSONObject();
		chart.put("type", chartType.getGeneralType());
		chart.put("zoomType", "x");
		data.put("chart", chart);

		JSONObject title = new JSONObject();
		title.put("text", result.getElementaryValue());
		data.put("title", title);

		JSONObject subtitle = new JSONObject();
		subtitle.put("text", result.getGroupCriteria());
		data.put("subtitle", subtitle);

		JSONObject xAxis = new JSONObject();
		xAxis.put("categories", xCategoryNames);
		xAxis.put("type", "datetime");
		data.put("xAxis", xAxis);

		JSONObject yAxis = new JSONObject();
		JSONObject yAxisTitle = new JSONObject();
		yAxisTitle.put("text", result.getElementaryValue() + " " + result.getGroupCriteria());
		yAxis.put("title", yAxisTitle);
		data.put("yAxis", yAxis);

		JSONObject plotOptions = new JSONObject();
		plotOptions.put(chartType.getGeneralType(), chartType.getAdditionalProperties());
		data.put("plotOptions", plotOptions);

		JSONArray series = new JSONArray();

		JSONObject obj = new JSONObject();
		obj.put("name", result.getElementaryValue());
		obj.put("data", makeSeriesData(result));

		series.put(obj);

		data.put("series", series);

		printChart(out, data);
	}

	public <A, B> void printChart(PrintStream out, QueryTable<A, B> result) {
		final Set<B> xCategories = result.getSecondCategories();
		Object[] xCategoryNames = xCategories.stream().map(result.getSecondGroupCriteria()::name).toArray();

		final Set<A> seriesCategories = result.getFirstCategories();

		JSONObject data = new JSONObject();

		JSONObject chart = new JSONObject();
		chart.put("type", chartType.getGeneralType());
		chart.put("zoomType", "x");
		data.put("chart", chart);

		JSONObject title = new JSONObject();
		title.put("text", result.getElementaryValue());
		data.put("title", title);

		JSONObject subtitle = new JSONObject();
		subtitle.put("text", result.getFirstGroupCriteria() + " " + result.getSecondGroupCriteria());
		data.put("subtitle", subtitle);

		JSONObject xAxis = new JSONObject();
		xAxis.put("categories", xCategoryNames);
		xAxis.put("type", "datetime");
		data.put("xAxis", xAxis);

		JSONObject yAxis = new JSONObject();
		JSONObject yAxisTitle = new JSONObject();
		yAxisTitle.put("text", result.getElementaryValue() + " " + result.getSecondGroupCriteria());
		yAxis.put("title", yAxisTitle);
		data.put("yAxis", yAxis);

		JSONObject plotOptions = new JSONObject();
		plotOptions.put(chartType.getGeneralType(), chartType.getAdditionalProperties());
		data.put("plotOptions", plotOptions);

		JSONArray series = new JSONArray();

		for (A seriesCategory : seriesCategories) {
			JSONArray seriesData = makeSeriesData(result, seriesCategory);

			JSONObject obj = new JSONObject();
			obj.put("name", seriesCategory);
			obj.put("data", seriesData);

			series.put(obj);
		}

		data.put("series", series);

		printChart(out, data);
	}

	private void printChart(PrintStream out, JSONObject data) {
		out.print("<script src='https://code.jquery.com/jquery-3.1.1.min.js'></script>");
		out.print("<script src='https://code.highcharts.com/highcharts.js'></script>");
		out.print("<script src='https://code.highcharts.com/modules/exporting.js'></script>");
		out.print("<div id='container' style='min-width: 310px; height: 400px; margin: 0 auto'></div>");

		out.print("<script type='text/javascript'>");
		out.print("Highcharts.chart('container', ");

		out.print(data.toString(2));
		out.print(");");
		out.print("</script>");
		out.println();
	}

	private <B> JSONArray makeSeriesData(QueryColumn<B> result) {
		final Set<B> xCategories = result.getCategories();

		JSONArray seriesData = new JSONArray();
		for (B xCategory : xCategories) {
			JSONObject point = new JSONObject();
			point.put("y", result.get(xCategory));
			point.put("name", xCategory);
			seriesData.put(point);
		}
		return seriesData;
	}

	private <A, B> JSONArray makeSeriesData(QueryTable<A, B> result, A seriesCategory) {
		final Set<B> xCategories = result.getSecondCategories();

		JSONArray seriesData = new JSONArray();
		for (B xCategory : xCategories) {
			JSONObject point = new JSONObject();
			point.put("y", result.get(seriesCategory, xCategory));
			point.put("name", xCategory);
			seriesData.put(point);
		}
		return seriesData;
	}
}
