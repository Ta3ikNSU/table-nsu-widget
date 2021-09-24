package com.ooad2021.controller;

import com.ooad2021.domain.GroupFilter;
import com.ooad2021.dto.WidgetData;
import com.ooad2021.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/widget")
@RequiredArgsConstructor
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping(value = "/student")
    public WidgetData getWidgetData(@ModelAttribute GroupFilter filter) {
        return widgetService.getWidgetData(filter);
    }

}
