package rw.auca.stolenvehicle.service;

import org.springframework.stereotype.Service;

@Service
public class CrudMessageService {
    public String saved(String label) {
        return label + " saved successfully";
    }
}
