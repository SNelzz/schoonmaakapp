package nl.hu.ipass.schoonmaakapp.entity;

import java.util.*;

public class Gebruiker {
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private String wachtwoord;
	private String gebruikersnaam;
	private List<Schoonmaaktaak> deTaken;

	public Gebruiker() {
	}

	public Gebruiker(String vn, String tsv, String an, String ww, String gn) {
		voornaam = vn;
		tussenvoegsel = tsv;
		achternaam = an;
		wachtwoord = ww;
		gebruikersnaam = gn;
	}

	public Gebruiker(String vn, String an, String ww, String gn) {
		voornaam = vn;
		achternaam = an;
		wachtwoord = ww;
		gebruikersnaam = gn;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public List<Schoonmaaktaak> getDeTaken() {
		return deTaken;
	}

	public String getVolledigeAchternaam() {
		return tussenvoegsel + " " + achternaam;
	}

	public void setVoornaam(String vn) {
		voornaam = vn;
	}

	public void setAchternaam(String an) {
		achternaam = an;
	}

	public void setTussenvoegsel(String tsv) {
		tussenvoegsel = tsv;
	}

	public void setWachtwoord(String ww) {
		wachtwoord = ww;
	}

	public void setGebruikersnaam(String gn) {
		gebruikersnaam = gn;
	}

	public boolean komtWachtwoordOvereen(String ww, String gn) {
		if (wachtwoord.equals(ww) && gebruikersnaam.equals(gn)) {
			return true;
		}
		return false;
	}

	public void vulTaken(List<Schoonmaaktaak> deTkn) {
		deTaken = deTkn;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		} else {
			Gebruiker gebruiker = (Gebruiker) obj;
			return Objects.equals(gebruikersnaam, (gebruiker.gebruikersnaam));
		}
	}

	public String toString() {
		String s = "gebruiker: " + voornaam + " " + getVolledigeAchternaam() + " " + gebruikersnaam;
		return s;
	}

}
