package com.games.leaderboard.enums;

import org.springframework.data.domain.Sort;

public enum SortKey {
    DESC("DESC"),
    ASC("ASC");

    public String label;
    private SortKey(String label){
        this.label = label;
    }
}
