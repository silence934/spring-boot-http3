package com.example.springboot.vo;

import lombok.Data;
import org.springframework.http.codec.multipart.FilePart;

/**
 * @author: silence
 * @Date: 2022/8/5 17:20
 * @Description:
 */
@Data
public class TestVO {

    private Integer code;

    private String message;

    private Object data;

    private String operationsName;

    private String fileName;

    private FilePart file;

    public String getName() {
        return "123";
    }

}
