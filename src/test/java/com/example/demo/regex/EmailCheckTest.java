package com.example.demo.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class EmailCheckTest {
    
    /** * Comment : 정상적인 이메일 인지 검증. */ 
    boolean isValidEmail(String email) { 
         boolean err = false; 
         String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
         Pattern p = Pattern.compile(regex); 
         Matcher m = p.matcher(email); 
         if(m.matches()) { 
             err = true; 
        }
         return err; 
        }
    
    @Test
    void test(){
        assertEquals(isValidEmail("sefefef"), true);
    }
    
    @Test
    void test2(){
        assertEquals(isValidEmail("senspond@gmail.com"), true);
    }

    @Test
    void test3(){
        System.out.println(isValidEmail("efefefef$ga"));

    }

}
