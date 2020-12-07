package org.codejudge.sb.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class RequestObjectLead {

    private String first_name;
    private String last_name;
    private String mobile;
    private String email;
    private LocationType location_type;
    private String location_string;

}
