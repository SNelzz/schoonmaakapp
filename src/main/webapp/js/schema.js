//bij het openen van de pagina direct de lijst laden
$(document).ready(function() {
	//terugsturen naar index.html als er niet is ingelogd
	if(window.sessionStorage.getItem("gebruikersnaam") === null) {
		window.location.assign("index.html");
	}
	
	 dataString = "gebruikersnaam=" + window.sessionStorage.getItem("gebruikersnaam") + "&method=ophalen";
	 
	 $.ajax({
		 type: "GET",
		 url: "/schoonmaakapp/SchemaServlet",
		 data: dataString,
		 dataType: "text",
        
		 success: function(d) {
			//knoppen in <div> zetten en annuleerknop toevoegen
			 document.getElementById("schema").innerHTML = d + "<input class=\"button\" type=\"button\" value=\"Annuleren\" id=\"anulButton\" onclick=\"annuleren()\" />";
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
	dataString = "gebruikersnaam=" + window.sessionStorage.getItem("gebruikersnaam") + "&taakNr=" + i + "&method=voltooid";
	$.ajax({
		 type: "GET",
		 url: "/schoonmaakapp/SchemaServlet",
		 data: dataString,
		 dataType: "text",
       
		 success: function(d) {
			 //schema herladen nadat taak als voltooid is opgeslagen
			 window.location.assign("schema.html");
		 },
       
		 error: function(jqXHR, textStatus, errorThrown){
			 console.log("error: " + textStatus);
		 },
       
	 });
}