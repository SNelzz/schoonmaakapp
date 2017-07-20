package nl.hu.ipass.schoonmaakapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.hu.ipass.schoonmaakapp.entity.Gebruiker;
import nl.hu.ipass.schoonmaakapp.entity.Schoonmaaktaak;
import nl.hu.ipass.schoonmaakapp.DAO.GebruikerDAO;
import nl.hu.ipass.schoonmaakapp.DAO.SchoonmaaktaakDAO;

//Servlet voor het maken van een lijst met alle schoonmaaktaken van een gebruiker
@WebServlet(urlPatterns = "/AanpaslijstServlet")
public class AanpaslijstServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gebruikersnaam = req.getParameter("gebruikersnaam");

		GebruikerDAO gebruikerDAO = new GebruikerDAO();
		SchoonmaaktaakDAO schoonmaaktaakDAO = new SchoonmaaktaakDAO();
		Gebruiker gebr = gebruikerDAO.findByGebruikersnaam(gebruikersnaam);
		gebr.vulTaken(schoonmaaktaakDAO.findByGebruikersnaam(gebruikersnaam));

		String printString = "";
		List<Schoonmaaktaak> takenLijst = gebr.getDeTaken();
		// per object in de lijst een knop aanmaken
		for (Schoonmaaktaak taak : takenLijst) {
			printString += "<input class=\"button\" type=\"button\" value=\"" + taak.getTaakNaam()
					+ "\" onclick=\"taakButton(" + taak.getTaakNr() + ")\"/>";
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println(printString);

	}
}