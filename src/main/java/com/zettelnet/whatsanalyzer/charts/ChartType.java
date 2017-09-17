package com.zettelnet.whatsanalyzer.charts;

import java.util.Collections;
import java.util.Map;

public enum ChartType {

	LINE("line"), COLUMN("column"), COLUMN_STACKED("column", HighchartsRenderer.STACKED), BAR("bar"), BAR_STACKED("bar", HighchartsRenderer.STACKED), PIE("pie");

	private final String generalType;
	private final Map<String, Object> additionalProperties;

	private ChartType(final String generalType) {
		this(generalType, Collections.emptyMap());
	}

	private ChartType(final String generalType, final Map<String, Object> additionalProperties) {
		this.generalType = generalType;
		this.additionalProperties = additionalProperties;
	}

	public String getGeneralType() {
		return generalType;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}
}
