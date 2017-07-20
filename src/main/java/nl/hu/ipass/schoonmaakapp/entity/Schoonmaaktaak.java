package nl.hu.ipass.schoonmaakapp.entity;

import java.time.*;
import java.util.*;

public class Schoonmaaktaak {
	private int taakNr;
	private String taakNaam;
	private Period tijdspanne;
	private Duration tijd;
	private LocalDate datum;
	private String gebouwNaam;
	private LocalDate uitvoerDatum;

	public Schoonmaaktaak() {
	}

	public Schoonmaaktaak(int tknr, String tknm, Period tdsp, Duration td, LocalDate dtm, String gbn) {
		taakNr = tknr;
		taakNaam = tknm;
		tijdspanne = tdsp;
		tijd = td;
		datum = dtm;
		gebouwNaam = gbn;
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public Schoonmaaktaak(String tknm, Period tdsp, Duration td, LocalDate dtm, String gbn) {
		taakNaam = tknm;
		tijdspanne = tdsp;
		tijd = td;
		datum = dtm;
		gebouwNaam = gbn;
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public Schoonmaaktaak(int tknr, String tknm, Period tdsp, Duration td, LocalDate dtm) {
		taakNr = tknr;
		taakNaam = tknm;
		tijdspanne = tdsp;
		tijd = td;
		datum = dtm;
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public Schoonmaaktaak(int tknr, String tknm, Period tdsp, LocalDate dtm) {
		taakNr = tknr;
		taakNaam = tknm;
		tijdspanne = tdsp;
		datum = dtm;
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public Schoonmaaktaak(int tknr, String tknm, Period tdsp, LocalDate dtm, String gbn) {
		taakNr = tknr;
		taakNaam = tknm;
		tijdspanne = tdsp;
		datum = dtm;
		gebouwNaam = gbn;
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public int getTaakNr() {
		return taakNr;
	}

	public String getTaakNaam() {
		return taakNaam;
	}

	public Period getTijdspanne() {
		return tijdspanne;
	}

	public Duration getTijd() {
		return tijd;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public String getGebouwNaam() {
		return gebouwNaam;
	}

	public LocalDate getUitvoerDatum() {
		return uitvoerDatum;
	}

	public void setUitvoerDatum() {
		uitvoerDatum = datum.plus(tijdspanne);
	}

	public void setTaakNr(int tknr) {
		taakNr = tknr;
	}

	public void setTaakNaam(String tknm) {
		taakNaam = tknm;
	}

	public void setTijdspanne(Period tdsp) {
		tijdspanne = tdsp;
	}

	public void setTijd(Duration td) {
		tijd = td;
	}

	public void setDatum(LocalDate dtm) {
		datum = dtm;
	}

	public void setGebouwNaam(String gbn) {
		gebouwNaam = gbn;
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
			Schoonmaaktaak taak = (Schoonmaaktaak) obj;
			return Objects.equals(taakNr, (taak.taakNr));
		}
	}

	public String toString() {
		String s = "Schoonmaaktaak: " + taakNr + " " + taakNaam + " " + tijdspanne + " " + tijd + " " + datum + " "
				+ gebouwNaam;
		return s;
	}

}
