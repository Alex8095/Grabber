package com.frogorf.grabber.dao.impl;

import com.frogorf.grabber.dao.GrabberDao;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.grabber.domain.TaskSetting;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("grabberDao")
public class GrabberDaoImpl implements GrabberDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Task findTaskById(int id) {
        return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @Override
    public void saveTask(Task task) {
        sessionFactory.getCurrentSession().saveOrUpdate(task);
    }

    @Override
    public void deleteTask(int id) {
        sessionFactory.getCurrentSession().delete(findTaskById(id));
    }

    @Override
    public DataSourceResult getListTask(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), Task.class);
    }

    @Override
    public TaskSetting findTaskSettingById(int id) {
        return (TaskSetting) sessionFactory.getCurrentSession().get(TaskSetting.class, id);
    }

    @Override
    public void saveTaskSetting(TaskSetting taskSetting) {
        sessionFactory.getCurrentSession().saveOrUpdate(taskSetting);
    }

    @Override
    public void deleteTaskSetting(int id) {
        sessionFactory.getCurrentSession().delete(findTaskSettingById(id));
    }

    @Override
    public DataSourceResult getListTaskSetting(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), TaskSetting.class);
    }

    @Override
    public TaskHistory findTaskHistoryById(int id) {
        return (TaskHistory) sessionFactory.getCurrentSession().get(TaskHistory.class, id);
    }

    @Override
    public void saveTaskHistory(TaskHistory taskHistory) {
        sessionFactory.getCurrentSession().saveOrUpdate(taskHistory);
    }

    @Override
    public void deleteTaskHistory(int id) {
        sessionFactory.getCurrentSession().delete(findTaskHistoryById(id));
    }

    @Override
    public DataSourceResult getListTaskHistory(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), TaskHistory.class);
    }
}
