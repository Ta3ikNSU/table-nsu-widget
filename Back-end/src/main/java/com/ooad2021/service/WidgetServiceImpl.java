package com.ooad2021.service;

import com.ooad2021.dao.WidgetDao;
import com.ooad2021.domain.GroupFilter;
import com.ooad2021.dto.WidgetData;
import org.springframework.beans.factory.annotation.Autowired;

public class WidgetServiceImpl implements WidgetService {

    @Autowired
    private WidgetDao widgetDao;

    @Autowired
    private WidgetDataMapper widgetDataMapper;

    @Override
    public WidgetData getWidgetData(GroupFilter filter) {
        return widgetDataMapper.mapToWidgetData(widgetDao.getData(filter));
    }

}
