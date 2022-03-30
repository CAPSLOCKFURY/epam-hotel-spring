package com.example.epamhotelspring.forms;

import com.example.epamhotelspring.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeclineRoomForm {

    private String comment;

    public void setComment(String comment) {
        this.comment = StringUtils.removeRegexFromString(comment);
    }

}
