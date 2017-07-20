//bij het openen van de pagina direct de lijst laden
$(document).ready(function() {	
	//terugsturen naar index.html als er niet is ingelogd
	if(window.sessionStorage.getItem("gebruikersnaam") === null) {
		window.location.assign("index.html");
	} 
	
	dataString = "gebruikersnaam=" + window.sessionStorage.getItem("gebruikersnaam");
	 
	 $.ajax({
		 type: "GET",
		 url: "/schoonmaakapp/AanpaslijstServlet",
		 data: dataString,
		 dataType: "text",
        
		 success: function(d) {
			 //knoppen in <div> zetten en annuleerknop toevoegen
			 document.getElementById("aanpassenlijst").innerHTML = d + "<input class=\"button\" type=\"button\" value=\"Annuleren\" id=\"anulButton\" onclick=\"annuleren()\" />";
		 },
        
		 error: function(jqXHR, textStatus, errorThrown){
			 console.log("error: " + textStatus);
		 },
        
	 });
});

//uitvoeren als annuleerknop wordt geklikt
function annuleren(){
	 window.location.assign("menu.html");	 
}

//uitvoeren als een taak wordt geselecteerd
function taakButton(i){
	window.sessionStorage.setItem("taakNr", i);
	window.location.assign("aanpassen.html");
}
