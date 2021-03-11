package com.example.TimeAttendanceAPI.domain.Repository;

import com.example.TimeAttendanceAPI.domain.Model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository  extends JpaRepository<AccountRole, Integer> {
}
