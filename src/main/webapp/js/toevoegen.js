$(document).ready(function() {
	//terugsturen naar index.html als er niet is ingelogd
	if(window.sessionStorage.getItem("gebruikersnaam") === null) {
		window.location.assign("index.html");
	} 
	
	//uitvoeren als de opslaan knop wordt geklikt
	 $("#taakButton").click(function(e){
		 dataString = $("#toevoegen-form").serialize();
		 dataString = dataString + "&gebruikersnaam=" + window.sessionStorage.getItem("gebruikersnaam");
		 
		//verplichte velden controleren op invoer
		 if(document.getElementById("taaknaam").value.length < 1) {
			 document.getElementById("p3").style.display = "block";
			 document.getElementById("p3").innerHTML = "Vul een taaknaam in";
		 } else if(document.getElementById("tijdspanne1").value.length < 1) {
			 document.getElementById("p3").style.display = "block";
			 document.getElementById("p3").innerHTML = "Vul de tijd tussen het uitvoeren in";
		 } else if(document.getElementById("tijdspanne2").value.length < 1) {
			 document.getElementById("p3").style.display = "block";
			 document.getElementById("p3").innerHTML = "kies een eenheid";
		 } else if(document.getElementById("datum").value.length < 1) {
			 document.getElementById("p3").style.display = "block";
			 document.getElementById("p3").innerHTML = "Vul een datum in";
		 } else {
			 
			 $.ajax({
				 type: "GET",
				 url: "/schoonmaakapp/ToevoegenServlet",
				 data: dataString,
				 dataType: "text",
             
				 success: function(d) {
					//melding geven
					 document.getElementById("p3").style.display = "block";
					 document.getElementById("p3").innerHTML = d;
				 },
             
				 error: function(jqXHR, textStatus, errorThrown){
					 console.log("error: " + textStatus);
				 },
             
		 	});
		 }
	 });
	 
	//uitvoeren als de annuleer knop is geklikt
	 $("#anulButton").click(function(e){
		 window.location.assign("menu.html");	 
	 });
});
