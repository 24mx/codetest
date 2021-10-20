package com.pierceecom.blog.repository;

import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.entity.PostEntity;
import com.pierceecom.blog.repository.mapper.PostMapper;

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

        PostEntity entity = PostMapper.map(post, new PostEntity());
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
        try {
            PostEntity entity = em.find(PostEntity.class, Long.valueOf(post.getId()));
            if (entity != null) {
                em.merge(PostMapper.map(post, entity));
                return true;
            }
            return false;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public List<Post> find(Integer postId) {

        PostEntity postEntity = em.find(PostEntity.class, Long.valueOf(postId.toString()));

        List<Post> result = new ArrayList<>();
        if (postEntity != null) {
            result.add(PostMapper.map(postEntity, new Post()));
        }

        return result;
    }

    public List<Post> findAll() {

        List<PostEntity> dbResult = em.createQuery("SELECT p FROM PostEntity p", PostEntity.class).getResultList();

        List<Post> result = new ArrayList<>();
        dbResult.forEach(postEntity ->
            result.add(PostMapper.map(postEntity, new Post())));

        return result;
    }
}
