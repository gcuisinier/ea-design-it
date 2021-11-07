package com.mauvaisetroupe.eadesignit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mauvaisetroupe.eadesignit.domain.enumeration.ApplicationType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ApplicationType type;

    @Column(name = "technology")
    private String technology;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Owner owner;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "application" }, allowSetters = true)
    private Set<ApplicationComponent> applicationsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Application id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Application name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Application description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApplicationType getType() {
        return this.type;
    }

    public Application type(ApplicationType type) {
        this.setType(type);
        return this;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public String getTechnology() {
        return this.technology;
    }

    public Application technology(String technology) {
        this.setTechnology(technology);
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getComment() {
        return this.comment;
    }

    public Application comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Application owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    public Set<ApplicationComponent> getApplicationsLists() {
        return this.applicationsLists;
    }

    public void setApplicationsLists(Set<ApplicationComponent> applicationComponents) {
        if (this.applicationsLists != null) {
            this.applicationsLists.forEach(i -> i.setApplication(null));
        }
        if (applicationComponents != null) {
            applicationComponents.forEach(i -> i.setApplication(this));
        }
        this.applicationsLists = applicationComponents;
    }

    public Application applicationsLists(Set<ApplicationComponent> applicationComponents) {
        this.setApplicationsLists(applicationComponents);
        return this;
    }

    public Application addApplicationsList(ApplicationComponent applicationComponent) {
        this.applicationsLists.add(applicationComponent);
        applicationComponent.setApplication(this);
        return this;
    }

    public Application removeApplicationsList(ApplicationComponent applicationComponent) {
        this.applicationsLists.remove(applicationComponent);
        applicationComponent.setApplication(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}