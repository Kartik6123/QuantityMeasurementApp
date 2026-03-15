package Repository;

import Model.QuantityMeasurementEntity;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null)
            instance = new QuantityMeasurementCacheRepository();
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {}

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>();
    }
}