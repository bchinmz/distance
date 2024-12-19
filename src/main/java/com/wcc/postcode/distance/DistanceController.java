package com.wcc.postcode.distance;

import com.wcc.postcode.distance.model.DistanceCalcResponse;
import com.wcc.postcode.distance.model.PostCodeDetail;
import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import com.wcc.postcode.services.DistanceCalculator;
import com.wcc.postcode.services.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/distances")
public class DistanceController {
    private static final Logger log = LoggerFactory.getLogger(DistanceController.class);

    @Autowired
    private UKPostCodeRepository repository;

    @Autowired
    private DistanceCalculator distanceCalculator;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/calc")
    public DistanceCalcResponse calcDistance(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        var logMsg = String.format("{\"type\": \"calcDistance\", \"start\": \"%s\", \"end\": \"%s\"}", start, end);
        log.info(logMsg);
        var startEntity = getPostCodeEntity(start);
        var endEntity = getPostCodeEntity(end);
        var distance = distanceCalculator.calculate(
                new Position(startEntity.getLatitude(), startEntity.getLongitude()) ,
                new Position(endEntity.getLatitude(), endEntity.getLongitude())
        );

        var startLocation = new PostCodeDetail(startEntity);
        var endLocation = new PostCodeDetail(endEntity);
        return new DistanceCalcResponse(startLocation, endLocation, distance);
    }

    private UKPostCodeEntity getPostCodeEntity(String postcode) {
        return repository.findById(postcode)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Post code")
                );
    }
}
