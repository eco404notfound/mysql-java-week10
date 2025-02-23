package com.promineotech.projects;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import com.promineotech.projects.entity.Project;
import com.promineotech.projects.service.ProjectService;
import java.math.BigDecimal;

public class ProjectsApp {
    private Scanner scanner = new Scanner(System.in);
    private ProjectService projectService = new ProjectService();
    private Project curProject; // ✅ Added instance variable for selected project

    private List<String> operations = List.of(
        "1) Add a project",
        "2) List all projects",
        "3) Select a project"
    );

    public static void main(String[] args) {
        new ProjectsApp().processUserSelections();
    }

    private void processUserSelections() {
        boolean done = false;

        while (!done) {
            try {
                int selection = getUserSelection();
                switch (selection) {
                    case -1:
                        done = exitMenu();
                        break;
                    case 1:
                        createProject();
                        break;
                    case 2:
                        listProjects();
                        break;
                    case 3:
                        selectProject();
                        break;
                    default:
                        System.out.println("\nInvalid selection. Try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e + " Try again.");
                e.printStackTrace();
            }
        }
    }

    private void createProject() {
        String projectName = getStringInput("Enter the project name");
        BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
        BigDecimal actualHours = getDecimalInput("Enter the actual hours");
        Integer difficulty = getIntInput("Enter project difficulty (1-5)");
        String notes = getStringInput("Enter project notes");

        Project project = new Project();
        project.setProjectName(projectName);
        project.setEstimatedHours(estimatedHours);
        project.setActualHours(actualHours);
        project.setDifficulty(difficulty);
        project.setNotes(notes);

        Project dbProject = projectService.addProject(project);
        System.out.println(" Project created: " + dbProject); 
    }

    private void listProjects() {
        List<Project> projects = projectService.fetchAllProjects();
        System.out.println("\nProjects:");
        projects.forEach(project -> System.out.println("  " + project.getProjectId() + ": " + project.getProjectName()));
    }

    private void selectProject() {
        listProjects();
        
        System.out.print("Enter project ID to select: ");
        try {
            int projectId = Integer.parseInt(scanner.nextLine()); // ✅ Fix for nextInt() issue
            
            curProject = null;
            try {
                curProject = projectService.fetchProjectById(projectId);
                System.out.println(" Project selected: " + curProject); 
                System.out.println("\nProject Details:");
                System.out.println("Name: " + curProject.getProjectName());
                System.out.println("Estimated Hours: " + curProject.getEstimatedHours());
                System.out.println("Actual Hours: " + curProject.getActualHours());
                System.out.println("Difficulty: " + curProject.getDifficulty());
                System.out.println("Notes: " + curProject.getNotes());
                System.out.println("Categories: " + curProject.getCategories());
                System.out.println("Materials: " + curProject.getMaterials());
                System.out.println("Steps: " + curProject.getSteps());
            } catch (Exception e) {
                System.out.println(" Invalid project ID selected."); 
            }
        } catch (NumberFormatException e) {
            System.out.println(" Invalid input. Please enter a valid numeric project ID."); 
        }
    }

    private boolean exitMenu() {
        System.out.println("Exiting...");
        return true;
    }

    private int getUserSelection() {
        printOperations();
        Integer input = getIntInput("Enter a menu selection");
        return Objects.isNull(input) ? -1 : input;
    }

    private void printOperations() {
        System.out.println("\nAvailable Options:");
        operations.forEach(System.out::println);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();
        return input.isBlank() ? null : input.trim();
    }

    private Integer getIntInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : Integer.valueOf(input);
    }

    private BigDecimal getDecimalInput(String prompt) {
        String input = getStringInput(prompt);
        return Objects.isNull(input) ? null : new BigDecimal(input).setScale(2);
    }
}
