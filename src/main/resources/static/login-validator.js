function validate() {
    var loginField = document.getElementById("login-field");
    var passField = document.getElementById("pass-field");

    var regex = /^[A-Za-z0-9]{5,}$/;

    if(!regex.test(loginField.value)) {
        return false;
    }

    if(!regex.test(passField.value)) {
        return false;
    }

    return true;
}