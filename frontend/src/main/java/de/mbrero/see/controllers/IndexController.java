package de.mbrero.see.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;  

@Controller  
public class IndexController {
	private static final long serialVersionUID = 1L;
       
    public IndexController() {
		// TODO Auto-generated constructor stub
	}
    
    @RequestMapping("/")
    public String home() {
    	return "index";
    }

}
