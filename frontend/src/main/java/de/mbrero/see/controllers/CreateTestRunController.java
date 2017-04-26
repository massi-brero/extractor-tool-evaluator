package de.mbrero.see.controllers;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import de.mbrero.see.models.bo.TestRunModel;
import de.mbrero.see.persistance.dto.TestRun;


@Controller  
public class CreateTestRunController {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTestRunController() {
		// TODO Auto-generated constructor stub
	}
    
    @GetMapping("create-test-run")
    public ModelAndView createTestRun(Model model) {
    	TestRun testRun = new TestRun();
    	testRun.setPath("example/path");
    	model.addAttribute("testRun", testRun);
    	
    	return new ModelAndView("create-test-run");
    }

    @PostMapping("create-test-run")
	protected void save(@ModelAttribute TestRun run) {
			
		TestRunModel testRunModel = new TestRunModel();
		testRunModel.save(run);
		
	}

}
