package com.pierceecom.blog.dao;

import com.pierceecom.blog.entity.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@Transactional
public class PostDaoImpl implements PostDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Post post) {
        entityManager.persist(post);
    }

    @Override
    public void update(Post post) {
        entityManager.merge(post);
    }

    @Override
    public List<Post> findAll() {
        return entityManager.createNamedQuery("BlogPost.getAll", Post.class).getResultList();
    }

    @Override
    public Optional<Post> findById(String blogPostId) {
        try {
            Post post = entityManager.createNamedQuery("BlogPost.findOne", Post.class)
                    .setParameter("id", blogPostId)
                    .getSingleResult();

            return Optional.of(post);
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Post post) {
        entityManager.remove(entityManager.contains(post) ? post : entityManager.merge(post));
    }
}
