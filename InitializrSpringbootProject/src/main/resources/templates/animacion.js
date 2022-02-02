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
