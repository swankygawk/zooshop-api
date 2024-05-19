package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshop.entity.AddressEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
  List<AddressEntity> findAllByCreatedById(Long id);
  Optional<AddressEntity> findByIdAndCreatedById(Long id, Long userId);
}
