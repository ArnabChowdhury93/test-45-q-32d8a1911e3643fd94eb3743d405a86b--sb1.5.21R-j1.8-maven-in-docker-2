package org.codejudge.sb.models;

import lombok.Data;

import javax.persistence.Entity;

@Data
//@Entity
public class AttendanceData {
    private double hours;
    private int leadId;

}
