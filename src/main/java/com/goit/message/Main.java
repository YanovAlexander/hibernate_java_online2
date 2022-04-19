package com.goit.message;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
            final Message message = session.get(Message.class, 1);
            //modify set new message body
            message.setMessage(message.getMessage() + " UPDATED");
            transaction.commit();
            session.merge(message);
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
