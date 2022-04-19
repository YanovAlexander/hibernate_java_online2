package com.goit.message;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        final SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            System.out.println("Session opened");
            transaction = session.beginTransaction();
            //retrieve by id = 1
            final List<Message> messages = session.createQuery("FROM Message").list();
            messages.forEach(message -> {
                System.out.println("----------------------------");
                System.out.println("Message id " + message.getId());
                System.out.println("Message " + message.getMessage());
            });
            transaction.commit();
            System.out.println("Session closed");
        } catch (Exception ex) {
            if (transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            ex.printStackTrace();
        }
    }
}
