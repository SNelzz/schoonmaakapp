package nl.hu.ipass.schoonmaakapp.DAO;

import java.sql.*;

import nl.hu.ipass.schoonmaakapp.entity.Gebruiker;

public class GebruikerDAO {
	private static final String DB_DRIV = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/schoonmaakapp";
	private static final String DB_USER = "Niels";
	private static final String DB_PASS = "IPASS2017ND";
	private static Connection conn;

	// Een gebruiker laden uit de database op basis van gebruikersnaam
	public Gebruiker findByGebruikersnaam(String gn) {
		Gebruiker resultaat = new Gebruiker();
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM gebruiker WHERE gebruikersnaam = \'" + gn + "\'");

			// resultaat van SQL query doorlopen en resultaat gebruiker vullen
			while (rs.next()) {
				String vn = rs.getString("voornaam");
				String tsv = rs.getString("tussenvoegsel");
				String an = rs.getString("achternaam");
				String ww = rs.getString("wachtwoord");
				resultaat.setVoornaam(vn);
				resultaat.setTussenvoegsel(tsv);
				resultaat.setAchternaam(an);
				resultaat.setWachtwoord(ww);
				resultaat.setGebruikersnaam(gn);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		return resultaat;
	}

	// nieuwe gebruiker toevoegen aan de database. return true als gelukt
	public boolean insert(Gebruiker geb) {
		boolean resultaat = false;
		try {
			Class.forName(DB_DRIV).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Gebruiker check = findByGebruikersnaam(geb.getGebruikersnaam());

		// controleren of de gebruikersnaam al bestaat
		if (!geb.equals(check)) {
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

				Statement stmt = conn.createStatement();
				if (stmt.executeUpdate("INSERT INTO gebruiker VALUES(\'" + geb.getGebruikersnaam() + "\', \'"
						+ geb.getWachtwoord() + "\', \'" + geb.getVoornaam() + "\', \'" + geb.getAchternaam() + "\', \'"
						+ geb.getTussenvoegsel() + "\')") == 1) {
					resultaat = true;
				}

				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return resultaat;
	}

}
