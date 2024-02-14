package com.example.best.repository;

import com.example.best.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Roles findByName(String roleAdmin) ;

}
