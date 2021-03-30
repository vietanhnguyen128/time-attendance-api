package com.example.TimeAttendanceAPI;

import com.example.TimeAttendanceAPI.domain.Model.Attendance;
import com.example.TimeAttendanceAPI.domain.Repository.AttendanceRepository;
import com.example.TimeAttendanceAPI.domain.Repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class getTotalLateTimeTest {

    @MockBean
    AttendanceRepository attendanceRepository;

    @MockBean
    EmployeeRepository employeeRepository;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Duration getTotalLateTime(Integer employeeId, ArrayList<Attendance> r) {
            ArrayList<Attendance> records = r;
            Duration total = Duration.ZERO;
            LocalTime startTime = LocalTime.parse("08:15:00");
            LocalDate currentDate;
            LocalDate previousDate = LocalDate.MIN;

            for (int i = 0; i < records.size(); i += 2) {
                currentDate = records.get(i).getDateRecord();
                if (previousDate.equals(currentDate)) {
                    continue;
                }
                Duration temp = Duration.between(startTime, records.get(i).getTimeRecord());
                if (!temp.isNegative()) {
                    total = total.plus(temp);
                }
                previousDate = currentDate;
            }

            return total;
    }

    @Test
    public void testFunction() throws Exception {
        Attendance record_1 = new Attendance(1, 1, LocalDate.now(), LocalTime.parse("08:45:00"));
        Attendance record_2 = new Attendance(2, 1, LocalDate.now(), LocalTime.parse("17:45:00"));
        Attendance record_3 = new Attendance(3, 1, LocalDate.now(), LocalTime.parse("07:45:00"));
        Attendance record_4 = new Attendance(4, 1, LocalDate.now(), LocalTime.parse("18:45:00"));
        Attendance record_5 = new Attendance(5, 1, LocalDate.now(), LocalTime.parse("07:45:00"));
        Attendance record_6 = new Attendance(6, 1, LocalDate.now(), LocalTime.parse("18:45:00"));

        ArrayList<Attendance> temp = new ArrayList<>();
        temp.add(record_1);
        temp.add(record_2);
        temp.add(record_3);
        temp.add(record_4);
        temp.add(record_5);
        temp.add(record_6);

//        Mockito.when(attendanceRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(new Attendance()));
//        Mockito.when(attendanceRepository.findById(Mockito.anyInt()).isPresent()).thenReturn(true);
//        Mockito.when(attendanceRepository.getAttendanceDetail(Mockito.anyInt())).thenReturn(temp);
//        Mockito.when(employeeRepository.getOne(Mockito.anyInt()).getShiftStart()).thenReturn();

        System.out.println(getTotalLateTime(1, temp));
    }
}
