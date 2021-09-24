package com.ooad2021.dao;

import com.ooad2021.domain.GroupFilter;
import com.ooad2021.domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class WidgetDaoImpl implements WidgetDao {

    @Autowired
    private PageLoader pageLoader;

    @Override
    public Map<String, Vector<Subject>> getData(GroupFilter filter) {
        try {
            return pageLoader.getTable(filter);
        } catch (IOException e) {
            return null;
        }
    }

}
