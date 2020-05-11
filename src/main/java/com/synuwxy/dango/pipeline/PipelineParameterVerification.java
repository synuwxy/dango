package com.synuwxy.dango.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class PipelineParameterVerification {

    List<String> errMessages = new ArrayList<>();

    public void add(String message) {
        this.errMessages.add(message);
    }

    public void verify() {
        if (!errMessages.isEmpty()) {
            StringBuilder fullMessageBuilder = new StringBuilder();
            for (String errMessage:errMessages) {
                fullMessageBuilder.append(errMessage).append("\n");
            }
            throw new RuntimeException(fullMessageBuilder.toString());
        }
    }

    public static PipelineParameterVerification create() {
        return new PipelineParameterVerification();
    }
}
