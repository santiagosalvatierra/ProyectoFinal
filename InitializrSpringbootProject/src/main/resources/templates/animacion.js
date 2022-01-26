function pregunta() {

  swal({
    title: "Success!",
    text: "Redirecting in 2 seconds.",
    type: "success",
    timer: 2000,
    showConfirmButton: false
  }, function () {
    window.location.href = "tupaginaweb.com";
  });
}

var myModal = document.getElementById('myModal')
var myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', function () {
  myInput.focus()
})

/* OPCION PARA QUE NO SE CIERRE EL MODAL
#1
onclick="logincheck()"
alert(sweetAlert);
event.stopPropagation();  


#2
<script>
$(window).on("navigate", function (event, data) {
  var direction = data.state.direction;
  if (direction == 'back') {
    event.preventDefault();//evita hacer la acción por defecto, volver atrás
    $(elementoModal).hide();
    window.history.forward(1);//volvemos donde estabamos
  }
});
</script>

*/



}

/* 
function stars() {
  $(".clasificacion").find("input").change(function () {
    var valor = $(this).val()
    $(".clasificacion").find("input").removeClass("activo")
    $(".clasificacion").find("input").each(function (index) {
      if (index + 1 <= valor) {
        $(this).addClass("activo")
      }

    })
  })

  $(".clasificacion").find("label").mouseover(function () {
    var valor = $(this).prev("input").val()
    $(".clasificacion").find("input").removeClass("activo")
    $(".clasificacion").find("input").each(function (index) {
      if (index + 1 <= valor) {
        $(this).addClass("activo")
      }

    })
  })
}
*/