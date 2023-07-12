package com.basic.basicjpa01;

import com.basic.basicjpa01.domain.Member;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Eaxm04JpqlPrePersist {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /**  준영속상태로 만들기
             *      1. em.detach(entity)
             *      2. em.clear()
             *        ?? 3. em.close()
             * */
            Member member = new Member();
            String name= "anakin";
//            String[] name = new String[10];
//            for (int i=0; i<10; i++) {
//                name[i]= "anakin"+i;
                member.setName(name);
                em.persist(member);
//            }

            tx.commit();
        }
        catch (JpaSystemException e) {
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        emf.close();

    }
}
