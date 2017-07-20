$(document).ready(function() {
	//terugsturen naar index.html als er niet is ingelogd
	if(window.sessionStorage.getItem("gebruikersnaam") === null) {
		window.location.assign("index.html");
	}
	
	//uitvoeren als de uitlog knop wordt geklikt
	 $("#loguitButton").click(function(e){
		 window.sessionStorage.clear();
		 window.location.assign("index.html");
	 });
});
