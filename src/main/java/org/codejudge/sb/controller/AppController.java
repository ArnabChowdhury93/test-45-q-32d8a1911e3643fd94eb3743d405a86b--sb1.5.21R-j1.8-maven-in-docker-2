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

    @GetMapping("/leads/{lead_id}")
    public ResponseEntity<?> getLeadInfo(@PathVariable("lead_id") Integer lead_id) {
        LeadInfo leadInfo = new LeadInfo();
        if (null == lead_id) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason("lead_id is null");
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        leadInfo = leadInfoRepository.findOne(lead_id);
        if (null == leadInfo) {
            return new ResponseEntity<>(new LeadInfo(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(leadInfo, HttpStatus.OK);
    }

    @PostMapping("/leads/")
    public ResponseEntity<?> addLeadInfo(@RequestBody LeadInfo lead_info) {
        try {
            lead_info.setStatus(Status.Created);
            leadInfoRepository.save(lead_info);
            return new ResponseEntity<>(lead_info, HttpStatus.CREATED);
        } catch (Throwable ex) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason(ex.getMessage());
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/leads/{lead_id}")
    public ResponseEntity<?> updateLeadInfo(@PathVariable("lead_id") Integer lead_id, @RequestBody LeadInfo lead_info){
        ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();

        LeadInfo leadInfoFromDB = leadInfoRepository.findOne(lead_id);
        leadInfoFromDB.setFirst_name(lead_info.getFirst_name());
        leadInfoFromDB.setLast_name(lead_info.getLast_name());
        leadInfoFromDB.setMobile(lead_info.getMobile());
        leadInfoFromDB.setEmail(lead_info.getEmail());
        leadInfoFromDB.setLocation_type(lead_info.getLocation_type());
        leadInfoFromDB.setLocation_string(lead_info.getLocation_string());

        try{
            leadInfoRepository.save(leadInfoFromDB);
            responseObjectStatus.setStatus("success");
        }
        catch(Throwable ex){
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason(ex.getMessage());
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseObjectStatus, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/leads/{lead_id}")
    public ResponseEntity<?> removeLeadInfo(@PathVariable("lead_id") Integer lead_id){
        if (null == lead_id) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason("lead_id is null");
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
        try{
            leadInfoRepository.delete(lead_id);
            responseObjectStatus.setStatus("success");
        }
        catch(Throwable ex){
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason(ex.getMessage());
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseObjectStatus, HttpStatus.OK);
    }

    @PutMapping("/mark_lead/{lead_id}")
    public ResponseEntity<?> markLeadInfo(@PathVariable("lead_id") Integer lead_id, @RequestBody LeadInfo lead_info){
        if (null == lead_id || null==lead_info.getCommunication()) {
            ResponseObjectStatus responseObjectStatus = new ResponseObjectStatus();
            responseObjectStatus.setStatus("failure");
            responseObjectStatus.setReason("lead_id is null");
            return new ResponseEntity<>(responseObjectStatus, HttpStatus.BAD_REQUEST);
        }

        LeadInfo leadInfoFromDB = leadInfoRepository.findOne(lead_id);
        leadInfoFromDB.setCommunication(lead_info.getCommunication());
        leadInfoFromDB.setStatus(Status.Contacted);
        leadInfoRepository.save(leadInfoFromDB);
        return new ResponseEntity<>(leadInfoFromDB, HttpStatus.ACCEPTED);
    }



}
