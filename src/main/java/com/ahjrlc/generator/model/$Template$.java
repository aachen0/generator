package com.ahjrlc.generator.model;

import java.io.Serializable;

public class $Template$ implements Serializable {
    private Object $Key$;

    private String $searchField$;

    private static final long serialVersionUID = 1L;

    public Object get$Key$() {
        return $Key$;
    }

    public void set$Key$(Object $Key$) {
        this.$Key$ = $Key$;
    }

    public String get$searchField$() {
        return $searchField$;
    }

    public void set$searchField$(String $searchField$) {
        this.$searchField$ = $searchField$ == null ? null : $searchField$.trim();
    }
}