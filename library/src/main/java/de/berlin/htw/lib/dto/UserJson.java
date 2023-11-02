package de.berlin.htw.lib.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import de.berlin.htw.lib.model.UserModel;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
public class UserJson implements UserModel {

    @Null
    private String id;

    @JsonProperty("user_name")
    @Size(min = 3, max = 99, message = "The name must be between 3 and 99 characters")
    private String name;

    @JsonProperty("user_email")
    @Size(max = 255, message = "The email cannot be longer than 255 characters")
    @Email(message = "Email should be valid")
    private String email;

    @JsonProperty("registered_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate registeredDate;

    @JsonIgnore
    private Integer age;
    
    public UserJson() {
        // do nothing
    }

    public UserJson(UserModel user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(final LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

}
