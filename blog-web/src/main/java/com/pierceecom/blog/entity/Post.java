package com.pierceecom.blog.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "BlogPost.findOne", query = "select bp from Post bp where bp.id = :id"),
        @NamedQuery(name = "BlogPost.getAll", query = "select bp from Post bp")
})
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
public class Post extends BaseEntity {

    @NotNull
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return id.equals(post.id);
    }
}
