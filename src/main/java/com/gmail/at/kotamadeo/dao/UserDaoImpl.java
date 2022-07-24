package com.gmail.at.kotamadeo.dao;

import com.gmail.at.kotamadeo.models.Role;
import com.gmail.at.kotamadeo.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<User> showAllUsers() {
            return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> showAllRoles() {
            return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllEmailList() {
        List<String> emails = new ArrayList<>();
        showAllUsers().forEach(user -> emails.add(user.getEmail()));
        return emails;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void createNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void createNewRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    @Transactional
    public void updateUserById(long id, User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        entityManager.remove(getUser(id));
    }
}
