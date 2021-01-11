package com.example.TimeAttendanceAPI.Repository;

import com.example.TimeAttendanceAPI.Model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository  extends JpaRepository<AccountRole, Integer> {
}
