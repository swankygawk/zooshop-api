package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.AuthorityEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {}
