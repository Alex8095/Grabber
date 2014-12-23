package com.frogorf.grabber.domain;

import com.frogorf.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "task_setting")
public class TaskSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public TaskSetting() {
    }
}
