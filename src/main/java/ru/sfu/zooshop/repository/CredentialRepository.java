package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.CredentialEntity;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {}
