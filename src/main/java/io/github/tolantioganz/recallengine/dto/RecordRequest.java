package io.github.tolantioganz.recallengine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RecordRequest {
    private int problemID;
    private int confidence;
    private String pattern;
    private String failLog;
}
