package com.example.best.repository;

import com.example.best.model.Hostels;
import com.example.best.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostelRepository extends JpaRepository<Hostels,Long> {
    List<Hostels> findByUser(Users user);

    Optional<Hostels> findByIdAndUser(Long id, Users user);
}
