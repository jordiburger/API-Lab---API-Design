package de.berlin.htw.entity.dto;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import de.berlin.htw.lib.model.ProjectModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;

import de.berlin.htw.lib.model.ProjectModel;

/**
 * @author Jordi Burger
 */
@NamedQuery(name = "UserEntity.deleteById", query = "delete from UserEntity user where user.id = :id")
@Entity
@Table(name = "PROJECT")
public class ProjectEntity implements ProjectModel {

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    private String id;

    @Column(name = "NAME")
    private String name;

    @Email
    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id.toString();
    }

    @Override
    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void created() {

        final Date now = new Date();
        createdAt = now;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectEntity)) {
            return false;
        }
        final ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
