package nl.hu.ipass.schoonmaakapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.*;
import java.time.format.DateTimeFormatter;

import nl.hu.ipass.schoonmaakapp.entity.Schoonmaaktaak;
import nl.hu.ipass.schoonmaakapp.DAO.SchoonmaaktaakDAO;

//Servlet voor het toevoegen van een schoonmaaktaak
@WebServlet(urlPatterns = "/ToevoegenServlet")
public class ToevoegenServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

		Schoonmaaktaak opslaan = new Schoonmaaktaak(taaknaam, tijdspanne, tijd, date, gebouwnaam);
		SchoonmaaktaakDAO schoonmaaktaakDAO = new SchoonmaaktaakDAO();

		if (schoonmaaktaakDAO.insert(opslaan, gebruikersnaam)) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("Opslaan voltooid");
		} else {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("Opslaan mislukt");
		}
	}
}