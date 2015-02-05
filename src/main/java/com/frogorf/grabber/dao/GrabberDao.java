/**
 *
 */
package com.frogorf.grabber.dao;

import com.frogorf.grabber.domain.SourceSetting;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;

import java.util.List;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
public interface GrabberDao {

    public List<Task> findTasks(Map<String,String> params);

    public Task findTaskById(int id);

    public void saveTask(Task task);

    public void deleteTask(int id);

    DataSourceResult getListTask(DataSourceRequest request);

    /*SourceSetting*/
    public SourceSetting findSourceSettingById(int id);

    public void saveSourceSetting(SourceSetting sourceSetting);

    public void deleteSourceSetting(int id);

    List<SourceSetting> findSourceSettings(Map<String, String> params);

    DataSourceResult findSourceSettings(DataSourceRequest request);

    SourceSetting findSourceSetting(Map<String, String> params);

    /*TaskHistory*/
    public TaskHistory findTaskHistoryById(int id);

    public void saveTaskHistory(TaskHistory taskHistory);

    public void deleteTaskHistory(int id);

    DataSourceResult getListTaskHistory(DataSourceRequest request);
}

