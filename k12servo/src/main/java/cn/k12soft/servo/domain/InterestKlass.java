package cn.k12soft.servo.domain;

import cn.k12soft.servo.domain.enumeration.KlassType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 兴趣班
 */
@Entity
@DynamicUpdate
public class InterestKlass extends SchoolEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private KlassType type;

    @Column(length = 32, unique = true, nullable = false)
    private String name;

    @Column(length = 512)
    private String description;

    private Instant createAt; //创建时间
    private Integer lessonCount; //课节数
    private Instant startLesAt; //费用开始日
    private Instant endLesAt; //费用结束日
    private Integer lesPeriod;//课节长度

//    @ManyToOne()
//    private Grade grade;

    @JsonIgnore
    @ManyToMany
    private List<Student> students = new ArrayList<>();

    public InterestKlass() {

    }

    public InterestKlass(Integer id) {
        this.id = id;
    }

    public InterestKlass(Integer schoolId, String name, KlassType type, String description) {
        super(schoolId);
//        this.grade = grade;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public KlassType getType() {
        return type;
    }

    public void setType(KlassType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Integer getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    public Instant getStartLesAt() {
        return startLesAt;
    }

    public void setStartLesAt(Instant startLesAt) {
        this.startLesAt = startLesAt;
    }

    public Instant getEndLesAt() {
        return endLesAt;
    }

    public void setEndLesAt(Instant endLesAt) {
        this.endLesAt = endLesAt;
    }

    public Integer getLesPeriod() {
        return lesPeriod;
    }

    public void setLesPeriod(Integer lesPeriod) {
        this.lesPeriod = lesPeriod;
    }

//    public Grade getGrade() {
//        return grade;
//    }

//    public void setGrade(Grade grade) {
//        this.grade = grade;
//    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
