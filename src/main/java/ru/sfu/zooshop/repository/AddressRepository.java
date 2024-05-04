package ru.sfu.zooshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.entity.UserEntity;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
  List<AddressEntity> findAllByUser(UserEntity user);

}
