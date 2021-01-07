package org.codejudge.sb.database;

import org.codejudge.sb.models.AttendanceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceDataRepository extends JpaRepository<AttendanceData, Long> {
}
