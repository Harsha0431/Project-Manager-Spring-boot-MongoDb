package com.model;


import com.Helpers.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MediaUploads {
    @NotBlank
    private String title;
    @NotBlank
    private String mediaLink;
    @NotNull
    private MediaType mediaType;

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(@NotBlank String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public @NotNull MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(@NotNull MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
