package nl.hu.ipass.schoonmaakapp.DAO;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.time.*;

import nl.hu.ipass.schoonmaakapp.entity.Schoonmaaktaak;

public class SchoonmaaktaakDAO {
	private static final String DB_DRIV = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://ec2-54-247-166-129.eu-west-1.compute.amazonaws.com:5432/d40ef96pei9a4t?sslmode=require";
	private static final String DB_USER = "dfanurbfauupnh";
	private static final String DB_PASS = "7c9ba1f0d4a7bf482de582453ed9d9f0db1c51c9d9283e99f627fd9da6cd42cb";
	private static Connection conn;

	// alle schoonmaaktaken van een gebruiker vinden op basis van gebruikersnaam
	public List<Schoonmaaktaak> findByGebruikersnaam(String gn) {
		List<Schoonmaaktaak> resultaat = new ArrayList<Schoonmaaktaak>();
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM schoonmaaktaak WHERE gebruikersnaam = \'" + gn + "\'");

			// resultaat van SQL query doorlopen en resultaat List vullen
			while (rs.next()) {
				int tknr = rs.getInt("taakNr");
				String tknm = rs.getString("taakNaam");
				Period tdsp = Period.ofDays(rs.getInt("tijdspanne"));
				Duration td = Duration.ofMinutes(rs.getInt("tijd"));
				LocalDate date = rs.getDate("datum").toLocalDate();
				String gnm = rs.getString("gebouwNaam");
				Schoonmaaktaak toev = new Schoonmaaktaak(tknr, tknm, tdsp, td, date, gnm);
				resultaat.add(toev);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return resultaat;
	}

	// een schoonmaaktaak vinden op basis van taakNr
	public Schoonmaaktaak findByTaakNr(int tknr) {
		Schoonmaaktaak resultaat = new Schoonmaaktaak();
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM schoonmaaktaak WHERE taakNr = " + tknr);

			// resultaat van SQL query doorlopen en resultaat Schoonmaaktaak
			// vullen
			while (rs.next()) {
				int tnr = rs.getInt("taakNr");
				String tknm = rs.getString("taakNaam");
				Period tdsp = Period.ofDays(rs.getInt("tijdspanne"));
				Duration td = Duration.ofMinutes(rs.getInt("tijd"));
				LocalDate date = rs.getDate("datum").toLocalDate();
				String gbn = rs.getString("gebouwNaam");
				resultaat.setTaakNr(tnr);
				resultaat.setTaakNaam(tknm);
				resultaat.setTijdspanne(tdsp);
				resultaat.setTijd(td);
				resultaat.setDatum(date);
				resultaat.setGebouwNaam(gbn);
				resultaat.setUitvoerDatum();
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return resultaat;
	}

	// nieuwe schoonmaaktaak toevoegen aan de database. return true als gelukt
	public boolean insert(Schoonmaaktaak sch, String gbn) {
		boolean resultaat = false;
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			int tijdsp = sch.getTijdspanne().getDays();
			long td = sch.getTijd().toMinutes();
			Date date = Date.valueOf(sch.getDatum());
			if (stmt.executeUpdate(
					"INSERT INTO schoonmaaktaak (gebruikersnaam, taakNaam, tijdspanne, tijd, datum, gebouwNaam) VALUES(\'"
							+ gbn + "\', \'" + sch.getTaakNaam() + "\', " + tijdsp + ", " + td + ", \'"
							+ date.toString() + "\', \'" + sch.getGebouwNaam() + "\')") == 1) {
				resultaat = true;
			}

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return resultaat;
	}

	// een schoonmaaktaak aanpassen in de database. return true als gelukt
	public boolean update(Schoonmaaktaak sch, String gbn) {
		boolean resultaat = false;
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			int tijdsp = sch.getTijdspanne().getDays();
			long td = sch.getTijd().toMinutes();
			Date date = Date.valueOf(sch.getDatum());
			if (stmt.executeUpdate("UPDATE schoonmaaktaak SET gebruikersnaam = \'" + gbn + "\', taakNaam = \'"
					+ sch.getTaakNaam() + "\', tijdspanne = " + tijdsp + ", tijd = " + td + ", datum = \'"
					+ date.toString() + "\', gebouwNaam = \'" + sch.getGebouwNaam() + "\' WHERE taakNr = "
					+ sch.getTaakNr()) == 1) {
				resultaat = true;
			}

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return resultaat;
	}

	// schoonmaaktaak verwijderen uit de database op basis van taakNr. return
	// true als gelukt
	public boolean delete(int tknr) {
		boolean resultaat = false;
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			if (stmt.executeUpdate("DELETE FROM schoonmaaktaak WHERE taakNr = " + tknr) == 1) {
				resultaat = true;
			}

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return resultaat;
	}

}
