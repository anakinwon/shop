package com.basic.basicjpa01;

import com.basic.basicjpa01.domain.*;
import com.basic.basicjpa01.domain.item.Book;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EnableJpaAuditing//(modifyOnCreate = false)     // @EnableJpaAuditing(modifyOnCreate = false) 시 UpdateID는 null로 저장된다.
public class MasterExam {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            LocalDate now = LocalDate.now();

            // 회원 가입
            Member member = new Member();
            member.setName("아나킨");
//            member.setCity("성남");
//            member.setStreet("정자로");
            em.persist(member);

            // 아이템 등록
            Book book = new Book();
            book.setName("JPA 기초");
            book.setAuthor("아나킨");
            em.persist(book);

            //
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(book);
            em.persist(orderItem);

            Order order = new Order();
            order.setMember(member);
            order.setOrderDate(LocalDateTime.from(now));
            order.addOrderItem(orderItem);

            em.persist(order);

            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
//            e.printStackTrace();
        }
        finally {
            em.close();
        }
        emf.close();
    }

}
