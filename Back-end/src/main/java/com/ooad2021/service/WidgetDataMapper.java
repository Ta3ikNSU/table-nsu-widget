package com.ooad2021.service;

import com.ooad2021.domain.Subject;
import com.ooad2021.dto.WidgetData;

import java.util.Map;
import java.util.Vector;

public class WidgetDataMapper {

    public WidgetData mapToWidgetData(Map<String, Vector<Subject>> rawWidgetData) {
        return WidgetData
            .builder()
            .subjects(rawWidgetData)
            .build();
    }

}
