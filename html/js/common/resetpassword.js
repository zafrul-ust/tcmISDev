function checkPassword() {
   var newPassword = document.getElementById("newPassword");
   var copyPassword = document.getElementById("copyPassword");
   if (newPassword.value.length < 1 || copyPassword.value.length < 1) {
      alert(messagesData.passwordMatchError);
      return false;
   }else if (newPassword.value != copyPassword.value) {
      alert(messagesData.passwordMatchError);
      return false;
   }else {
      var action = document.getElementById("action");
      action.value = "resetPassword";
      return true;
   }
}

