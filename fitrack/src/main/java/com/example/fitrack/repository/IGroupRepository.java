package com.example.fitrack.repository;

import com.example.fitrack.models.Group;
import com.example.fitrack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersContaining(User user);
}
