package com.managetruck.controllers;

import com.managetruck.entidades.LoginConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/login")
public class AjaxLoginController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    //In order to login with a modal through ajax we have to create a validation method other than the spring security
    public String performLogin(@RequestBody LoginConfig lc,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            //Using the class HttpServletRequest we can check the matches in the username and password
            request.login(lc.getUsername(), lc.getPassword());
            //if the request matches returns true and enables a login 
            return "{\"status\": true}";
        } catch (Exception e) {
            //if the request fails throws an exception that we can handle to send a message to the front-end
            return "{\"status\": false, \"error\": \"Los datos ingresados son incorrectos\"}";
        }
    }
}
