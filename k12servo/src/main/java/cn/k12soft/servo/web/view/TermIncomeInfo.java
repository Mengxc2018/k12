package cn.k12soft.servo.web.view;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by liubing on 2018/5/30
 */
public class TermIncomeInfo {
    private LocalDate date;
    private List<InnerTermIncomeInfo> infoList;

    public TermIncomeInfo(LocalDate date, List<InnerTermIncomeInfo> infoList) {
        this.date = date;
        this.infoList = infoList;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<InnerTermIncomeInfo> getInfoList() {
        return infoList;
    }

    public static class InnerTermIncomeInfo{
        private int id;
        private String name;
        private Instant createAt;
        private int stuCount;
        private float income;

        public InnerTermIncomeInfo(int id, String name, Instant createAt, int stuCount, float income) {
            this.id = id;
            this.name = name;
            this.createAt = createAt;
            this.stuCount = stuCount;
            this.income = income;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Instant getCreateAt() {
            return createAt;
        }

        public int getStuCount() {
            return stuCount;
        }

        public float getIncome() {
            return income;
        }
    }
}


