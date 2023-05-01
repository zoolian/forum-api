package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Privilege;
import com.jmscottnovels.forumapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
