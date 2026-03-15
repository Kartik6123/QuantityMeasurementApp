package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private final ConnectionPool pool;

    public QuantityMeasurementDatabaseRepository() {
        this.pool = ConnectionPool.getInstance();
        initializeSchema();
        logger.info("QuantityMeasurementDatabaseRepository initialized");
    }

    private void initializeSchema() {
        Connection conn = pool.acquireConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS quantity_measurements (" +
                            "id               BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "operation_type   VARCHAR(50)," +
                            "operand1_value   DOUBLE," +
                            "operand1_unit    VARCHAR(50)," +
                            "operand1_type    VARCHAR(50)," +
                            "operand2_value   DOUBLE," +
                            "operand2_unit    VARCHAR(50)," +
                            "operand2_type    VARCHAR(50)," +
                            "result_value     DOUBLE," +
                            "result_unit      VARCHAR(50)," +
                            "scalar_result    DOUBLE," +
                            "comparison_result BOOLEAN," +
                            "has_error        BOOLEAN," +
                            "error_message    VARCHAR(500)," +
                            "created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")"
            );
            logger.info("Database schema initialized");
        } catch (SQLException e) {
            throw new DatabaseException("Schema initialization failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String sql = "INSERT INTO quantity_measurements " +
                "(operation_type, operand1_value, operand1_unit, operand1_type, " +
                "operand2_value, operand2_unit, operand2_type, " +
                "result_value, result_unit, scalar_result, comparison_result, has_error, error_message) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = pool.acquireConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getOperationType());
            if (entity.getOperand1() != null) {
                ps.setDouble(2, entity.getOperand1().getValue());
                ps.setString(3, entity.getOperand1().getUnit().getUnitName());
                ps.setString(4, entity.getOperand1().getUnit().getMeasurementType());
            } else { ps.setNull(2, Types.DOUBLE); ps.setNull(3, Types.VARCHAR); ps.setNull(4, Types.VARCHAR); }
            if (entity.getOperand2() != null) {
                ps.setDouble(5, entity.getOperand2().getValue());
                ps.setString(6, entity.getOperand2().getUnit().getUnitName());
                ps.setString(7, entity.getOperand2().getUnit().getMeasurementType());
            } else { ps.setNull(5, Types.DOUBLE); ps.setNull(6, Types.VARCHAR); ps.setNull(7, Types.VARCHAR); }
            if (entity.getResult() != null) {
                ps.setDouble(8, entity.getResult().getValue());
                ps.setString(9, entity.getResult().getUnit().getUnitName());
            } else { ps.setNull(8, Types.DOUBLE); ps.setNull(9, Types.VARCHAR); }
            ps.setDouble(10, entity.getScalarResult());
            ps.setBoolean(11, entity.getComparisonResult());
            ps.setBoolean(12, entity.hasError());
            ps.setString(13, entity.getErrorMessage());
            ps.executeUpdate();
            logger.debug("Entity saved to database");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save entity", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return query("SELECT * FROM quantity_measurements", null);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operationType) {
        return query("SELECT * FROM quantity_measurements WHERE operation_type = ?", operationType);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return query("SELECT * FROM quantity_measurements WHERE operand1_type = ?", measurementType);
    }

    @Override
    public int getTotalCount() {
        Connection conn = pool.acquireConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM quantity_measurements")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get count", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        Connection conn = pool.acquireConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM quantity_measurements");
            logger.info("All measurements deleted");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete all", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public String getPoolStatistics() {
        return pool.getStatistics();
    }

    @Override
    public void releaseResources() {
        pool.closeAll();
    }

    private List<QuantityMeasurementEntity> query(String sql, String param) {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection conn = pool.acquireConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (param != null) ps.setString(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("has_error")) {
                    results.add(new QuantityMeasurementEntity(rs.getString("error_message")));
                } else {
                    QuantityDTO op1 = buildDTO(rs, "operand1");
                    QuantityDTO op2 = buildDTO(rs, "operand2");
                    String opType   = rs.getString("operation_type");
                    if ("COMPARE".equals(opType)) {
                        QuantityMeasurementEntity e = new QuantityMeasurementEntity(op1, op2, opType, rs.getBoolean("comparison_result"));
                        results.add(e);
                    } else {
                        QuantityMeasurementEntity e = new QuantityMeasurementEntity(op1, op2, opType);
                        String resultUnit = rs.getString("result_unit");
                        if (resultUnit != null && op1 != null) {
                            String type = op1.getUnit().getMeasurementType();
                            double resultValue = rs.getDouble("result_value");
                            QuantityDTO result = new QuantityDTO(resultValue, resolveUnit(type, resultUnit));
                            e.setResult(result);
                        }
                        double scalar = rs.getDouble("scalar_result");
                        if (scalar != 0) e.setScalarResult(scalar);
                        results.add(e);
                    }
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException("Query failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return results;
    }

    private QuantityDTO buildDTO(ResultSet rs, String prefix) throws SQLException {
        String unitName = rs.getString(prefix + "_unit");
        String unitType = rs.getString(prefix + "_type");
        if (unitName == null || unitType == null) return null;
        double value = rs.getDouble(prefix + "_value");
        QuantityDTO.IMeasurableUnit unit = resolveUnit(unitType, unitName);
        return new QuantityDTO(value, unit);
    }

    private QuantityDTO.IMeasurableUnit resolveUnit(String type, String name) {
        switch (type.toUpperCase()) {
            case "LENGTH":      return QuantityDTO.LengthUnit.valueOf(name);
            case "WEIGHT":      return QuantityDTO.WeightUnit.valueOf(name);
            case "VOLUME":      return QuantityDTO.VolumeUnit.valueOf(name);
            case "TEMPERATURE": return QuantityDTO.TemperatureUnit.valueOf(name);
            default: throw new DatabaseException("Unknown type: " + type);
        }
    }
}