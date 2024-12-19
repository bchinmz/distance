package com.wcc.postcode.management;

import com.wcc.postcode.management.model.PositionRequest;
import com.wcc.postcode.management.model.PostCodeDetail;
import com.wcc.postcode.repository.UKPostCodeEntity;
import com.wcc.postcode.repository.UKPostCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/postcodes")
public class PostcodeController {
    @Autowired
    private UKPostCodeRepository repository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{postcode}")
    PostCodeDetail get(@PathVariable("postcode") String postcode) {
        var entity = getPostCodeEntity(postcode);
        return new PostCodeDetail(entity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{postcode}")
    void update(@PathVariable("postcode") String postcode, @RequestBody PositionRequest position) {
        var entity = getPostCodeEntity(postcode);
        repository.save(entity.toNewCoordinates(position.getLatitude(), position.getLongitude()));
    }

    private UKPostCodeEntity getPostCodeEntity(String postcode) {
        return repository.findById(postcode)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Post code")
                );
    }
}
