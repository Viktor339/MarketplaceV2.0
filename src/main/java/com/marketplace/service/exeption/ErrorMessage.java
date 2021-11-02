package com.marketplace.service.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Map<String,Object> resultMessage = new LinkedHashMap<>();

    public ErrorMessage(LocalDateTime timestamp, int status, String error, String massage){
        resultMessage.put("timestamp", timestamp);
        resultMessage.put("status",status);
        resultMessage.put("error",error);
        resultMessage.put("massage",massage);
    }
    public Map<String,Object> getMessage(){
        return resultMessage;
    }
}