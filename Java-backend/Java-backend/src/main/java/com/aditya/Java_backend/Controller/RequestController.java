package com.aditya.Java_backend.Controller;

import com.aditya.Java_backend.Service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RequestController {

    @Autowired
    public ApiService apiService;

    @PostMapping("/review")
    public Map<String,String> codeReview(@RequestBody Map<String,String> body)
    {
        System.out.println("Request Recived in codeReview function !");
        String review = apiService.fetchData(body.get("code"));
        return Map.of("review", review);
    }

}
