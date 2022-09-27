package com.fkmalls.meidusha.meidusha.constants.enums;


import java.util.Objects;
import java.util.stream.Stream;


/**
 * @author: djm
 * @date: 2022年06月23日 19:01
 */
public enum XmindMarkers {
    //枚举

    CASE_TITLE("task-done","title")
    ;

    private String markers;
    private String message;

    XmindMarkers(String markers, String message) {
        this.markers=markers;
        this.message=message;
    }

    public String getMarkersId() {
        return null;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessageByMarkers(String markers) {
        return Stream.of(XmindMarkers.values()).filter(e -> Objects.equals(markers, e.getMarkersId())).findFirst().get().getMessage();
    }

    public static XmindMarkers parseByMarkers(String markers) {
        return Stream.of(XmindMarkers.values()).filter(e -> Objects.equals(markers, e.getMarkersId())).findFirst().get();
    }
}
