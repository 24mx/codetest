package com.pierceecom.dao;
import java.util.List;



import org.hibernate.Session;
import org.hibernate.Transaction;
import com.pierceecom.model.Post;

public class PostDAO {
	
	private Session currentSession;
    
    private Transaction currentTransaction;
	public PostDAO() {

	}
 
  
	   public Session openCurrentSession() {
	        currentSession = SessionFactoryHelper.getSessionFactory().openSession();
	        return currentSession;
	    }
	 
	    public Session openCurrentSessionwithTransaction() {
	        currentSession = SessionFactoryHelper.getSessionFactory().openSession();
	        currentTransaction = currentSession.beginTransaction();
	        return currentSession;
	    }
     
    public void closeCurrentSession() {
        currentSession.close();
    }
     
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
     
  
    public Session getCurrentSession() {
        return currentSession;
    }
 
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
 
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
 
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
 
    public void persist(Post entity) {
        getCurrentSession().save(entity);
    }
 
    public void update(Post entity) {
        getCurrentSession().update(entity);
    }
 
    public Post findById(String id) {
        Post post = (Post) getCurrentSession().get(Post.class, id);
        return post; 
    }
 
    public void delete(Post entity) {
        getCurrentSession().delete(entity);
    }
 
    @SuppressWarnings("unchecked")
    public List<Post> findAll() {
        List<Post> posts = (List<Post>) getCurrentSession().createQuery("from Post").list();
        return posts;
    }
 
    public void deleteAll() {
        List<Post> entityList = findAll();
        for (Post entity : entityList) {
            delete(entity);
        }
    }
}
