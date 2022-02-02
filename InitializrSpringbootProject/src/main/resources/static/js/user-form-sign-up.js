/*creamos las variables de las class del html para manipularlas*/ 
let cerrar = document.querySelectorAll(".close-signup")[0];
let abrir = document.querySelectorAll(".cta")[0];
let modal = document.querySelectorAll(".modal-signup")[0];
let modalC = document.querySelectorAll(".modal-container-signup")[0];

abrir.addEventListener("click", function(e){
    e.preventDefault();
    modalC.style.opacity = "1";
    modalC.style.visibility = "visible";
    modal.classList.toggle("modal-close-signup"); /*toggle hace que cada vez que se seleccione el boton de modal va a activar  la clase modal-close*/    
});

cerrar.addEventListener("click", function(){
    modal.classList.toggle("modal-close-signup");
    
    setTimeout(function(){
        modalC.style.opacity = "0";
        modalC.style.visibility = "hidden";
    },500)
});

window.addEventListener("click", function(e){
    console.log("hola");
    if(e.target == modalC){
        modal.classList.toggle("modal-close-signup");
        
        setTimeout(function(){
            modalC.style.opacity = "0";
            modalC.style.visibility = "hidden";
        },500)
    }
})