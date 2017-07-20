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

//Servlet voor get opslaan van een nieuwe gebruiker
@WebServlet(urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String voornaam = req.getParameter("voornaam");
		String tussenvoegsel = req.getParameter("tussenvoegsel");
		String achternaam = req.getParameter("achternaam");
		String gebruikersnaam = req.getParameter("gebruikersnaam1");
		String wachtwoord = req.getParameter("wachtwoord1");

		GebruikerDAO gebruikerDAO = new GebruikerDAO();
		Gebruiker register = new Gebruiker(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		Gebruiker check = gebruikerDAO.findByGebruikersnaam(gebruikersnaam);

		// check uitvoeren op de ingevoerde velden
		if (gebruikersnaam.length() < 4) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("gebruikersnaam is te kort");

		} else if (register.equals(check)) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("gebruikersnaam bestaat al");

		} else if (gebruikerDAO.insert(register)) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("registratie voltooid");

		} else {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("registratie mislukt");

		}
	}
}