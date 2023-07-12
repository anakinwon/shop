package com.basic.basicjpa01;

import com.basic.basicjpa01.domain.Member;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Eaxm02JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        try {
            List<Member> result = em.createQuery("select m from Member as m ", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getId()+", "+ member.getName());
            }
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
