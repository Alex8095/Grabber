package com.frogorf.grabber.domain;

import com.frogorf.core.domain.BaseEntity;
import com.frogorf.dictionary.domain.DictionaryValue;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_history")
public class TaskHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final int START = 7;
    public static final int IN_PROCESS = 8;
    public static final int CANCELED = 9;
    public static final int FAILED = 10;
    public static final int COMPLETE = 11;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private DictionaryValue state;
    @Column(name = "count_found")
    private int countFound;
    @Column(name = "count_new")
    private int countNew;
    @Column(name = "count_duplicated")
    private int countDuplicated;
    @Column(name = "count_update")
    private int countUpdate;
    @Column
    @Lob
    private String message;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DictionaryValue getState() {
        return state;
    }

    public void setState(DictionaryValue state) {
        this.state = state;
    }

    public int getCountFound() {
        return countFound;
    }

    public void setCountFound(int countFound) {
        this.countFound = countFound;
    }

    public int getCountNew() {
        return countNew;
    }

    public void setCountNew(int countNew) {
        this.countNew = countNew;
    }

    public int getCountDuplicated() {
        return countDuplicated;
    }

    public void setCountDuplicated(int countDuplicated) {
        this.countDuplicated = countDuplicated;
    }

    public int getCountUpdate() {
        return countUpdate;
    }

    public void setCountUpdate(int countUpdate) {
        this.countUpdate = countUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TaskHistory() {
    }
}
