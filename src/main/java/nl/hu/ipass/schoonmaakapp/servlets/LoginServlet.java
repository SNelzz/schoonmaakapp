package nl.hu.ipass.schoonmaakapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.hu.ipass.schoonmaakapp.entity.Gebruiker;
import nl.hu.ipass.schoonmaakapp.DAO.GebruikerDAO;

//Servlet voor het inloggen
@WebServlet(urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gebruikersnaam = req.getParameter("gebruikersnaam");
		String wachtwoord = req.getParameter("wachtwoord");

		GebruikerDAO gebruikerDAO = new GebruikerDAO();
		Gebruiker login = gebruikerDAO.findByGebruikersnaam(gebruikersnaam);

		if (login.komtWachtwoordOvereen(wachtwoord, gebruikersnaam) && !gebruikersnaam.equals("")) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("1");

		} else {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("gebruikersnaam en/of wachtwoord incorrect");
		}
	}
}