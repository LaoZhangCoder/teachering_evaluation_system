package com.shendehaizi.controller;

import com.shendehaizi.request.ClassAddeRquest;
import com.shendehaizi.request.ClassUpdateRequest;
import com.shendehaizi.response.ClassInfo;
import com.shendehaizi.response.Response;
import com.shendehaizi.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
@Controller
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping(value = "class/add")
    public Response<String> addClass(@RequestBody ClassAddeRquest request) {
         return classService.addClass(request);
    }

    @GetMapping(value = "class/list")
    public Response<List<ClassInfo>> listClassInfos(){
        return classService.listClass();
    }
    @GetMapping(value = "class/delete")
    public Response<String> deleteMajor(Long classId){
        return classService.deleteClassInfo(classId);
    }
    @PostMapping(value = "class/update")
    public Response<String> updateMajor(@RequestBody ClassUpdateRequest request){
        return classService.updateClassInfo(request);
    }
}
