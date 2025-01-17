package com.mauvaisetroupe.eadesignit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;

/**
 * A FunctionalFlow.
 */
@Entity
@Table(name = "flow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FunctionalFlow implements Serializable, Comparable<FunctionalFlow> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "alias", unique = true)
    private String alias;

    @Size(max = 1500)
    @Column(name = "description", length = 1500)
    private String description;

    @Size(max = 1000)
    @Column(name = "jhi_comment", length = 1000)
    private String comment;

    @Column(name = "status")
    private String status;

    @Size(max = 500)
    @Column(name = "documentation_url", length = 500)
    private String documentationURL;

    @Size(max = 500)
    @Column(name = "documentation_url_2", length = 500)
    private String documentationURL2;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "flow", fetch = FetchType.EAGER)
    @SortNatural
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "flow" }, allowSetters = true)
    private SortedSet<FunctionalFlowStep> steps = new TreeSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private Owner owner;

    @ManyToMany(mappedBy = "flows", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "flows", "capabilityApplicationMappings" }, allowSetters = true)
    private Set<LandscapeView> landscapes = new HashSet<>();

    @ManyToMany(mappedBy = "functionalFlows")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "events", "functionalFlows", "flowInterface" }, allowSetters = true)
    private Set<DataFlow> dataFlows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FunctionalFlow id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return this.alias;
    }

    public FunctionalFlow alias(String alias) {
        this.setAlias(alias);
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return this.description;
    }

    public FunctionalFlow description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return this.comment;
    }

    public FunctionalFlow comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return this.status;
    }

    public FunctionalFlow status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentationURL() {
        return this.documentationURL;
    }

    public FunctionalFlow documentationURL(String documentationURL) {
        this.setDocumentationURL(documentationURL);
        return this;
    }

    public void setDocumentationURL(String documentationURL) {
        this.documentationURL = documentationURL;
    }

    public String getDocumentationURL2() {
        return this.documentationURL2;
    }

    public FunctionalFlow documentationURL2(String documentationURL2) {
        this.setDocumentationURL2(documentationURL2);
        return this;
    }

    public void setDocumentationURL2(String documentationURL2) {
        this.documentationURL2 = documentationURL2;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public FunctionalFlow startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public FunctionalFlow endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<FunctionalFlowStep> getSteps() {
        return this.steps;
    }

    public void setSteps(SortedSet<FunctionalFlowStep> functionalFlowSteps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setFlow(null));
        }
        if (functionalFlowSteps != null) {
            functionalFlowSteps.forEach(i -> i.setFlow(this));
        }
        this.steps = functionalFlowSteps;
    }

    public FunctionalFlow steps(SortedSet<FunctionalFlowStep> functionalFlowSteps) {
        this.setSteps(functionalFlowSteps);
        return this;
    }

    public FunctionalFlow addSteps(FunctionalFlowStep functionalFlowStep) {
        this.steps.add(functionalFlowStep);
        functionalFlowStep.setFlow(this);
        return this;
    }

    public FunctionalFlow removeSteps(FunctionalFlowStep functionalFlowStep) {
        if (this.steps.contains(functionalFlowStep)) {
            this.steps.remove(functionalFlowStep);
        } else {
            // hibernate bug due to hashcode ?
            for (Iterator<FunctionalFlowStep> iterator = this.steps.iterator(); iterator.hasNext();) {
                FunctionalFlowStep step = iterator.next();
                if (step.getId() != null && step.getId().equals(functionalFlowStep.getId())) {
                    iterator.remove();
                }
            }
        }
        functionalFlowStep.setFlow(null);
        return this;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public FunctionalFlow owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    public Set<LandscapeView> getLandscapes() {
        return this.landscapes;
    }

    public void setLandscapes(Set<LandscapeView> landscapeViews) {
        if (this.landscapes != null) {
            this.landscapes.forEach(i -> i.removeFlows(this));
        }
        if (landscapeViews != null) {
            landscapeViews.forEach(i -> i.addFlows(this));
        }
        this.landscapes = landscapeViews;
    }

    public FunctionalFlow landscapes(Set<LandscapeView> landscapeViews) {
        this.setLandscapes(landscapeViews);
        return this;
    }

    public FunctionalFlow addLandscape(LandscapeView landscapeView) {
        this.landscapes.add(landscapeView);
        landscapeView.getFlows().add(this);
        return this;
    }

    public FunctionalFlow removeLandscape(LandscapeView landscapeView) {
        this.landscapes.remove(landscapeView);
        landscapeView.getFlows().remove(this);
        return this;
    }

    public Set<DataFlow> getDataFlows() {
        return this.dataFlows;
    }

    public void setDataFlows(Set<DataFlow> dataFlows) {
        if (this.dataFlows != null) {
            this.dataFlows.forEach(i -> i.removeFunctionalFlows(this));
        }
        if (dataFlows != null) {
            dataFlows.forEach(i -> i.addFunctionalFlows(this));
        }
        this.dataFlows = dataFlows;
    }

    public FunctionalFlow dataFlows(Set<DataFlow> dataFlows) {
        this.setDataFlows(dataFlows);
        return this;
    }

    public FunctionalFlow addDataFlows(DataFlow dataFlow) {
        this.dataFlows.add(dataFlow);
        dataFlow.getFunctionalFlows().add(this);
        return this;
    }

    public FunctionalFlow removeDataFlows(DataFlow dataFlow) {
        if (this.dataFlows.contains(dataFlow)) {
            this.dataFlows.remove(dataFlow);
        } else {
            // hibernate bug due to hashcode ?
            for (Iterator<DataFlow> iterator = this.dataFlows.iterator(); iterator.hasNext();) {
                DataFlow _dataflow = iterator.next();
                if (_dataflow.getId() != null && _dataflow.getId().equals(dataFlow.getId())) {
                    iterator.remove();
                }
            }
        }

        if (dataFlow.getFunctionalFlows().contains(this)) {
            dataFlow.getFunctionalFlows().remove(this);
        } else {
            // hibernate bug due to hashcode ?
            for (Iterator<FunctionalFlow> iterator = dataFlow.getFunctionalFlows().iterator(); iterator.hasNext();) {
                FunctionalFlow _flow = iterator.next();
                if (_flow.getId() != null && _flow.getId().equals(this.getId())) {
                    iterator.remove();
                }
            }
        }

        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FunctionalFlow)) {
            return false;
        }
        return id != null && id.equals(((FunctionalFlow) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FunctionalFlow{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", description='" + getDescription() + "'" +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", documentationURL='" + getDocumentationURL() + "'" +
            ", documentationURL2='" + getDocumentationURL2() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }

    @Override
    public int compareTo(FunctionalFlow arg0) {
        int result = -1;
        if (arg0 == null) {
            result = -1;
        }
        // compare alias is one is not null
        else if (this.alias != null || arg0.alias != null) {
            result = ObjectUtils.compare(this.alias, arg0.alias, true);
        }
        // compare id is one is not null
        else if (this.id != null || arg0.id != null) {
            result = ObjectUtils.compare(this.id, arg0.id, true);
        } else {
            // alias and id are both null
            result = 0;
        }
        return result;
    }

    @JsonIgnore
    public SortedSet<FlowInterface> getInterfaces() {
        SortedSet<FlowInterface> interfaces = new TreeSet<>();
        for (FunctionalFlowStep step : this.steps) {
            interfaces.add(step.getFlowInterface());
        }
        return interfaces;
    }
}
