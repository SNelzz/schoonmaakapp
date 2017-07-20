package nl.hu.ipass.schoonmaakapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.time.*;
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

//Servlet voor het ophalen van de taken in de huidige week en taak opslaan als voltooid
@WebServlet(urlPatterns = "/SchemaServlet")
public class SchemaServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		String gebruikersnaam = req.getParameter("gebruikersnaam");

		GebruikerDAO gebruikerDAO = new GebruikerDAO();
		SchoonmaaktaakDAO schoonmaaktaakDAO = new SchoonmaaktaakDAO();

		// uitvoeren als er wordt gevraagd om schoonmaaktaak gegevens
		if (method.equals("ophalen")) {
			Gebruiker gebr = gebruikerDAO.findByGebruikersnaam(gebruikersnaam);
			gebr.vulTaken(schoonmaaktaakDAO.findByGebruikersnaam(gebruikersnaam));

			String printString = "";
			List<Schoonmaaktaak> takenLijst = gebr.getDeTaken();

			TemporalField woy = WeekFields.ISO.weekOfWeekBasedYear();
			LocalDate today = LocalDate.now();
			// voor ieder goed resultaat een knop aanmaken
			for (Schoonmaaktaak taak : takenLijst) {
				// als week van uitvoerdatum overeenkomt met de week vandaag
				if (taak.getUitvoerDatum().get(woy) == today.get(woy)) {
					printString += "<input class=\"button\" type=\"button\" value=\"" + taak.getTaakNaam() + "  |  "
							+ taak.getGebouwNaam() + "  |  " + taak.getTijd().toMinutes()
							+ " minuten\" onclick=\"taakButton(" + taak.getTaakNr() + ")\"/>";
				}
			}

			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println(printString);

			// uitvoeren als er wordt gevraagd om een taak op te slaan als
			// voltooid. datum van laatste uitvoering verplaatsen naar vandaag
		} else if (method.equals("voltooid")) {
			int taakNr = Integer.parseInt(req.getParameter("taakNr"));
			Schoonmaaktaak upd = schoonmaaktaakDAO.findByTaakNr(taakNr);
			upd.setDatum(LocalDate.now());
			if (schoonmaaktaakDAO.update(upd, gebruikersnaam)) {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("1");
			} else {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("Opslaan Mislukt");
			}

		}

	}
}