package edu.mum.waa.repository;

import edu.mum.waa.entity.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends CrudRepository<Attendance, Long> {




}
