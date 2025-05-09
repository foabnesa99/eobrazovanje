package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(UserRole role);

}
