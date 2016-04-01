function Redirect(value, path) {

    if(value == 'OTP'){
        window.location = "/test/site/otp.xhtml"
    } else if(value == 'REWORK'){
        window.location = "/test/site/rework.xhtml"
    } else if(value == 'BREAK DOWN'){
        window.location = "/test/site/breakDown.xhtml"
    } else if(value == 'OUTSTANDING'){
        window.location = "/test/site/outstading.xhtml"
    }

}