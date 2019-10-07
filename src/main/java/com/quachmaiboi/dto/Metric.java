package com.quachmaiboi.dto;

public class Metric {


    private String orgName;

    private String repoName;

    private Double healthScore;
    private Double numberCommit;
//    private Double numberRelease;
    private Long maxNumberCommit;



    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }


    public Double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Double healthScore) {
        this.healthScore = healthScore;
    }


    public Double getNumberCommit() {
        return numberCommit;
    }

    public void setNumberCommit(Double numberCommit) {
        this.numberCommit = numberCommit;
    }


//    public Double getNumberRelease() { return numberRelease; }
//
//    public void setNumberRelease(Double numberRelease) { this.numberRelease = numberRelease; }

    public Long getMaxNumberCommit() {
        return maxNumberCommit;
    }

    public void setMaxNumberCommit(Long maxNumberCommit) {
        this.maxNumberCommit = maxNumberCommit;
    }


}
