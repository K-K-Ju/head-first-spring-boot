package ua.andrii;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String doService(String str) {
        return str.toLowerCase() + str.toUpperCase();
    }
}
