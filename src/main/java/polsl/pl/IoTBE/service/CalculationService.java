package polsl.pl.IoTBE.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class CalculationService {


  public  List<Double>  sortArray(List<Double> data){
        Collections.sort(data);
        return data;
    }

  public double calculateAvarage(List<Double> data){
      return data.stream()
              .mapToDouble(d -> d)
              .average()
              .orElse(0.0);
    }

    public double calculateMax(List<Double> data){
        return Collections.max(data);
    }
    public double calculateMin(List<Double> data){
        return Collections.min(data);
    }
    public double calculateDomimant(List<Double> data){
      Map<Double, Integer> map = new HashMap<>();
        for (Double i : data) {
            Integer val = map.get(i);
            map.put(i, val == null ? 1 : val + 1);
        }
        Map.Entry<Double, Integer> max = null;

        for ( Map.Entry<Double, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        return max.getKey();
    }

    public double calculateMedian(List<Double> data){
      double median = 0;
      if(data.size() % 2 == 1){
          median = data.get(data.size()/2);
      }
      else{
          median = data.get(data.size()/2) + data.get(data.size()/2 -1 );
          median /=2;
      }
      return median;
    }

    public double calculateStandardDeviation(List<Double> data){
      double avarage = this.calculateAvarage(data);
      double indirectSum = 0.0;
      for(Double i: data){
          indirectSum += (i - avarage)*(i - avarage);
      }
      if(data.size()!=1){
          indirectSum = indirectSum/(data.size()-1);
      }
      return Math.sqrt(indirectSum);
    }
}
