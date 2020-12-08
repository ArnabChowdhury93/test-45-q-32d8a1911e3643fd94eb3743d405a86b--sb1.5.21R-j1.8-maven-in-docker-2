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
        if (null == lead_id) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason("lead_id is null");
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        leadInfo = leadInfoRepository.findOne(lead_id);
        if (null != leadInfo) {
            return new ResponseEntity<>(new LeadInfo(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new LeadInfo(), HttpStatus.OK);
    }

    @PostMapping("/leads/")
    public ResponseEntity<?> addLeadInfo(@RequestBody LeadInfo lead_info) {
        try {
            lead_info.setStatus(Status.CREATED);
            leadInfoRepository.save(lead_info);
            return new ResponseEntity<>(lead_info, HttpStatus.CREATED);
        } catch (Throwable ex) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason(ex.getMessage());
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
    }

    /*@PutMapping("/leads/")
    public ResponseEntity<?>*/

}
