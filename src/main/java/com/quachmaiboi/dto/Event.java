package com.quachmaiboi.dto;

import java.util.Date;

public class Event {
    long id;
    long numberCommit;
    Date createAt;

    public Event(long id, long numberCommit, Date createAt) {
        this.id = id;
        this.numberCommit = numberCommit;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberCommit() {
        return numberCommit;
    }

    public void setNumberCommit(long numberCommit) {
        this.numberCommit = numberCommit;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public int getCreateAtDay() {
        return (int) (createAt.getTime()/ (24 * 60 * 60 * 1000));
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
