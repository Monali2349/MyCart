//User related database operations
package com.mycompany.mycart.dao;

import com.mycompany.mycart.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDao {
        private SessionFactory factory;//for performing operations

    public UserDao(SessionFactory factory) {
        this.factory = factory;
    }
    
     //get user by email and password   
    public User getUserByEmailAndPassword(String email,String password)
    {
        User user=null;
        
        try {
                
            String query="from User where userEmail =: e and userPassword =: p";
            Session session = this.factory.openSession();
            Query q = session.createQuery(query);  //query is present in "q"
            q.setParameter("e", email);  //with the help of q,pass value of e
            q.setParameter("p", password);  //with the help of q,pass value of p
            user = (User) q.uniqueResult(); //return single object of user
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }   
}
