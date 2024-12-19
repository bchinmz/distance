package com.wcc.postcode.distance;

import com.wcc.postcode.distance.model.PostCodeDetail;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostCodeDetailTest {

    @Test
    void converter() {
        var target = new PostCodeDetail("xx", 57.209059, -2.176051);
        assertThat(target.getLatitude()).isEqualTo("57° 12' 32.6124\"");
        assertThat(target.getLongitude()).isEqualTo("-2° 10' 33.7836\"");
    }
}