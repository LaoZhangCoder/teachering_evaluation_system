package com.shendehaizi.controller;

import com.shendehaizi.request.MajorAddRequest;
import com.shendehaizi.request.MajorUpdateRequest;
import com.shendehaizi.response.MajorInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MajorController {
    @Autowired
    private MajorService majorService;
    @PostMapping(value = "major/add")
    public Response<String> addMajor(@RequestBody  MajorAddRequest request){
        return majorService.addMajor(request);
    }

    @GetMapping(value = "major/list")
    public Response<List<MajorInfo>> listMajorList(){
        return majorService.listMajor();
    }

    @PostMapping(value = "major/update")
    public Response<String> updateMajor(@RequestBody MajorUpdateRequest request){
        return majorService.updateMajorInfo(request);
    }

    @GetMapping(value = "major/delete")
    public Response<String> deleteMajor(Long majorId){
        return majorService.deleteMajorInfo(majorId);
    }
}
