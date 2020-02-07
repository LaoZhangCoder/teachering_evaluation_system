package com.shendehaizi.controller;

import com.shendehaizi.request.UserActivationRequest;
import com.shendehaizi.request.UserActivationUpdateRequest;
import com.shendehaizi.response.ActivationUserInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class UserActivationController {
    @Autowired
    private UserActivationService userActivationService;

     @PostMapping(value = "user/activation")
    public Response<String> addUserActivation(@RequestBody UserActivationRequest request){
         return userActivationService.addUserActivation(request);
     }
    @GetMapping(value = "user/list")
    public Response<List<ActivationUserInfo>> getActivationUserInfo(){
        return userActivationService.getActivationUserInfo();
    }
    @GetMapping(value = "user/page")
    public Response<List<ActivationUserInfo>> getPagingActivationUserInfo(Integer currentPage){
        return userActivationService.getPagingActivationUserInfo(currentPage,8);
    }

    @PostMapping(value = "user/update/{id}")
    public Response<String>  updateActivationUserInfo(@RequestBody UserActivationUpdateRequest request,@PathVariable("id") String id){
         return userActivationService.handleUpdateUserInfo(request,id);
    }
    @GetMapping(value = "user/delete/{id}")
    public Response<String>  deleteActivationUserInfo(@PathVariable("id") String id){
        return userActivationService.handleDeleteUserInfo(id);
    }
}
