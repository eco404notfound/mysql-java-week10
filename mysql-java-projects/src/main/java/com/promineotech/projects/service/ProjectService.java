package com.promineotech.projects.service;

import java.util.List;
import com.promineotech.projects.dao.ProjectDao;
import com.promineotech.projects.entity.Project;

public class ProjectService {
    private ProjectDao projectDao = new ProjectDao();

    public List<Project> fetchAllProjects() { // ✅ Correct method
        return projectDao.fetchAllProjects(); // Calls DAO method
    }

    public Project addProject(Project project) { // ✅ Fixed placement
        return projectDao.insertProject(project);  // Calls DAO to insert project
    }
 public Project fetchProjectById(int projectId) { // ✅ Fixed placement
        return projectDao.fetchProjectById(projectId)
                         .orElseThrow(() -> new IllegalArgumentException("❌ Project with ID " + projectId + " does not exist."));
    }
}
