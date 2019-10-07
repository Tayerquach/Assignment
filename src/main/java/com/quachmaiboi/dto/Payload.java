
package com.quachmaiboi.dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {

    @SerializedName("push_id")
    @Expose
    private Long pushId;
    @SerializedName("size")
    @Expose
    private Long size;
    @SerializedName("distinct_size")
    @Expose
    private Long distinctSize;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("commits")
    @Expose
    private List<Commit> commits = null;

    public Long getPushId() {
        return pushId;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(Long distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

}
