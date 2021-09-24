package com.ooad2021.domain;

import lombok.Data;

@Data
public class Subject {
    private final String label;
    private final String room;
    private final String tutor;
    private final SubjectType type;

    public Subject(String label,  String room, String tutor, SubjectType type) {
        this.label = label;
        this.room = room;
        this.tutor = tutor;
        this.type = type;
    }

    public static Subject empty(){
        return new Subject("", "", "", SubjectType.EMPTY);
    }

}

