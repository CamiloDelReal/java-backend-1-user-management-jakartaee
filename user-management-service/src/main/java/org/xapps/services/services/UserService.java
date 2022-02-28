package org.xapps.services.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.xapps.services.dtos.RoleResponse;
import org.xapps.services.dtos.UserRequest;
import org.xapps.services.dtos.UserResponse;
import org.xapps.services.entities.Role;
import org.xapps.services.entities.User;
import org.xapps.services.exceptions.DuplicityException;
import org.xapps.services.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Slf4j
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ModelMapper modelMapper;

    @Inject
    private RoleService roleService;

    public List<UserResponse> getAll() {
        log.debug("getAll");
        List<UserResponse> response = null;
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        if (users != null && !users.isEmpty()) {
            response = users.stream().map(u -> modelMapper.map(u, UserResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        return response;
    }

    public UserResponse getById(Long id) throws NotFoundException {
        log.debug("getById " + id);
        UserResponse response = null;
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = query.getSingleResult();
        if (user != null) {
            response = modelMapper.map(user, UserResponse.class);
        } else {
            log.debug("User with Id " + id + " not found");
            throw new NotFoundException("Nonexistent Id " + id);
        }
        return response;
    }

    public Optional<User> getByEmail(String email) {
        log.debug("getByEmail " + email);
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException exception) {
            log.debug("No user found with email " + email);
        } catch (Exception ex) {
            log.error("Exception captured", ex);
        }
        return Optional.ofNullable(user);
    }

    public UserResponse create(UserRequest userRequest) throws DuplicityException {
        log.debug("create " + userRequest);
        UserResponse response = null;
        Optional<User> duplicity = getByEmail(userRequest.getEmail());
        if (duplicity.isEmpty()) {
            User user = modelMapper.map(userRequest, User.class);
            List<Role> roles = null;
            if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
                roles = roleService.getById(userRequest.getRoles());
            }
            if (roles == null || roles.isEmpty()) {
                Role guestRole = roleService.getByName(Role.GUEST);
                roles = List.of(guestRole);
            }
            user.setRoles(roles);
            // Protect password
            entityManager.persist(user);
            response = modelMapper.map(user, UserResponse.class);
        } else {
            log.debug("Email " + userRequest.getEmail() + " is in use");
            throw new DuplicityException("Email " + userRequest.getEmail() + " is in use");
        }
        return response;
    }

    public UserResponse edit(Long id, UserRequest userRequest) throws NotFoundException, DuplicityException {
        log.debug("edit " + id + " " + userRequest);
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("User with id " + id + " not found");
        }
        UserResponse response = null;
        if (user != null) {
            query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.id != :id", User.class);
            query.setParameter("email", userRequest.getEmail());
            query.setParameter("id", id);
            User duplicatedEmail = null;
            try {
                duplicatedEmail = query.getSingleResult();
            } catch (NoResultException exception) {
                log.debug("No duplicity found for email " + id);
            }
            if(duplicatedEmail == null) {
                user.setEmail(userRequest.getEmail());
                // Protect password
                user.setFirstName(userRequest.getFirstName());
                user.setLastName(userRequest.getLastName());

                List<Role> roles = null;
                if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
                    roles = roleService.getById(userRequest.getRoles());
                }
                if (roles != null && !roles.isEmpty()) {
                    user.setRoles(roles);
                }
                entityManager.refresh(user);
                response = modelMapper.map(user, UserResponse.class);
            } else {
                log.debug("Email " + userRequest.getEmail() + " is in use");
                throw new DuplicityException("Email " + userRequest.getEmail() + " is in use");
            }
        } else {
            log.debug("User with Id " + id + " not found");
            throw new NotFoundException("Nonexistent Id " + id);
        }
        return response;
    }

    public void delete(Long id) throws NotFoundException {
        log.debug("delete " + id);
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        User user = query.getSingleResult();
        if (user != null) {
            entityManager.remove(user);
        } else {
            log.debug("User with id " + id + " not found");
            throw new NotFoundException("Nonexistent id " + id);
        }
    }

}
