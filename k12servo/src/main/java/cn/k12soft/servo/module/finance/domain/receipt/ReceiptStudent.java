package cn.k12soft.servo.module.finance.domain.receipt;

public class ReceiptStudent {

  private Integer studentId;
  private String klassName;
  private String studentName;

  private ReceiptStudent() {}

  public ReceiptStudent(Integer studentId, String klassName, String studentName) {
    this.studentId = studentId;
    this.klassName = klassName;
    this.studentName = studentName;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public String getKlassName() {
    return klassName;
  }

  public String getStudentName() {
    return studentName;
  }
}
