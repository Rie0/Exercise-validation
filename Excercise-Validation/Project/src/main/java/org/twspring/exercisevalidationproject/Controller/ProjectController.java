package org.twspring.exercisevalidationproject.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.exercisevalidationproject.ApiResponse.ApiResponse;
import org.twspring.exercisevalidationproject.Model.Project;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/project_tracker")
public class ProjectController {
    ArrayList<Project> projects = new ArrayList<>();

    //---------------------Get---------------------
    @GetMapping("get/projects")
    public ResponseEntity getProjects() {
        if (projects.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("404", "No projects found"));
        } else {
            return ResponseEntity.status(200).body(projects);
        }
    }

    @GetMapping("get/projects/by_title/{title}")
    public ResponseEntity getProjectsByTitle(@PathVariable String title) {
        ArrayList<Project> foundProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                foundProjects.add(project);
            }
        }
        if (foundProjects.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("404", "No projects found"));
        } else {
            return ResponseEntity.status(200).body(foundProjects);
        }
    }

    @GetMapping("get/projects/by_company/{company_name}")
    public ResponseEntity getProjectsByCompanyName(@PathVariable String company_name) {
        ArrayList<Project> foundProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompany_name().equals(company_name)) {
                foundProjects.add(project);
            }
        }
        if (foundProjects.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("404", "No projects found")) ;
        }else {
            return ResponseEntity.status(200).body(foundProjects);
        }
    }

    //---------------------Post---------------------
    @PostMapping("post/project")
    public ResponseEntity addProject(@Valid @RequestBody Project project, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        projects.add(project);
        return ResponseEntity.status(201).body(new ApiResponse("201", "Project created"));
    }

    //---------------------Put----------------------

    @PutMapping("/update/project/{id}")
    public ResponseEntity updateProject(@PathVariable String id, @Valid @RequestBody Project project, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for(Project p : projects) {
            if (p.getId().equals(id)) {
                projects.set(projects.indexOf(p), project);
                return ResponseEntity.status(200).body(new ApiResponse("200", "Project updated"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No project found"));
    }

    @PutMapping("update/project/status/{id}")
    public ResponseEntity updateProjectStatus(@PathVariable String id) {
        for(Project p : projects) {
            if (p.getId().equals(id)) {
                switch (p.getStatus()) {
                    case "Not Started":
                        p.setStatus("In Progress");
                        return ResponseEntity.status(200).body(new ApiResponse("200", "Project status updated to in progress"));
                    case "In Progress":
                        p.setStatus("Completed");
                        return ResponseEntity.status(200).body(new ApiResponse("200", "Project status updated to completed"));
                    case "Completed":
                        return ResponseEntity.status(400).body(new ApiResponse("400", "Invalid. Project status already completed"));
                }
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No project found"));
    }


    //---------------------Delete-------------------
    @DeleteMapping("/delete/project/{id}")
    public ResponseEntity deleteProject(@PathVariable String id) {
        for(Project p : projects) {
            if (p.getId().equals(id)) {
                projects.remove(p);
                return ResponseEntity.status(200).body(new ApiResponse("200", "Project deleted"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("404", "No project found")) ;

    }

}

