package com.example.demo.repository;

import com.example.demo.model.UserCV;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserCVRepository extends JpaRepository<UserCV, Integer> {
    Optional<UserCV> findById(int cvID);



}
