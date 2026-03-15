CREATE TABLE IF NOT EXISTS quantity_measurements (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type    VARCHAR(50),
    operand1_value    DOUBLE,
    operand1_unit     VARCHAR(50),
    operand1_type     VARCHAR(50),
    operand2_value    DOUBLE,
    operand2_unit     VARCHAR(50),
    operand2_type     VARCHAR(50),
    result_value      DOUBLE,
    result_unit       VARCHAR(50),
    scalar_result     DOUBLE,
    comparison_result BOOLEAN,
    has_error         BOOLEAN,
    error_message     VARCHAR(500),
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
