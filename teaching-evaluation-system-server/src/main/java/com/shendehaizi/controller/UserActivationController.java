package com.shendehaizi.controller;

import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
public class UserActivationController {
    @Autowired
    private UserActivationService userActivationService;

     @PostMapping(value = "user/activation")
    public Response<String> addUserActivation(@RequestBody UserActivationRequest request){
         return userActivationService.addUserActivation(request);
     }
}
