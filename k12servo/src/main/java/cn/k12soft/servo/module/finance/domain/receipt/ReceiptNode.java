package cn.k12soft.servo.module.finance.domain.receipt;//package cn.k12soft.servo.module.finance.domain.receipt;
//
//import cn.k12soft.servo.domain.SchoolEntity;
//import java.time.Instant;
//import java.util.List;
//import javax.annotation.concurrent.Immutable;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import net.optionfactory.hj.JsonType;
//import net.optionfactory.hj.spring.SpringDriverLocator;
//import org.hibernate.annotations.Type;
//
///**
// * 收据,提供收费明细,不可变
// */
//@Immutable
//@Entity
//public class ReceiptNode extends SchoolEntity {
//
//  @Id
//  private String noteId;
//  private Integer klassId;
//  private String klassName;
//  private Integer studentId;
//  private String studentName;
//  @Type(type = JsonType.TYPE)
//  @JsonType.Conf(locator = SpringDriverLocator.class)
//  private List<ReceiptItem> items;
//  private Long receiptedAmount;
//  private Instant receiptedAt;
//
//  private ReceiptNode() {}
//
//  public ReceiptNode(Integer schoolId,
//                     String noteId,
//                     Integer klassId,
//                     String klassName,
//                     Integer studentId,
//                     String studentName,
//                     List<ReceiptItem> items) {
//    super(schoolId);
//    this.noteId = noteId;
//    this.klassId = klassId;
//    this.klassName = klassName;
//    this.studentId = studentId;
//    this.studentName = studentName;
//    this.items = items;
//    this.receiptedAmount = items.stream().mapToLong(ReceiptItem::getReceiptedAmount).sum();
//    this.receiptedAt = Instant.now();
//  }
//
//  public String getNoteId() {
//    return noteId;
//  }
//
//  public Integer getKlassId() {
//    return klassId;
//  }
//
//  public String getKlassName() {
//    return klassName;
//  }
//
//  public Integer getStudentId() {
//    return studentId;
//  }
//
//  public String getStudentName() {
//    return studentName;
//  }
//
//  public List<ReceiptItem> getItems() {
//    return items;
//  }
//
//  public Long getReceiptedAmount() {
//    return receiptedAmount;
//  }
//
//  public Instant getReceiptedAt() {
//    return receiptedAt;
//  }
//}
