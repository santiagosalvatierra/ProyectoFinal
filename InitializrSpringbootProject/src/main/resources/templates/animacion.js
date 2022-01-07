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


     $(".clasificacion").find("input").change(function(){
        var valor = $(this).val()
        $(".clasificacion").find("input").removeClass("activo")
        $(".clasificacion").find("input").each(function(index){
           if(index+1<=valor){
            $(this).addClass("activo")
           }
           
        })
      })
      
      $(".clasificacion").find("label").mouseover(function(){
        var valor = $(this).prev("input").val()
        $(".clasificacion").find("input").removeClass("activo")
        $(".clasificacion").find("input").each(function(index){
           if(index+1<=valor){
            $(this).addClass("activo")
           }
           
        })
      })