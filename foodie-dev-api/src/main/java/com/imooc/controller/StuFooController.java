package com.imooc.controller;

import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuFooController {

    @Autowired
    private StuService stuService;
    @GetMapping("/getStu")
    public Object getStu(@RequestParam("id") int id){
        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu(){
        stuService.saveStu();
        return "OK";
    }

    @PostMapping("/updateStu")
    public Object updateStu(@RequestParam("id") int id){
        stuService.updateStu(id);
        return "OK";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(@RequestParam("id") int id){
        stuService.deleteStu(id);
        return "OK";
    }
}
