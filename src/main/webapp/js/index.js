$(document).ready(function() {
	
	//wisselen tussen login en register form
	$('.mess a').click(function(){
		$('form').animate({height: "toggle", opacity: "toggle"}, "slow");
	});
	
	//uitvoeren als de login knop wordt geklikt
	 $("#loginButton").click(function(e){
		 dataString = $("#login-form").serialize();
		 console.log(dataString);
		 $.ajax({
             type: "GET",
             url: "/schoonmaakapp/LoginServlet",
             data: dataString,
             dataType: "text",
             
             success: function(d) {
            	 if(d == 1){
            		 //inloggen door gebruikersnaam op te slaan in sessionStorage
            		 var x = document.getElementById("gebruikersnaam").value;
            		 window.sessionStorage.setItem("gebruikersnaam", x);
            		 window.location.assign("menu.html");
            		 
            	 } else {
            		 //foutmelding weergeven
            		 document.getElementById("p1").style.display = "block";
                	 document.getElementById("p1").innerHTML = d;
            	 }
             },
             
             error: function(jqXHR, textStatus, errorThrown){
                 console.log("error: " + textStatus);
            },
             
		 });
	 });
	 
	 //uitvoeren als de registreer knop wordt geklikt
	 $("#regButton").click(function(e){
		 dataString1 = $("#register-form").serialize();
		 
		 $.ajax({
             type: "GET",
             url: "/schoonmaakapp/RegisterServlet",
             data: dataString1,
             dataType: "text",
             
             success: function(d) {
            	 //melding weergeven
            	 document.getElementById("p2").style.display = "block";
            	 document.getElementById("p2").innerHTML = d;
             },
             
             error: function(jqXHR, textStatus, errorThrown){
                 console.log("error: " + textStatus);
            },
             
		 });
	 });
});
