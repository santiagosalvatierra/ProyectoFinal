function pregunta(){  

    swal({
      title: "Success!",
      text: "Redirecting in 2 seconds.",
      type: "success",
      timer: 2000,
      showConfirmButton: false
    }, function(){
          window.location.href = "tupaginaweb.com";
    });
         } 

var myModal = document.getElementById('myModal')
var myInput = document.getElementById('myInput')
         
  myModal.addEventListener('shown.bs.modal', function () {
    myInput.focus()
     })        


/* ANIMACIÓN CARRUSEL INDEX EMPRESA
RESOLVER BIEN!

<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script>
var current = 0;
var imagenes = new Array();
 
$(document).ready(function() {
    var numImages = 6;
    if (numImages <= 3) {
        $('.right-arrow').css('display', 'none');
        $('.left-arrow').css('display', 'none');
    }
 
    $('.left-arrow').on('click',function() {
        if (current > 0) {
            current = current - 1;
        } else {
            current = numImages - 3;
        }
 
        $(".carrusel").animate({"left": -($('#product_'+current).position().left)}, 600);
 
        return false;
    });
 
    $('.left-arrow').on('hover', function() {
        $(this).css('opacity','0.5');
    }, function() {
        $(this).css('opacity','1');
    });
 
    $('.right-arrow').on('hover', function() {
        $(this).css('opacity','0.5');
    }, function() {
        $(this).css('opacity','1');
    });
 
    $('.right-arrow').on('click', function() {
        if (numImages > current + 3) {
            current = current+1;
        } else {
            current = 0;
        }
 
        $(".carrusel").animate({"left": -($('#product_'+current).position().left)}, 600);
 
        return false;
    }); 
 });
</script>
*/