package com.app.quantitymeasurement.entity;

public class QuantityMeasurementEntity {

    private QuantityDTO operand1;
    private QuantityDTO operand2;
    private String      operationType;
    private QuantityDTO result;
    private double      scalarResult;
    private boolean     hasScalarResult;
    private boolean     isComparison;
    private boolean     comparisonResult;
    private boolean     hasError;
    private String      errorMessage;

    public QuantityMeasurementEntity(QuantityDTO operand1, QuantityDTO operand2, String operationType) {
        this.operand1      = operand1;
        this.operand2      = operand2;
        this.operationType = operationType;
        this.hasError      = false;
    }

    public QuantityMeasurementEntity(QuantityDTO operand1, QuantityDTO operand2, String operationType, boolean comparisonResult) {
        this.operand1         = operand1;
        this.operand2         = operand2;
        this.operationType    = operationType;
        this.isComparison     = true;
        this.comparisonResult = comparisonResult;
        this.hasError         = false;
    }

    public QuantityMeasurementEntity(String errorMessage) {
        this.hasError     = true;
        this.errorMessage = errorMessage;
    }

    public void setResult(QuantityDTO result)       { this.result = result; }
    public void setScalarResult(double scalarResult){ this.scalarResult = scalarResult; this.hasScalarResult = true; }

    public QuantityDTO getOperand1()     { return operand1;         }
    public QuantityDTO getOperand2()     { return operand2;         }
    public String getOperationType()     { return operationType;    }
    public QuantityDTO getResult()       { return result;           }
    public double getScalarResult()      { return scalarResult;     }
    public boolean hasScalarResult()     { return hasScalarResult;  }
    public boolean isComparison()        { return isComparison;     }
    public boolean getComparisonResult() { return comparisonResult; }
    public boolean hasError()            { return hasError;         }
    public String getErrorMessage()      { return errorMessage;     }

    @Override
    public String toString() {
        if (hasError)        return "Error: " + errorMessage;
        if (isComparison)    return operand1 + " == " + operand2 + " -> " + comparisonResult;
        if (hasScalarResult) return operand1 + " " + operationType + " " + operand2 + " = " + scalarResult;
        return operand1 + " " + operationType + " -> " + result;
    }
}
