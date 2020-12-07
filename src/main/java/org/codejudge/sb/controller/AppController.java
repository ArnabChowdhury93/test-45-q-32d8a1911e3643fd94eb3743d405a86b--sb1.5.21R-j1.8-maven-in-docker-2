package org.codejudge.sb.controller;

import io.swagger.annotations.ApiOperation;
import org.codejudge.sb.database.LeadInfoRepository;
import org.codejudge.sb.models.LeadInfo;
import org.codejudge.sb.models.RequestObjectLead;
import org.codejudge.sb.models.ResponseObjectStatus;
import org.codejudge.sb.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private LeadInfoRepository leadInfoRepository;

    @ApiOperation("This is the hello world api")
    @GetMapping("/")
    public String hello() {
        return "Hello World!!";
    }

    @GetMapping("/leads/")
    public ResponseEntity<?> getLeadInfo(@RequestParam("lead_id") Integer lead_id) {
        LeadInfo leadInfo = new LeadInfo();
        if(null==lead_id){
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason("lead_id is null");
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        leadInfo = leadInfoRepository.findOne(lead_id);
        if(null==leadInfo){
            return new ResponseEntity<>(new LeadInfo(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new LeadInfo(), HttpStatus.OK);
    }

    @PostMapping("/leads/")
    public ResponseEntity<?> addLeadInfo(@RequestBody RequestObjectLead requestObjectLead) {
       try{
            LeadInfo lead_Info = new LeadInfo();
            lead_Info.setFirst_name(requestObjectLead.getFirst_name());
            lead_Info.setLast_name(requestObjectLead.getLast_name());
            lead_Info.setEmail(requestObjectLead.getEmail());
            lead_Info.setMobile(requestObjectLead.getMobile());
            lead_Info.setLocation_string(requestObjectLead.getLocation_string());
            lead_Info.setLocation_type(requestObjectLead.getLocation_type());
            lead_Info.setStatus(Status.CREATED);
            leadInfoRepository.save(lead_Info);
            return new ResponseEntity<>(lead_Info, HttpStatus.CREATED);
       }
       catch(Throwable ex){
           ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
           responseObjectStatus.setStatus("failure");
           responseObjectStatus.setReason(ex.getMessage());
           return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
       }
    }

   /* @PutMapping("/leads/")
    public ResponseEntity<?>*/

}
