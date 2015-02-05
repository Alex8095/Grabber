/**
 *
 */
package com.frogorf.grabber.service.impl;

import com.frogorf.grabber.dao.GrabberDao;
import com.frogorf.grabber.domain.SourceSetting;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.service.GrabberService;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Tsurkin Alex
 */
@Service("grabberService")
public class GrabberServiceImpl implements GrabberService {

    @Autowired
    private GrabberDao grabberDao;

    @Override
    @Transactional
    public Task findTaskById(int id) {
        return grabberDao.findTaskById(id);
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        grabberDao.saveTask(task);
    }

    @Override
    @Transactional
    public void deleteTask(int id) {
        grabberDao.deleteTask(id);
    }

    @Override
    @Transactional
    public List<Task> findTasks(Map<String, String> params) {
        return grabberDao.findTasks(params);
    }

    @Override
    @Transactional
    public DataSourceResult getListTask(DataSourceRequest request) {
        return grabberDao.getListTask(request);
    }

    @Override
    @Transactional
    public SourceSetting findSourceSettingById(int id) {
        return grabberDao.findSourceSettingById(id);
    }

    @Override
    @Transactional
    public void saveSourceSetting(SourceSetting sourceSetting) {
        grabberDao.saveSourceSetting(sourceSetting);
    }

    @Override
    @Transactional
    public void deleteSourceSetting(int id) {
        grabberDao.deleteSourceSetting(id);
    }

    @Override
    @Transactional
    public List<SourceSetting> findSourceSettings(Map<String, String> params) {
        return grabberDao.findSourceSettings(params);
    }

    @Override
    @Transactional
    public DataSourceResult findSourceSettings(DataSourceRequest request) {
        return grabberDao.findSourceSettings(request);
    }

    @Override
    public SourceSetting findSourceSetting(Map<String, String> params) {
        return grabberDao.findSourceSetting(params);
    }

    @Override
    @Transactional
    public TaskHistory findTaskHistoryById(int id) {
        return grabberDao.findTaskHistoryById(id);
    }

    @Override
    @Transactional
    public void saveTaskHistory(TaskHistory taskHistory) {
        grabberDao.saveTaskHistory(taskHistory);
    }

    @Override
    @Transactional
    public void deleteTaskHistory(int id) {
        grabberDao.deleteTaskHistory(id);
    }

    @Override
    @Transactional
    public DataSourceResult getListTaskHistory(DataSourceRequest request) {
        return grabberDao.getListTaskHistory(request);
    }
}
