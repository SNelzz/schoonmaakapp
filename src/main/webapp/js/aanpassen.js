//bij het openen van de pagina direct de gegevens van de taak laden
$(document).ready(function() {
	//terugsturen naar index.html als er niet is ingelogd
	if(window.sessionStorage.getItem("gebruikersnaam") === null) {
		window.location.assign("index.html");
	}
	
	dataString = "taakNr=" + window.sessionStorage.getItem("taakNr") + "&method=ophalen";
	$.ajax({
		 type: "GET",
		 url: "/schoonmaakapp/AanpassenServlet",
		 data: dataString,
		 dataType: "JSON",
       
		 success: function(d) {
			 //inputs vullen met waardes van het JSON object
			 document.getElementById("taaknaam").value = d.taakNaam;
			 document.getElementById("tijdspanne1").value = d.tijdspanne;
			 document.getElementById("tijdspanne2").value = "dagen";
			 document.getElementById("tijd1").value = d.tijd;
			 document.getElementById("tijd2").value = "minuten";
			 document.getElementById("datum").value = d.datum;
			 document.getElementById("gebouwnaam").value = d.gebouwnaam;
		 },
       
		 error: function(jqXHR, textStatus, errorThrown){
			 console.log("error: " + textStatus);
		 },
       
	 });
	
	//uitvoeren als de opslaan knop wordt geklikt
	$("#taakButton").click(function(e){
		 dataString = $("#aanpassen-form").serialize();
		 dataString = dataString + "&gebruikersnaam=" + window.sessionStorage.getItem("gebruikersnaam") + "&method=aanpassen" + "&taakNr=" + window.sessionStorage.getItem("taakNr");
		 console.log(dataString)
		 
		 //verplichte velden controleren op invoer
		 if(document.getElementById("taaknaam").value.length < 1) {
			 document.getElementById("p4").style.display = "block";
			 document.getElementById("p4").innerHTML = "Vul een taaknaam in";
		 } else if(document.getElementById("tijdspanne1").value.length < 1) {
			 document.getElementById("p4").style.display = "block";
			 document.getElementById("p4").innerHTML = "Vul de tijd tussen het uitvoeren in";
		 } else if(document.getElementById("tijdspanne2").value.length < 1) {
			 document.getElementById("p4").style.display = "block";
			 document.getElementById("p4").innerHTML = "kies een eenheid";
		 } else if(document.getElementById("datum").value.length < 1) {
			 document.getElementById("p4").style.display = "block";
			 document.getElementById("p4").innerHTML = "Vul een datum in";
		 } else {
			 
			 $.ajax({
				 type: "GET",
				 url: "/schoonmaakapp/AanpassenServlet",
				 data: dataString,
				 dataType: "text",
            
				 success: function(d) {
					 if(d == 1){
						 window.sessionStorage.removeItem("taakNr");
						 window.location.assign("aanpaslijst.html");
	            		 
	            	 } else {
	            		 document.getElementById("p4").style.display = "block";
	                	 document.getElementById("p4").innerHTML = d;
	            	 }
				 },
            
				 error: function(jqXHR, textStatus, errorThrown){
					 console.log("error: " + textStatus);
				 },
            
		 	});
		 }
	 });
	
	//uitvoeren als de annuleer knop is geklikt
	 $("#anulButton").click(function(e){
		 window.sessionStorage.removeItem("taakNr");
		 window.location.assign("aanpaslijst.html");	 
	 });
	 
	 //uitvoeren als de verwijder knop is geklikt
	 $("#delButton").click(function(e){
		 dataString = "taakNr=" + window.sessionStorage.getItem("taakNr") + "&method=verwijderen";
		 
		 $.ajax({
			 type: "GET",
			 url: "/schoonmaakapp/AanpassenServlet",
			 data: dataString,
			 dataType: "JSON",
	       
			 success: function(d) {
				 if(d == 1){
					 window.sessionStorage.removeItem("taakNr");
					 window.location.assign("aanpaslijst.html");
            		 
            	 } else {
            		 document.getElementById("p4").style.display = "block";
                	 document.getElementById("p4").innerHTML = d;
            	 }
			 },
	       
			 error: function(jqXHR, textStatus, errorThrown){
				 console.log("error: " + textStatus);
			 },
	       
		 });	 
	 });
});
