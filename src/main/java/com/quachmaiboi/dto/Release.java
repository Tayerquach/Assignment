package com.quachmaiboi.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Release {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
