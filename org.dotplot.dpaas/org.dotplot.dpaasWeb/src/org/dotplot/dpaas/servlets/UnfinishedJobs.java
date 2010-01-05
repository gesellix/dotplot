package org.dotplot.dpaas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dotplot.dpaas.beans.Login;
import org.dotplot.dpaas.ejb.BackendLocal;
import org.dotplot.dpaas.entities.Job;

/**
 * Servlet implementation class UnfinishedJobs
 */
public class UnfinishedJobs extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	BackendLocal backend;
	
    public UnfinishedJobs() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Login login = (Login) request.getSession().getAttribute("login");
		PrintWriter out = response.getWriter();
		
		if ((login == null)||(!login.isAuthenticated())) {
			out.println("Not allowed!");
			return;
		}
		
		out.println("<h3>Zu bearbeitende Jobs:</h3>");
		
		List<Job> joblist = backend.unfinishedJobs();
		
		if (joblist.size() == 0) {
			out.println("<div class='entry'>Keine Jobs in der Warteliste!</div>");
			return;
		}
		
		for ( Job j : backend.unfinishedJobs() ) {
			out.println( "<div class='entry'>" 
					+ j.getId()
					+ ":&nbsp;"
					+ j.getName()
					+ "&nbsp;("
					+ j.getEmail()
					+ ")</div>"
				);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
