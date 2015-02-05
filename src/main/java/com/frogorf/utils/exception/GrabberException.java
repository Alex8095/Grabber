package com.frogorf.utils.exception;

import com.frogorf.grabber.domain.TaskHistory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Alex on 19.01.15.
 */
public class GrabberException {
    public static String format(Exception e) {
        StringBuffer sb = new StringBuffer();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        sb.append("message:");
        sb.append(e.getMessage());
        sb.append(", ");
        sb.append("stacktrace:");
        sb.append(sw.toString());
        return sb.toString();
    }
}
