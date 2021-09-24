package com.ooad2021.configuration;

import com.ooad2021.controller.WidgetController;
import com.ooad2021.dao.PageLoader;
import com.ooad2021.dao.WidgetDao;
import com.ooad2021.dao.WidgetDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ooad2021.service.WidgetDataMapper;
import com.ooad2021.service.WidgetService;
import com.ooad2021.service.WidgetServiceImpl;

@Configuration
public class WidgetConfiguration {

    @Bean
    public WidgetController widgetController(WidgetService widgetService) {
        return new WidgetController(widgetService);
    }

    @Bean
    public WidgetService widgetService() {
        return new WidgetServiceImpl();
    }

    @Bean
    public WidgetDao widgetDao() {
        return new WidgetDaoImpl();
    }

    @Bean
    public WidgetDataMapper widgetDataMapper() {
        return new WidgetDataMapper();
    }

    @Bean
    public PageLoader pageLoader() {
        return new PageLoader();
    }

}
