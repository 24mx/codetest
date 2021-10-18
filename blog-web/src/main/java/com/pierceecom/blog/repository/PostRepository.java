package com.pierceecom.blog.repository;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.entity.PostEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class PostRepository {

    @PersistenceContext(unitName = "InMemDB")
    private EntityManager em;

    @Transactional(REQUIRED)
    public Long create(Post post) {

        PostEntity entity = new PostEntity();
        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());

        em.persist(entity);
        return entity.getId();
    }

    @Transactional(REQUIRED)
    public boolean delete(Integer postId) {
        PostEntity entity = em.find(PostEntity.class, Long.valueOf(postId.toString()));
        if (entity != null) {
            em.remove(entity);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(REQUIRED)
    public boolean update(Post post) {
        PostEntity entity = em.find(PostEntity.class, Long.valueOf(post.getId()));

        if (entity != null) {
            entity.setTitle(post.getTitle());
            entity.setContent(post.getContent());
            em.merge(entity);
            return true;
        }

        return false;
    }

    public List<Post> find(Integer postId) {
        String p = postId.toString();
        Long l = Long.valueOf(postId.toString());

        PostEntity postEntity = em.find(PostEntity.class, Long.valueOf(postId.toString()));

        List<Post> result = new ArrayList<>();
        if (postEntity != null) {
            Post post = new Post();
            post.setId(postEntity.getId().toString());
            post.setTitle(postEntity.getTitle());
            post.setContent(postEntity.getContent());
            result.add(post);
        }

        return result;
    }

    public List<Post> findAll() {
        List<PostEntity> dbResult = em.createQuery("SELECT p FROM PostEntity p", PostEntity.class).getResultList();

        List<Post> result = new ArrayList<>();
        dbResult.forEach(postEntity -> {
            Post post = new Post();
            post.setId(postEntity.getId().toString());
            post.setTitle(postEntity.getTitle());
            post.setContent(postEntity.getContent());
            result.add(post);
        });

        return result;
    }
}
