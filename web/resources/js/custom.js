function Redirect(value, path) {

    if(value == 'OTP'){
        window.location = "../site/otp.xhtml"
    } else if(value == 'REWORK'){
        window.location = "../site/rework.xhtml"
    } else if(value == 'BREAK DOWN'){
        window.location = "../site/breakDown.xhtml"
    } else if(value == 'OUTSTANDING'){
        window.location = "../site/outstading.xhtml"
    }

}