package com.example.epamhotelspring.forms;

import com.example.epamhotelspring.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CloseRequestForm {

    private String comment;

    private Long requestId;

    public void setComment(String comment) {
        comment = StringUtils.removeRegexFromString(comment);
        this.comment = comment;
    }
}
