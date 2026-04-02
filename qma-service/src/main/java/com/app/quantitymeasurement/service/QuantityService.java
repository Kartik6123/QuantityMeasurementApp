package com.app.quantity.service;

import com.app.quantity.dto.InputDTO;

public interface QuantityService {
    boolean compare(InputDTO a, InputDTO b);
    double add(InputDTO a, InputDTO b);
    double convert(InputDTO a, String target);
}
