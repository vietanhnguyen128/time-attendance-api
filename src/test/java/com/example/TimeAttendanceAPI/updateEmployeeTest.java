package com.example.TimeAttendanceAPI;


import com.example.TimeAttendanceAPI.Model.Department;
import com.example.TimeAttendanceAPI.Model.Employee;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class updateEmployeeTest {

    public Employee updateEmployee(Employee currentEmployee, Employee updatedEmployee) throws IllegalAccessException {

        Field[] fields = currentEmployee.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            Class t = f.getType();
            Object v = f.get(updatedEmployee);

            if (v != null) {
                f.set(currentEmployee, v);
            }
        }

        return currentEmployee;
    }

//    @Test
//    public void updateEmployeeTesting() throws Exception {
//        Employee current = new Employee(1, "Viet Anh", 23, "male", 1, 1, 1, 1,
//                LocalTime.parse("08:15:00"), LocalTime.parse("17:30:00"), LocalTime.parse("03:00:00"), LocalTime.parse("00:00:00"), LocalTime.parse("00:00:00"),
//                "vietanhng", "12345", 2, LocalDateTime.parse("11-01-2021 15:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
//                null, null);
//
//        Employee updated = new Employee(1, "Thuy Duong", 23, "female", null, 2, 2, 2,
//                null, null, null, null, null,
//                "duonglnt", "12345", null, null,
//                3, LocalDateTime.parse("11-01-2021 15:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
//
//        System.out.println(updateEmployee(current, updated));
//
//    }
}
