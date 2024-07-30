package com.riskanalysisexpertsystem.authApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.riskanalysisexpertsystem.authApp.entity.User;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long>{
}
