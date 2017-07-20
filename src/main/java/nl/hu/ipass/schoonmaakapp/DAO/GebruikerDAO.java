package nl.hu.ipass.schoonmaakapp.DAO;

import java.sql.*;
import java.util.Properties;

import nl.hu.ipass.schoonmaakapp.entity.Gebruiker;

public class GebruikerDAO {
	private static final String DB_DRIV = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://ec2-54-247-166-129.eu-west-1.compute.amazonaws.com:5432/d40ef96pei9a4t?sslmode=require";
	private static final String DB_USER = "dfanurbfauupnh";
	private static final String DB_PASS = "7c9ba1f0d4a7bf482de582453ed9d9f0db1c51c9d9283e99f627fd9da6cd42cb";
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
