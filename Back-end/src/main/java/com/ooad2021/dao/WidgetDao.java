package com.ooad2021.dao;

import com.ooad2021.domain.GroupFilter;
import com.ooad2021.domain.Subject;

import java.util.Map;
import java.util.Vector;

public interface WidgetDao {

    Map<String, Vector<Subject>> getData(GroupFilter filter);

}
