package com.ooad2021.service;

import com.ooad2021.domain.GroupFilter;
import com.ooad2021.dto.WidgetData;

public interface WidgetService {

    WidgetData getWidgetData(GroupFilter filter);

}
