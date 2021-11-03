package polsl.pl.IoTBE.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/toja")
    public ResponseEntity<String> odp(){
        return ResponseEntity.ok("JD");
    }

}
