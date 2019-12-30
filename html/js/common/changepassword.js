function checkPassword() {
   var oldPassword = document.getElementById("oldPassword");
   var newPassword = document.getElementById("newPassword");
   var copyPassword = document.getElementById("copyPassword");
   if (newPassword.value.length < 1 || copyPassword.value.length < 1) {
      alert(messagesData.passwordMatchError);
      return false;
   }else if (newPassword.value != copyPassword.value) {
      alert(messagesData.passwordMatchError);
      return false;
   }else if (oldPassword.value == newPassword.value) {
      alert(messagesData.passwordReuseError);
      return false;
   }else {
      var action = document.getElementById("action");
      action.value = "changePassword";
      return true;
   }
}

function resetPassword() {
   var oldPassword = document.getElementById("oldPassword");
   var newPassword = document.getElementById("newPassword");
   var copyPassword = document.getElementById("copyPassword");
   var action = document.getElementById("action");
   oldPassword.value = "";
   newPassword.value = "";
   copyPassword.value = "";
   action.value = "";
   return false;
}