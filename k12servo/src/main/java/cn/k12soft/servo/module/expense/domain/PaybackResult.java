package cn.k12soft.servo.module.expense.domain;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liubing on 2018/4/16
 */
public class PaybackResult {
    private Student student;
    private Klass klass;
    private float money; // 应退的费用
    private List<ExpenseEntry> expenseEntryList = new LinkedList<>(); // 退的费种

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public void addMoney(float money){
        this.money += money;
    }

    public float getMoney() {
        return money;
    }

    public void addExpenseEntry(ExpenseEntry expenseEntry){
        this.expenseEntryList.add(expenseEntry);
    }

    public List<ExpenseEntry> getExpenseEntryList() {
        return expenseEntryList;
    }

}
