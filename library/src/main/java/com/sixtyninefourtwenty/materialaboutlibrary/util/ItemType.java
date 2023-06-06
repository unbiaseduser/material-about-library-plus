package com.sixtyninefourtwenty.materialaboutlibrary.util;

import java.util.List;

public enum ItemType {
    ACTION, TITLE, CHECKBOX, SWITCH, ACTION_CHECKBOX, ACTION_SWITCH;

    public static final List<ItemType> VALUES = List.of(values());
}
