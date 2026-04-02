package com.app.quantity.service;

import com.app.quantity.dto.InputDTO;
import com.app.quantity.model.QuantityRecord;
import com.app.quantity.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl implements com.app.quantity.service.QuantityService {

    @Autowired
    private QuantityRepository repo;

    private void validate(InputDTO a, InputDTO b) {
        if (!a.type.equals(b.type)) throw new RuntimeException("Type mismatch");
    }

    private double lengthToBase(InputDTO q) {
        switch (q.unit) {
            case "FEET":   return q.value * 12;
            case "INCHES": return q.value;
            case "METER":  return q.value * 39.37;
            default: throw new RuntimeException("Invalid length unit");
        }
    }

    private double weightToBase(InputDTO q) {
        switch (q.unit) {
            case "KG":   return q.value * 1000;
            case "GRAM": return q.value;
            default: throw new RuntimeException("Invalid weight unit");
        }
    }

    private double toBase(InputDTO q) {
        if (q.type.equals("LENGTH")) return lengthToBase(q);
        if (q.type.equals("WEIGHT")) return weightToBase(q);
        throw new RuntimeException("Invalid type");
    }

    @Override
    public boolean compare(InputDTO a, InputDTO b) {
        validate(a, b);
        boolean result = toBase(a) == toBase(b);
        repo.save(new QuantityRecord(null, a.value, a.unit, b.value, b.unit, "COMPARE", result ? 1 : 0, a.type));
        return result;
    }

    @Override
    public double add(InputDTO a, InputDTO b) {
        validate(a, b);
        double result = toBase(a) + toBase(b);
        repo.save(new QuantityRecord(null, a.value, a.unit, b.value, b.unit, "ADD", result, a.type));
        return result;
    }

    @Override
    public double convert(InputDTO a, String target) {
        double result = toBase(a);
        repo.save(new QuantityRecord(null, a.value, a.unit, 0, target, "CONVERT", result, a.type));
        return result;
    }
}
