package cn.k12soft.servo.module.charge.domain;

import java.util.Map;

public class StudentChargeRemain {

  private int studentId;
  private int klassId;
  private String studentName;
  private Map<Integer, Float> expenseEntryMap;

  public StudentChargeRemain(int studentId, int klassId, String studentName, Map<Integer, Float> expenseEntryMap) {
    this.studentId = studentId;
    this.klassId = klassId;
    this.studentName = studentName;
    this.expenseEntryMap = expenseEntryMap;
  }

  public int getStudentId() {
    return studentId;
  }

  public int getKlassId() {
    return klassId;
  }

  public String getStudentName() {
    return studentName;
  }

  public Map<Integer, Float> getExpenseEntryMap() {
    return expenseEntryMap;
  }
}
