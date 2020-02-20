package com.shendehaizi.controller;

import com.shendehaizi.request.*;
import com.shendehaizi.response.*;
import com.shendehaizi.service.AdminActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class AdminController {
    @Autowired
    private AdminActivationService adminActivationService;

    @GetMapping(value = "admin/list")
    public Response<List<AdminInfo>> getActivationUserInfo() {
        return adminActivationService.getAdminInfo();
    }

    @PostMapping(value = "admin/activation")
    public Response<String> addAdmin(@RequestBody AdminAddRequest request) {
        return adminActivationService.addAdmin(request);
    }

    @GetMapping(value = "admin/page")
    public Response<List<AdminInfo>> getPagingAdminInfo(Integer currentPage) {
        return adminActivationService.getPagingAdminInfo(currentPage, 8);
    }

    @PostMapping(value = "admin/update/{id}")
    public Response<String> updateAdminInfo(@RequestBody AdminUpdateRequest request, @PathVariable("id") String id) {
        return adminActivationService.updateAdminInfo(request, id);
    }

    @GetMapping(value = "admin/delete/{id}")
    public Response<String> deleteAdminInfo(@PathVariable("id") String id) {
        return adminActivationService.deleteAdminInfo(id);
    }

    @GetMapping(value = "admin/user")
    public Response<List<UserInfoDetail>> getUserInfoList(UserInfoRequest request) {
        return adminActivationService.getUserInfos(request);
    }
    @GetMapping(value = "admin/count")
    public Response<CountInfo> getCountInfos(){
        return adminActivationService.getCountInfos();
    }

    @GetMapping(value = "admin/process")
    public Response<ProcessInfo> getProcessInfo(){
        return adminActivationService.getProcessInfo();
    }

    @GetMapping(value = "admin/score")
    public Response<List<TeacherScore>> getTeacherScoreList(TeacherScoreRequest request){
          return adminActivationService.getTeacherScoreList(request);
    }
}
