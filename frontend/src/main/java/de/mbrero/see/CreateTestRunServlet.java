package de.mbrero.see;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.mbrero.see.models.bo.TestRunModel;
import de.mbrero.see.persistance.dto.TestRun;

/**
 * Servlet implementation class createTestRun
 */
@WebServlet("create-test-run")
public class CreateTestRunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTestRunServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getParameter("path");
		
		TestRun testRun = new TestRun();
		testRun.setPath(path);
		
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		TestRunModel testRunModel = (TestRunModel) context.getBean("testRunBo");
		
		testRunModel.save(testRun);
		
		PrintWriter out = response.getWriter();
		out.print("Test run created");
	}

}
