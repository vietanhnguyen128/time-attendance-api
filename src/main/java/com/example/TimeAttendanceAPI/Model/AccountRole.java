package com.example.TimeAttendanceAPI.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class AccountRole {

    @Id
    @Column(name = "id")
    @Min(0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "role_name")
    @Size(min = 0, max = 50)
    private String roleName;

    @Column(name = "created_by")
    @Min(0)
    private Integer createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    @Min(0)
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
