$(document).ready(function () {
  $("#submit").click(function (event) {
    event.preventDefault();

    const email = $("#email").val();
    const password = $("#password").val();
    console.log(email, password);

    $.ajax({
      contentType: "application/json",
      method: "post",
      url: "http://localhost:8080/login",
      data: JSON.stringify({
        email: email,
        password: password,
      }),
    }).done(function (token){
        if(token){
          localStorage.setItem("token", token)
          console.log(token)
          window.location.href = "./index.html"
        }
        else{
            console.log("Login failure")
        }

        
    })
  });
});
