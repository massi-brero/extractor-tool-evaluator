package de.mbrero.see;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @RequestMapping("/create-test-run")
    public String createTestRun() {
    	return "create-test-run";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getParameter("path");
		
		TestRun testRun = new TestRun();
		testRun.setPath(path);
		
//		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
//		TestRunModel testRunModel = (TestRunModel) context.getBean("testRunBo");
		
//		testRunModel.save(testRun);
//		
//		PrintWriter out = response.getWriter();
//		out.print("Test run created");
	}

}
