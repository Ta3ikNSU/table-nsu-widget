package com.ooad2021.dao;

import com.ooad2021.domain.GroupFilter;
import com.ooad2021.domain.Subject;
import com.ooad2021.domain.SubjectType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PageLoader {

    public Map<String, Vector<Subject>> getTable(GroupFilter filter) throws IOException {
        Map<String, Vector<Subject>> table = initMap();
        String[] dayName = initDayArray();

        String URL = "https://table.nsu.ru/group/" + filter.getGroupNumber();
        Document tableDoc = Jsoup.connect(URL).get();
        Elements lines = tableDoc.select("tbody").get(1).children();

        int countLines = lines.size();
        for (int i = 1; i < countLines; i++) {
            Elements linesAttributes = lines.get(i).children();

            for (int j = 1; j < linesAttributes.size(); j++) {
                Subject newSubject;
                if (linesAttributes.get(j).children().size() == 0) {
                    newSubject = Subject.empty();
                } else {
                    Elements subjectInfo = linesAttributes.get(j).children().get(0).children();
                    SubjectType type = getSubjectType(subjectInfo);
                    String label = getLabel(subjectInfo);
                    String tutor = getTutor(subjectInfo);
                    String room = getRoom(subjectInfo);
                    newSubject = new Subject(label, room, tutor, type);
                }

                table.get(dayName[j - 1]).add(i - 1, newSubject);
            }
        }

        return table;
    }

    private Map<String, Vector<Subject>> initMap() {
        Map<String, Vector<Subject>> table = new HashMap<>();

        table.put("Monday", new Vector<>());
        table.put("Tuesday", new Vector<>());
        table.put("Wednesday", new Vector<>());
        table.put("Thursday", new Vector<>());
        table.put("Friday", new Vector<>());
        table.put("Saturday", new Vector<>());
        table.put("Sunday", new Vector<>());

        return table;
    }

    private String[] initDayArray() {
        String[] dayName = new String[7];

        dayName[0] = "Monday";
        dayName[1] = "Tuesday";
        dayName[2] = "Wednesday";
        dayName[3] = "Thursday";
        dayName[4] = "Friday";
        dayName[5] = "Saturday";
        dayName[6] = "Sunday";

        return dayName;
    }

    private String getLabel(Elements subjectInfo) {
        try {
            return subjectInfo.get(1).attributes().get("title");
        } catch (Exception exception){
            return "";
        }
    }

    private String getRoom(Elements subjectInfo) {
        try {
            return subjectInfo.get(2).children().textNodes().get(0).text();
        } catch (Exception exception){
            return "";
        }
    }

    private String getTutor(Elements subjectInfo) {
        try {
            return subjectInfo.get(3).textNodes().get(0).text();
        } catch (Exception exception){
            return "";
        }

    }

    private SubjectType getSubjectType(Elements subjectInfo) {
        return switch (subjectInfo.get(0).attributes().get("class")) {
            case "type lab" -> SubjectType.LABORATORY;
            case "type f_2" -> SubjectType.FACULTY;
            case "type lek" -> SubjectType.LECTURE;
            case "type pr" -> SubjectType.PRACTICE;
            default -> SubjectType.EMPTY;
        };
    }

}
