package com.shendehaizi.controller;

import com.shendehaizi.request.*;
import com.shendehaizi.response.ActivationUserInfo;
import com.shendehaizi.response.AdminInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.response.UserInfoDetail;
import com.shendehaizi.service.AdminActivationService;
import com.shendehaizi.service.UserActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class AdminController {
    @Autowired
    private AdminActivationService adminActivationService;

    @GetMapping(value = "admin/list")
    public Response<List<AdminInfo>> getActivationUserInfo(){
        return adminActivationService.getAdminInfo();
    }

    @PostMapping(value = "admin/activation")
    public Response<String> addAdmin(@RequestBody AdminAddRequest request){
        return adminActivationService.addAdmin(request);
    }

    @GetMapping(value = "admin/page")
    public Response<List<AdminInfo>> getPagingAdminInfo(Integer currentPage){
        return adminActivationService.getPagingAdminInfo(currentPage,8);
    }

    @PostMapping(value = "admin/update/{id}")
    public Response<String>  updateAdminInfo(@RequestBody AdminUpdateRequest request, @PathVariable("id") String id){
        return adminActivationService.updateAdminInfo(request,id);
    }
    @GetMapping(value = "admin/delete/{id}")
    public Response<String>  deleteAdminInfo(@PathVariable("id") String id){
        return adminActivationService.deleteAdminInfo(id);
    }
    @GetMapping(value = "admin/userInfo/List}")
    public Response<List<UserInfoDetail>>  deleteAdminInfo(UserInfoRequest request){

    }
}
