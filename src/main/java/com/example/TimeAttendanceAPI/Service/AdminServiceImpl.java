package com.example.TimeAttendanceAPI.Service;

import com.example.TimeAttendanceAPI.Model.DetailByMonth;
import com.example.TimeAttendanceAPI.Model.Employee;
import com.example.TimeAttendanceAPI.Repository.DetailByMonthRepository;
import com.example.TimeAttendanceAPI.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DetailByMonthRepository detailByMonthRepository;

    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployeeInfo(Integer id, Employee employee) {
        Optional<Employee> currentInfo = employeeRepository.findById(id);

        if (currentInfo.isPresent()) {
            Employee updatedInfo = currentInfo.get();

            if (employee.getName() != null)
                updatedInfo.setName(employee.getName());

            if (employee.getAge() != null)
                updatedInfo.setAge(employee.getAge());

            if (employee.getDepartment() != null)
                updatedInfo.setDepartment(employee.getDepartment());

            if (employee.getPosition() != null)
                updatedInfo.setPosition(employee.getPosition());

            if (employee.getRole() != null)
                updatedInfo.setRole(employee.getRole());

            return employeeRepository.save(employee);
        }

        return null;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        Optional<Employee> result = findEmployeeById(id);
        if (result.isPresent()) {
            employeeRepository.deleteById(id);

            return  true;
        }

        return false;
    }

    @Override
    public DetailByMonth createDetailByMonth(DetailByMonth detail) {
        return detailByMonthRepository.save(detail);
    }

    @Override
    public DetailByMonth updateDetailByMonth(DetailByMonth detail) {
        Optional<DetailByMonth> currentInfo = Optional.ofNullable(detailByMonthRepository.findByEmployeeIdAndMonth(detail.getEmployeeId(), detail.getMonth()));

        if (currentInfo.isPresent()) {
            DetailByMonth updatedInfo = currentInfo.get();

            if (detail.getDaysOff() != null)
                updatedInfo.setDaysOff(detail.getDaysOff());

            if (detail.getHoursOT() != null)
                updatedInfo.setHoursOT(detail.getHoursOT());

            if (detail.getStartTime() != null)
                updatedInfo.setStartTime(detail.getStartTime());

            if (detail.getEndTime() != null)
                updatedInfo.setEndTime(detail.getEndTime());

            if(detail.getBreakTime() != null) {
                updatedInfo.setBreakTime(detail.getBreakTime());
            }

            return detailByMonthRepository.save(detail);
        }

        return null;
    }
}
