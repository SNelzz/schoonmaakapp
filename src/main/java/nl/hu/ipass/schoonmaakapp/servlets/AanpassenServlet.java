package nl.hu.ipass.schoonmaakapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.hu.ipass.schoonmaakapp.entity.Schoonmaaktaak;
import nl.hu.ipass.schoonmaakapp.DAO.SchoonmaaktaakDAO;

//Servlet voor het ophalen, aanpassen en verwijderen van een schoonmaaktaak in de databse
@WebServlet(urlPatterns = "/AanpassenServlet")
public class AanpassenServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		int taakNr = Integer.parseInt(req.getParameter("taakNr"));

		SchoonmaaktaakDAO schoonmaaktaakDAO = new SchoonmaaktaakDAO();

		// uitvoeren als er wordt gevraagd om gegevens op te halen
		if (method.equals("ophalen")) {
			Schoonmaaktaak oph = schoonmaaktaakDAO.findByTaakNr(taakNr);

			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");

			// JSON object aanmaken en versturen
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("taakNaam", oph.getTaakNaam()).add("tijdspanne", oph.getTijdspanne().getDays())
					.add("tijd", oph.getTijd().toMinutes()).add("datum", oph.getDatum().toString())
					.add("gebouwnaam", oph.getGebouwNaam());
			out.println(builder.build());

			// uitvoeren als er wordt gevraagd om een schoonmaaktaak aan te
			// passen
		} else if (method.equals("aanpassen")) {
			String taaknaam = req.getParameter("taaknaam");
			String tijdspanne1 = req.getParameter("tijdspanne1");
			String tijdspanne2 = req.getParameter("tijdspanne2");
			String tijd1 = req.getParameter("tijd1");
			String tijd2 = req.getParameter("tijd2");
			String datum = req.getParameter("datum");
			String gebouwnaam = req.getParameter("gebouwnaam");
			String gebruikersnaam = req.getParameter("gebruikersnaam");

			// gegeven tijdspanne omzetten naar Period object
			int tijdsp1 = Integer.parseInt(tijdspanne1);
			Period tijdspanne = Period.ofDays(0);
			if (tijdspanne2.equals("dagen")) {
				tijdspanne = Period.ofDays(tijdsp1);
			} else if (tijdspanne2.equals("weken")) {
				tijdspanne = Period.ofWeeks(tijdsp1);
			} else if (tijdspanne2.equals("maanden")) {
				tijdspanne = Period.ofDays(tijdsp1 * 30);
			}

			// gegeven tijd omzetten naar Duration object
			Duration tijd = Duration.ofMinutes(0);
			if (!tijd1.equals("")) {
				int td1 = Integer.parseInt(tijd1);
				if (tijd2.equals("minuten")) {
					tijd = Duration.ofMinutes(td1);
				} else if (tijd2.equals("uren")) {
					tijd = Duration.ofHours(td1);
				}
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(datum, formatter);

			Schoonmaaktaak aanpassen = new Schoonmaaktaak(taakNr, taaknaam, tijdspanne, tijd, date, gebouwnaam);

			if (schoonmaaktaakDAO.update(aanpassen, gebruikersnaam)) {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("1");
			} else {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("Opslaan mislukt");
			}

			// uitvoeren als er wordt gevraagd om een schoonmaaktaak te
			// verwijderen
		} else if (method.equals("verwijderen")) {
			if (schoonmaaktaakDAO.delete(taakNr)) {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("1");

			} else {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("Verwijderen mislukt");
			}
		}
	}
}