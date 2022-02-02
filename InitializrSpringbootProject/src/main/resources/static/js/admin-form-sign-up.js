//REGISTRO MODAL ADMIN
let cerrarA = document.querySelectorAll(".close-admin")[0];
let abrirA = document.querySelectorAll(".cta-admin")[0];
let modalA = document.querySelectorAll(".modalA")[0];
let modalCA = document.querySelectorAll(".modal-container-admin")[0];

/*Este método hace que a través de la variable abrir, que corresponde a la clase de html,
el modal se pueda visualizar.*/
abrirA.addEventListener("click", function (e) {
    e.preventDefault();
    modalCA.style.opacity = "1";
    modalCA.style.visibility = "visible";
    modalA.classList.toggle("modal-close-admin");

}); cerrarA.addEventListener("click", function () {
    modalA.classList.toggle("modal-close-admin");

    setTimeout(function () {
        modalCA.style.opacity = "0";
        modalCA.style.visibility = "hidden";
    }, 500)
});
window.addEventListener("click", function (e) {
    console.log(e.target)
    if (e.target == modalCA) {
        modalA.classList.toggle("modal-close-admin");

        setTimeout(function () {
            modalCA.style.opacity = "0";
            modalCA.style.visibility = "hidden";
        }, 400)
    }
});