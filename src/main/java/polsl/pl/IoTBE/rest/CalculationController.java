package polsl.pl.IoTBE.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.rest.dto.CalculationResultsDto;
import polsl.pl.IoTBE.rest.dto.calculationDataDto;
import polsl.pl.IoTBE.service.CalculationService;

@RestController
public class CalculationController {

    @Autowired
    CalculationService calculationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/results/statistics")
    ResponseEntity<CalculationResultsDto> calculateStatistics(@RequestBody calculationDataDto data){
        CalculationResultsDto calculationResultsDto = new CalculationResultsDto();
            if(data.getData().size() == 0)
                return new ResponseEntity<>(calculationResultsDto, HttpStatus.OK);
        calculationResultsDto.setAvarage(this.calculationService.calculateAvarage(data.getData()));
        calculationResultsDto.setDomimant(this.calculationService.calculateDomimant(data.getData()));
        calculationResultsDto.setMedian(this.calculationService.calculateMedian(data.getData()));
        calculationResultsDto.setMax(this.calculationService.calculateMax(data.getData()));
        calculationResultsDto.setMin(this.calculationService.calculateMin(data.getData()));
        calculationResultsDto.setStandardDeviation(this.calculationService.calculateStandardDeviation(data.getData()));
        return new ResponseEntity<>(calculationResultsDto, HttpStatus.OK);
    }
}