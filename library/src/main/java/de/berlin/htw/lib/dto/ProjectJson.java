package de.berlin.htw.lib.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.berlin.htw.lib.model.ProjectModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;


public class ProjectJson implements ProjectModel {

    @Null
    private String id;

    @JsonProperty("user_name")
    @Size(min = 3, max = 99, message = "The title must be between 3 and 99 characters")
    private String title;

    @JsonProperty("user_email")
    @Size(max = 255, message = "The description cannot be longer than 255 characters")
    @Email(message = "Email should be valid")
    private String description;

    @JsonProperty("registered_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate registeredDate;

    @JsonIgnore
    private Integer age;

    public ProjectJson() {
        // do nothing
    }

    public ProjectJson(ProjectModel project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(final String name) {
        this.title = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(final String email) {
        this.description = email;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(final LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

}
