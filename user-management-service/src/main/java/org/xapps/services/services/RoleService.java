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
import org.xapps.services.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Slf4j
public class RoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ModelMapper modelMapper;

    public List<Role> getById(List<Long> ids) {
        log.debug("GetById " + ids);
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class);
        query.setParameter("ids", ids);
        List<Role> roles = query.getResultList();
        return roles;
    }

    public Role getByName(String name) {
        log.debug("GetByName " + name);
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("No role found with name " + name);
        } catch (Exception ex) {
            log.error("Exception captured", ex);
        }
        return role;
    }

    public List<RoleResponse> getAll() {
        log.debug("getAll");
        List<RoleResponse> response = null;
        List<Role> roles = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        if (roles != null && !roles.isEmpty()) {
            response = roles.stream().map(r -> modelMapper.map(r, RoleResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        return response;
    }

}
