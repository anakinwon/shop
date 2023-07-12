package com.basic.basicjpa01;

import com.basic.basicjpa01.domain.Member;
import org.springframework.orm.jpa.JpaSystemException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Eaxm03JpqlPersistMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /**  비영속 상태  */
            Member member = new Member();
//            member.setId(500L);
            member.setName("HelloJPA5");

            /** 영속 상태   (1차 캐시)  */
            System.out.println("Persist 전단계 = " + member.getName());
            em.persist(member);
            em.flush();
            System.out.println("Persist 후단계  = " + member.getName());

            /**  준영속상태   */
            em.detach(member);
            System.out.println("detach 후단계  = " + member.getName());

            // 저장 후 1차 캐시에서 조회되므로,
            // Select 쿼리로 DB를 접근하지 않는다.
            Member findMember = em.find(Member.class, 500L);
            System.out.println("\t\tfindMember = " + findMember);

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
