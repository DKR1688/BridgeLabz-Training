package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);

    Optional<Tag> findByNameAndOwner(String name, User owner);

    Optional<Tag> findByNameAndOwnerAndIsDeletedFalse(String name, User owner);

    List<Tag> findByOwnerAndIsDeletedFalse(User owner);

    Optional<Tag> findByTagIdAndOwner(int tagId, User owner);
}
