package com.frogorf.grabber.service;

import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.domain.TaskSetting;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;

public interface GrabberService {

    public Task findTaskById(int id);

    public void saveTask(Task task);

    public void deleteTask(int id);

    public DataSourceResult getListTask(DataSourceRequest request);

    /*TaskSetting*/
    public TaskSetting findTaskSettingById(int id);

    public void saveTaskSetting(TaskSetting taskSetting);

    public void deleteTaskSetting(int id);

    DataSourceResult getListTaskSetting(DataSourceRequest request);

    /*TaskHistory*/
    public TaskHistory findTaskHistoryById(int id);

    public void saveTaskHistory(TaskHistory taskHistory);

    public void deleteTaskHistory(int id);

    DataSourceResult getListTaskHistory(DataSourceRequest request);
}
