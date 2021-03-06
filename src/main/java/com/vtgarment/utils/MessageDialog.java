package com.vtgarment.utils;

import lombok.Getter;

@Getter
public enum MessageDialog {
    ERROR("Error", ""),
    EDIT("Edit", "Successfully edited."),
    CREATE("Create", "Successfully created."),
    DELETE("Delete", "Successfully deleted."),
    UPDATE("Update", "Successfully updated."),
    WARNING("Warning", ""),
    POST("Post", "Successfully Closed"),
    SAVE("Save", "Successfully saved.");
    private String messageHeader;
    private String message;

    MessageDialog(String messageHeader, String message) {
        this.messageHeader = messageHeader;
        this.message = message;
    }
}
