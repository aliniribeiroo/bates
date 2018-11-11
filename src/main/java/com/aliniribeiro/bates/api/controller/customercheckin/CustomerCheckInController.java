
package com.aliniribeiro.bates.api.controller.customercheckin;

import com.aliniribeiro.bates.api.APIPaths;
import com.aliniribeiro.bates.api.common.Response;
import com.aliniribeiro.bates.api.common.util.Spring;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInDTO;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.CheckInOutput;
import com.aliniribeiro.bates.api.controller.customercheckin.contracts.HostedCustomersOutut;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(APIPaths.CHECK_IN)
@CrossOrigin(origins = "*")
public class CustomerCheckInController {


    @PostMapping(value = "/create")
    public ResponseEntity<Response<CheckInOutput>> createClient(HttpServletRequest request, @RequestBody CheckInDTO checkin, BindingResult result) {
        Response<CheckInOutput> response = new Response<CheckInOutput>();
        try {
            CheckInOutput createdCustumer = Spring.bean(CustomerCheckInService.class).createOrUpdateCheckIn(checkin);
            response.setData(createdCustumer);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/past-hosted-customers/{page}/{size}")
    public ResponseEntity<Response<HostedCustomersOutut>> pastHostedCustomers(@PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<HostedCustomersOutut> response = new Response<HostedCustomersOutut>();
        try {
            HostedCustomersOutut pastHosted = Spring.bean(CustomerCheckInService.class).getHostedCustomers(true, page, size);
           response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/hosted-customers/{page}/{size}")
    public ResponseEntity<Response<HostedCustomersOutut>> getostedCustomers(@PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<HostedCustomersOutut> response = new Response<HostedCustomersOutut>();
        try {
            HostedCustomersOutut pastHosted = Spring.bean(CustomerCheckInService.class).getHostedCustomers(false, page, size);
            response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
