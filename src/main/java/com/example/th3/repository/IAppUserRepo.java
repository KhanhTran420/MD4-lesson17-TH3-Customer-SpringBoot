package com.example.th3.repository;

import com.example.th3.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByName(String name);
}
