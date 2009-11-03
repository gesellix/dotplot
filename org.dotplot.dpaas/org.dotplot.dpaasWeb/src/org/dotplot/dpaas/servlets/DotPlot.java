package org.dotplot.dpaas.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.dotplot.dpaas.beans.Check;
import org.dotplot.dpaas.ejb.BackendLocal;
import org.dotplot.dpaas.entities.Job;

/**
 * Servlet implementation class Service
 */
public class DotPlot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private BackendLocal backend;
	
	private ServletOutputStream _out;
	
    public DotPlot() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (_out==null) _out  = response.getOutputStream();
		
		if (request.getParameter("mode").equals("view")) 
			showImage(request, response, false);
		else if (request.getParameter("mode").equals("download")) 
			showImage(request, response, true);
		else if (request.getParameter("mode").equals("state"))
			state(request, response);
		else {
			_out.println("Fehlerhafte Anfrage!");
			_out.flush();
			_out = null;
		}
	}
	
	private void state(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Check check = (Check) request.getSession().getAttribute("check");
		
		int jobId;
		
		if (check == null) return;
		jobId = check.getJobId();
				
		Job j = backend.job(jobId);
		
		if (_out==null) _out  = response.getOutputStream();
		
		if (j == null) { 
			_out.println("Die angegebene Job-ID ist ung&uuml;ltig!");
			_out.flush();
			_out = null;
			return;
		}
		
		if (j.getFinished() == null) {
			_out.println("DotPlot noch in Bearbeitung!");
			_out.flush();
			_out = null;
			return;
		}
		
		if (j.getFinished() != null) {
			_out.println("DotPlot fertig gestellt! Sie k&ouml;nnen es jetzt <a href='DotPlot?mode=download'>hier</a> herunterladen.");
			_out.flush();
			_out = null;
			return;
		}
	}
	
	private void showImage(HttpServletRequest request,
			HttpServletResponse response, boolean attachment) throws IOException {
		
		try {
			Check check = (Check) request.getSession().getAttribute("check");
		
			int jobId;
			
			if (check == null) return;
			jobId = check.getJobId();
			
			if (_out==null) _out  = response.getOutputStream();
			
			if ( jobId > 0 ) {
				Job j = backend.job(jobId);
				
				if (j == null) {
					_out.println("Diese Anfrage kann nicht bearbeitet werden!");
					_out.flush();
					_out = null;
					return;
				}
				if (j.getImage() == null) {
					_out.println("Ihr Dotplot wird berechnet!");
					_out.flush();
					_out = null;
					return;
				}
				
				response.setContentType("image/png");
				if (attachment) {
					response.addHeader("Content-Disposition", "attachment; filename=" + "dotplot_"+jobId+".png");
				}
				_out.write(j.getImage());
				_out.flush();
				_out = null;
				
			} else {
				_out.println("Diese Anfrage kann nicht bearbeitet werden!");
			}
		} catch (Exception e) {	
			
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
