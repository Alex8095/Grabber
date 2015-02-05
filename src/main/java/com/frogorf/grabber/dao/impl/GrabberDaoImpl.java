package com.frogorf.grabber.dao.impl;

import com.frogorf.grabber.dao.GrabberDao;
import com.frogorf.grabber.domain.SourceSetting;
import com.frogorf.grabber.domain.Task;
import com.frogorf.grabber.domain.TaskHistory;
import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.realty.domain.RealtyOption;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("grabberDao")
public class GrabberDaoImpl implements GrabberDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Task> findTasks(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Task.class);
        if (params != null) {
            if (params.containsKey("code")) {
                criteria.createAlias("dictionary", "dictionary");
                criteria.add(Restrictions.eq("dictionary.code", params.get("code").toUpperCase()));
            }
        }
        return criteria.list();
    }

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
    public SourceSetting findSourceSettingById(int id) {
        return (SourceSetting) sessionFactory.getCurrentSession().get(SourceSetting.class, id);
    }

    @Override
    public void saveSourceSetting(SourceSetting sourceSetting) {
        sessionFactory.getCurrentSession().saveOrUpdate(sourceSetting);
    }

    @Override
    public void deleteSourceSetting(int id) {
        sessionFactory.getCurrentSession().delete(findSourceSettingById(id));
    }

    @Override
    public List<SourceSetting> findSourceSettings(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SourceSetting.class);
        if (params.containsKey(SourceSetting.SITE_CODE)) {
            criteria.add(Restrictions.eq(SourceSetting.SITE_CODE, params.get(SourceSetting.SITE_CODE).toUpperCase()));
        }
        if (params.containsKey(SourceSetting.NAME)) {
            criteria.add(Restrictions.eq(SourceSetting.NAME, params.get(SourceSetting.NAME)));
        }
        return criteria.list();
    }

    @Override
    public SourceSetting findSourceSetting(Map<String, String> params) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SourceSetting.class);
        if (params.containsKey(SourceSetting.SITE_CODE)) {
            criteria.add(Restrictions.eq(SourceSetting.SITE_CODE, params.get(SourceSetting.SITE_CODE).toUpperCase()));
        }
        if (params.containsKey(SourceSetting.NAME)) {
            criteria.add(Restrictions.eq(SourceSetting.NAME, params.get(SourceSetting.NAME)));
        }
        return (SourceSetting) criteria.uniqueResult();
    }

    @Override
    public DataSourceResult findSourceSettings(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), SourceSetting.class);
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
