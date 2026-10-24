package com.example.QuoraApp.utils;

import java.time.LocalDateTime;

public class CursorUtils {
    public static boolean isValidCursor(String cursor){
        if(cursor == null || cursor.isEmpty()){
            return false;
        }

        try {
            LocalDateTime.parse(cursor);
            return true;
        }
    catch(Exception e){
            return false;
        }
    }
    public static LocalDateTime parseCursor(String cursor){
        if(!isValidCursor(cursor)){
            throw new IllegalArgumentException("Invalid cursor");
            }
        return LocalDateTime.parse(cursor);
    }
}

//So this utility:
//
//        ✅ Verifies that the cursor is properly formatted.
//
//✅ Converts it into a LocalDateTime object for use in DB queries (like WHERE createdAt < cursor).