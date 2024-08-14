package com.revature.exceptions;

import java.util.HashMap;
import java.util.Map;

public class InvalidUserException extends CustomException{
    private Map<String, String> map;
    public InvalidUserException() {
        map = new HashMap<>();
    }

    // to send a response like this:
    // { "field1_name" : "error message", "field2_name" : "error message", ...} easier to show in the front-end
    public void addMessage(String field, String msg){
        map.put(field, msg);
    }

    @Override
    public Object getMsg() {
        return map;
    }
}
