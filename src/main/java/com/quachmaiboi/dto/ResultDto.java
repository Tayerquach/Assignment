package com.quachmaiboi.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class ResultDto {
    private Long id;

    @SerializedName("name")
    @Expose
    private String repoName;

    private String url;

    private Long orgId;

    private String orgName;

    private long totalCommits;

    private long releaseNumber;

    private long maxNumberCommit;

    private List<Event> pushEvents;

    public List<Event> getPushEvents() {
        return pushEvents;
    }

    public void setPushEvents(List<Event> pushEvents) {
        this.pushEvents = pushEvents;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public long getTotalCommits() {
        return totalCommits;
    }

    public void setTotalCommits(long totalCommits) {
        this.totalCommits = totalCommits;
    }


    public long getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(long releaseNumber) {
        this.releaseNumber = releaseNumber;
    }


    public long getMaxNumberCommit() {return maxNumberCommit;}

    public void setMaxNumberCommit(long maxNumberCommit) {
        this.maxNumberCommit = maxNumberCommit;
    }


}
