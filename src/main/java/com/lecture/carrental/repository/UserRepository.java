package com.lecture.carrental.repository;

import com.lecture.carrental.domain.User;
import com.lecture.carrental.exception.ResourcesNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
@Transactional// içerideki tüm metodların aynı anda yürütülmesini istediğimizde bunu yazmamız lazım
public interface UserRepository extends JpaRepository<User, Long> {


    // @Query("SELECT u from User u where u.email=?1")  // bu otomatik oluşuyor  o yüzden yorum
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email) throws ResourcesNotFoundException;
}
