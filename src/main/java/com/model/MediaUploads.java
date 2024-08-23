package com.model;


import com.Helpers.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MediaUploads {
    @NotNull
    private String title;
    @NotNull
    private String mediaLink;
    @NotBlank
    @NotNull
    private MediaType mediaType;

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public @NotNull String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(@NotNull String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public @NotBlank @NotNull MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(@NotBlank @NotNull MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
