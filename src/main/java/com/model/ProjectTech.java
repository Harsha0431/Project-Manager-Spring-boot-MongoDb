package com.model;

import jakarta.validation.constraints.NotNull;

public class ProjectTech {
    @NotNull
    private String title;

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }
}
