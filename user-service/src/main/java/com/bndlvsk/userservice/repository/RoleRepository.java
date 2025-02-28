package com.bndlvsk.userservice.repository;

import com.bndlvsk.userservice.model.Role;
import com.bndlvsk.userservice.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);

}
