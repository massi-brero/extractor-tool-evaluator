package de.mbrero.see.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

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
 * Servlet implementation class DisplayTestRunsServlet
 */
@WebServlet("/display-test-runs")
public class DisplayTestRunsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayTestRunsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		TestRunModel testRunModel = (TestRunModel) context.getBean("testRunBo");
		
		List<TestRun> allTests = testRunModel.getAll();
		ListIterator<TestRun> iter = allTests.listIterator();
		
		while (iter.hasNext())
		{
			TestRun item = iter.next();
			out.println(item.getId());
			out.println(item.getDate());
			out.println(item.getPath());
			out.println(item.getResult());
		}

	}


}
