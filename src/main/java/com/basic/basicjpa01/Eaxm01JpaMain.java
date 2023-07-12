package com.basic.basicjpa01;

import com.basic.basicjpa01.domain.Member;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Eaxm01JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
//            member.setId(4L);
            member.setName("요다");
            /** 1. 저장 기능 */
            em.persist(member);
            em.flush();
            em.clear();
//            member.setId(5L);
            member.setName("듀크");
            /** 1. Insert 하기 */
            em.persist(member);

            /** 2. Select 하기  */
            Member findMember = em.find(Member.class, 2L);

            /** 3. Update 하기  */
            findMember.setName("아름다은 파드메");

            /** 4. Delete <- select 하기  */
            Member deleteMember = em.find(Member.class, 3L);
            em.remove(deleteMember);

            tx.commit();
        }
        catch (JpaSystemException e) {
            tx.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        emf.close();

    }
}
