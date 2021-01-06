package com.example.TimeAttendanceAPI.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;

    private String name;

    private Integer age;

    private String department;

    private String position;

    private Integer daysOff;

    private Integer daysOT;

    private Date startDate;

    private Date endDate;

    private String role;
}
