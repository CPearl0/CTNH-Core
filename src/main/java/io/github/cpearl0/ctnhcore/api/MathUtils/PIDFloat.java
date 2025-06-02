package io.github.cpearl0.ctnhcore.api.MathUtils;

import lombok.Setter;

public class PIDFloat {
    @Setter
    float p, i, d;
    float e, de, ie;
    @Setter
    float target;
    public PIDFloat(float p, float i, float d, float target) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.target = target;
    }
    public float update(float real) {
        de = e;
        e = real - target;
        de = e-de;
        ie += e;
        return p*e + i*ie + d*de;
    }
}
