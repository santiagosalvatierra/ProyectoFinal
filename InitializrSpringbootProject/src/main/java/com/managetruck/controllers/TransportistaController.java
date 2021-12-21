
package com.managetruck.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transportista")
public class TransportistaController {
    
    @GetMapping("")
    public String transportista(){
  
    return "transportista.html";
    }
}
