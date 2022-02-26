package com.lecture.carrental.repository;

import com.lecture.carrental.domain.User;
import com.lecture.carrental.projection.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.lecture.carrental.exception.BadRequestException;
import com.lecture.carrental.exception.ResourceNotFoundException;


import java.util.List;
import java.util.Optional;

@Repository
@Transactional// içerideki tüm metodların aynı anda yürütülmesini istediğimizde bunu yazmamız lazım
public interface UserRepository extends JpaRepository<User, Long> {


    // @Query("SELECT u from User u where u.email=?1")  // bu otomatik oluşuyor  o yüzden yorum
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email) throws ResourceNotFoundException;

    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, " +
            "u.email = ?5, u.address = ?6, u.zipCode = ?7 " +
            "WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email, String address,
                String zipCode) throws BadRequestException;

    List<ProjectUser> findAllBy();
}
