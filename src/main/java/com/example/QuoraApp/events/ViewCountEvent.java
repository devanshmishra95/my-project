package com.example.QuoraApp.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCountEvent {
    private String targetId;
    private String targetType;
    private LocalDateTime timestamp;
}


///ViewCountEvent me sirf wahi fields hoti hain
//jo tum apne “event” ko represent karna chahte ho.
//        Na zyada, na kam.