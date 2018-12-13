package cn.k12soft.servo.module.rateGroup.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.rateGroup.domain.GroupStudentRate;
import cn.k12soft.servo.module.rateGroup.domain.GroupTeacherRate;
import cn.k12soft.servo.module.rateGroup.repository.GroupStuRateRepositdry;
import cn.k12soft.servo.module.rateGroup.repository.GroupTeaRateRepositdry;
import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.module.rateRegions.repository.RegionsStudentRateRepository;
import cn.k12soft.servo.module.rateRegions.repository.RegionsTeacherRateRepository;
import cn.k12soft.servo.module.zone.domain.Groups;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional
public class GroupRateService {

    private final GroupsRepository groupsRepository;
    private final GroupTeaRateRepositdry groupTeaRateRepositdry;
    private final GroupStuRateRepositdry groupStuRateRepositdry;
    private final RegionsTeacherRateRepository regionsTeacherRateRepository;
    private final RegionsStudentRateRepository regionsStudentRateRepository;


    public GroupRateService(GroupsRepository groupsRepository, GroupTeaRateRepositdry groupTeaRateRepositdry,
                            GroupStuRateRepositdry groupStuRateRepositdry, RegionsTeacherRateRepository regionsTeacherRateRepository, RegionsStudentRateRepository regionsStudentRateRepository) {
        this.groupsRepository = groupsRepository;
        this.groupTeaRateRepositdry = groupTeaRateRepositdry;
        this.groupStuRateRepositdry = groupStuRateRepositdry;
        this.regionsTeacherRateRepository = regionsTeacherRateRepository;
        this.regionsStudentRateRepository = regionsStudentRateRepository;
    }

    public void groupTeaRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Groups> Groups = groupsRepository.findAll();
        for (Groups group : Groups) {
            Integer groupsId = group.getId();
            Collection<RegionsTeacherRate> regionsTeacherRates = regionsTeacherRateRepository.findByRegionsId(groupsId);
            if (regionsTeacherRates.size() == 0) {
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(regionsTeacherRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);
            String monthValue = "";
            for (RegionsTeacherRate rtr : regionsTeacherRates) {
                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        monthValue = rtr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = rtr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = rtr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = rtr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = rtr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = rtr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = rtr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = rtr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = rtr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = rtr.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = rtr.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = rtr.getDecember();
                        break;
                }
                if (StringUtils.isEmpty(monthValue)) {
                    monthValue = "0.00";
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);
            GroupTeacherRate gtr = groupTeaRateRepositdry.queryByGroups(group);
            if (gtr == null) {
                gtr = new GroupTeacherRate();
                gtr.setCreatedAt(Instant.now());
                gtr.setGroups(group);
            }
            switch (calendar.get(Calendar.MONTH)) {
                case Calendar.JANUARY:
                    gtr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    gtr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    gtr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    gtr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    gtr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    gtr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    gtr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    gtr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    gtr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    gtr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    gtr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    gtr.setDecember(monthValue);
                    break;
            }
            groupTeaRateRepositdry.save(gtr);
        }
    }

    public Collection<GroupTeacherRate> findGroupTeaRate(Actor actor) {
        Groups groups = groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
        return groupTeaRateRepositdry.findByGroups(groups);
    }

    public Collection<RegionsTeacherRate> findRegionsTeacherRate(Actor actor) {
        Groups groups = groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
        return regionsTeacherRateRepository.findByGroupId(Integer.valueOf(groups.getId()));
    }

    public void groupStuRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Groups> Groups = groupsRepository.findAll();
        for (Groups group : Groups) {
            Integer groupsId = group.getId();
            Collection<RegionsStudentRate> regionsStudentRates = regionsStudentRateRepository.findByRegionsId(groupsId);
            if (regionsStudentRates.size() == 0) {
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(regionsStudentRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);
            String monthValue = "";
            for (RegionsStudentRate rtr : regionsStudentRates) {
                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        monthValue = rtr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = rtr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = rtr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = rtr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = rtr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = rtr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = rtr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = rtr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = rtr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = rtr.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = rtr.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = rtr.getDecember();
                        break;
                }
                if (StringUtils.isEmpty(monthValue)) {
                    monthValue = "0.00";
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);
            GroupStudentRate gsr = groupStuRateRepositdry.queryByGroups(group);
            if (gsr == null) {
                gsr = new GroupStudentRate();
                gsr.setCreatedAt(Instant.now());
                gsr.setGroups(group);
            }
            switch (calendar.get(Calendar.MONTH)) {
                case Calendar.JANUARY:
                    gsr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    gsr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    gsr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    gsr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    gsr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    gsr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    gsr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    gsr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    gsr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    gsr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    gsr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    gsr.setDecember(monthValue);
                    break;
            }
            groupStuRateRepositdry.save(gsr);
        }
    }

    public Collection<GroupStudentRate> findGroupStuRate(Actor actor) {
        Groups groups = groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
        return groupStuRateRepositdry.findByGroups(groups);
    }

    public Collection<RegionsStudentRate> findRegionsStudentRate(Actor actor) {
        Groups groups = groupsRepository.findById(Integer.valueOf(actor.getGroupId()));
        return regionsStudentRateRepository.findByGroupId(groups.getId());
    }

    public Collection<RegionsStudentRate> getRegionsStuRate(Actor actor, String code) {
        Groups groups = groupsRepository.findByCode(code);
        Collection<RegionsStudentRate> regionsStudentRates = regionsStudentRateRepository.findByGroupId(groups.getId());
        return regionsStudentRates;
    }

    public Collection<RegionsTeacherRate> getRegionsTeaRate(Actor actor, String code) {
        Groups groups = groupsRepository.findByCode(code);
        Collection<RegionsTeacherRate> regionsTeaRates = regionsTeacherRateRepository.findByGroupId(groups.getId());
        return regionsTeaRates;
    }
}
