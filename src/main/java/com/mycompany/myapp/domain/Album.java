package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "event", length = 255)
    private String event;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "override_date")
    private Instant overrideDate;

    @Lob
    @Column(name = "thumbnail")
    private byte[] thumbnail;

    @Column(name = "thumbnail_content_type")
    private String thumbnailContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Album id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Album name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return this.event;
    }

    public Album event(String event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Album creationDate(Instant creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getOverrideDate() {
        return this.overrideDate;
    }

    public Album overrideDate(Instant overrideDate) {
        this.setOverrideDate(overrideDate);
        return this;
    }

    public void setOverrideDate(Instant overrideDate) {
        this.overrideDate = overrideDate;
    }

    public byte[] getThumbnail() {
        return this.thumbnail;
    }

    public Album thumbnail(byte[] thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailContentType() {
        return this.thumbnailContentType;
    }

    public Album thumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
        return this;
    }

    public void setThumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Album)) {
            return false;
        }
        return getId() != null && getId().equals(((Album) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", event='" + getEvent() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", overrideDate='" + getOverrideDate() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", thumbnailContentType='" + getThumbnailContentType() + "'" +
            "}";
    }
}
