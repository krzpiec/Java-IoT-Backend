package polsl.pl.IoTBE.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.repository.TestRepository;
import polsl.pl.IoTBE.repository.dao.TestEntity;

@RestController
public class Controller {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/toja")
    public ResponseEntity<String> odp(){
        TestEntity test = new TestEntity();
        test.setName("stefan");
        testRepository.save(test);
        return ResponseEntity.ok("JD");
    }

}
