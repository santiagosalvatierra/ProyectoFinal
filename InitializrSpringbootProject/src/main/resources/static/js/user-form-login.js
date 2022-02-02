/*creamos las variables de las class del html para manipularlas*/ 
let login = document.querySelectorAll(".login")[0];
let cerrarL = document.querySelectorAll(".close-login")[0];
let modalLogin = document.querySelectorAll(".modal-login")[0];
let modalL = document.querySelectorAll(".modal-container-login")[0];

login.addEventListener("click", function(e){
    e.preventDefault();
    modalL.style.opacity = "1";
    modalL.style.visibility = "visible";
    modalLogin.classList.toggle("modal-close-login"); //toggle hace que cada vez que se seleccione el boton de modal va a activar  la clase modal-close/    
});

cerrarL.addEventListener("click", function(){
    modalLogin.classList.toggle("modal-close-login");
    
    setTimeout(function(){
        modalL.style.opacity = "0";
        modalL.style.visibility = "hidden";
    },500)
});

window.addEventListener("click", function(e){
    
    if(e.target == modalL){
        modalLogin.classList.toggle("modal-close-login");
        
        setTimeout(function(){
            modalL.style.opacity = "0";
            modalL.style.visibility = "hidden";
        },500)
    }
});