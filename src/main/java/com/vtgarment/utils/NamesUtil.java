package com.vtgarment.utils;

import lombok.Getter;

@Getter
public enum NamesUtil {
    LOGIN_PAGE("/login.xhtml"),
    MAIN_PAGE("/site/overAll.xhtml"),
    EDIT_PROFILE_PAGE("/site/editProfile.xhtml"),
    DIALOG_NAME("msgBoxSystemMessageDlg")
    ;
    private String name;

    NamesUtil(String name) {
        this.name = name;
    }
}
