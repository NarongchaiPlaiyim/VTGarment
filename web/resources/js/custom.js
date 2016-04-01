function Redirect(value, path) {
    switch (value) {
        case 'OTP':
            window.location = "../site/otp.xhtml"
            break;
        case 'REWORK':
            window.location = "../site/rework.xhtml"
            break;
        case 'BREAK DOWN':
            window.location = "../site/breakDown.xhtml"
            break;
        case 'OUTSTANDING':
            window.location = "../site/outstading.xhtml"
            break;
    }
}