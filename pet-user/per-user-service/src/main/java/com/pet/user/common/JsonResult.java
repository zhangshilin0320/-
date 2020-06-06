package com.pet.user.common;


import java.io.Serializable;

import lombok.Data;

/**
 * @author Dragon_Kater
 *
 */
@Data
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 2944384082936622509L;

    private Integer state = 1;

    private String message = "ok";

    private Object data;

    public JsonResult() { }

    public JsonResult(String message) {
        this.message = message;
    }

    public JsonResult(Object data) {
        this.data = data;
    }

    public JsonResult(Throwable t){
        this.state = 0;
        this.message = t.getMessage();
    }
}