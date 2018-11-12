
package com.aliniribeiro.bates.api.controller.customer;

import com.aliniribeiro.bates.api.APIPaths;
import com.aliniribeiro.bates.api.common.Response;
import com.aliniribeiro.bates.api.common.util.Spring;
import com.aliniribeiro.bates.api.controller.customer.contracts.CustomerDTO;
import com.aliniribeiro.bates.api.controller.customer.contracts.SearchCustomerInput;
import com.aliniribeiro.bates.api.controller.customer.contracts.SearchCustomerOutput;
import com.aliniribeiro.bates.model.customer.CustomerEntity;
import com.aliniribeiro.bates.model.customer.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(APIPaths.CUSTOMER)
@CrossOrigin(origins = "*")
public class CustomerController {

    @PostMapping(value = "/create")
    public ResponseEntity<Response<CustomerDTO>> createClient(HttpServletRequest request, @RequestBody CustomerDTO custumer, BindingResult result) {
        Response<CustomerDTO> response = new Response<CustomerDTO>();
        try {
            CustomerDTO createdCustumer = Spring.bean(CustomerService.class).createClient(custumer);
            response.setData(createdCustumer);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<Response<CustomerDTO>> update(HttpServletRequest request, @RequestBody CustomerDTO custumer, BindingResult result) {
        Response<CustomerDTO> response = new Response<CustomerDTO>();
        try {
            CustomerDTO createdCustumer = Spring.bean(CustomerService.class).updateClient(custumer);
            response.setData(createdCustumer);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/search")
    public ResponseEntity<Response<SearchCustomerOutput>> searchCustomer(HttpServletRequest request, @RequestBody  SearchCustomerInput input, BindingResult result) {
        Response<SearchCustomerOutput> response = new Response<SearchCustomerOutput>();
        try {
            Optional<SearchCustomerOutput> serviceResponse = Spring.bean(CustomerService.class).searchCustomer(input.q, input.page, input.size);
            if (serviceResponse.isPresent()) {
                response.setData(serviceResponse.get());
            }
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") UUID id) {
        Response<String> response = new Response<String>();
        try {
            Optional<CustomerEntity> customer = Spring.bean(CustomerRepository.class).findCustomerById(id);
            Spring.bean(CustomerService.class).delete(customer.get());
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(new Response<String>());
    }



}
