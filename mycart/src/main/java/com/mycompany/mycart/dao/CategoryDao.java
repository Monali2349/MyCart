//category related database operations
package com.mycompany.mycart.dao;

import com.mycompany.mycart.entities.Category;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CategoryDao {
    private SessionFactory factory;
    
    //If we make CategoryDao's object ,we have to pass factory
    //for assigning factory,we need constructor
    public CategoryDao(SessionFactory factory) {
        this.factory = factory;
    }
    
    //saves category to db
    public int saveCategory(Category cat)
    {
        Session session = this.factory.openSession();
        Transaction tx = session.beginTransaction();
        int catId=(int) session.save(cat);
        tx.commit();
        session.close();
        return catId;
    }
    //getting list of categories from database
    public List<Category> getCategories()
    {
        Session s = this.factory.openSession();
        Query query = s.createQuery("from Category");
        List<Category> list=query.list();
        return list;
    }
    
    public Category getCategoryById(int cid)
    {   Category cat=null;
        try {
            
            
            Session session = this.factory.openSession();
            cat = session.get(Category.class, cid);//fetching category from db
            session.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cat;
    }
    
}
