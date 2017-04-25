package de.mbrero.see.controllers;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView createTestRun() {
    	return new ModelAndView("create-test-run");
    }

    @PostMapping("create-test-run")
	protected void save(@ModelAttribute TestRun run) {
		
//		String path = request.getParameter("path");
//		
//		TestRun testRun = new TestRun();
//		testRun.setPath(path);
		
//		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
//		TestRunModel testRunModel = (TestRunModel) context.getBean("testRunBo");
		
//		testRunModel.save(testRun);
//		
//		PrintWriter out = response.getWriter();
//		out.print("Test run created");
	}

}
