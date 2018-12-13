package cn.k12soft.servo.module.countIncomePayout.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.countIncomePayout.domain.income.*;
import cn.k12soft.servo.module.countIncomePayout.domain.payout.*;
import cn.k12soft.servo.module.countIncomePayout.domain.pojo.DistrictPojo;
import cn.k12soft.servo.module.countIncomePayout.repository.*;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.revenue.domain.Income;
import cn.k12soft.servo.module.revenue.domain.Payout;
import cn.k12soft.servo.module.revenue.repository.IncomeRepository;
import cn.k12soft.servo.module.revenue.repository.PayoutRepository;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Calendar.*;

@Service
@Transactional
public class CountMoneyService {

    private final CountIncSchRepository incSchRepository;
    private final CountIncCitysRepository incCitysRepository;
    private final CountIncGroupsRepository incGroupsRepository;
    private final CountIncRegionsRepository incRegionsRepository;
    private final CountIncProvincesRepository incProvincesRepository;

    private final CountPayoutSchRepository payoutSchRepository;
    private final CountPayoutCitysRepository payoutCitysRepository;
    private final CountPayoutGroupsRepository payoutGroupsRepository;
    private final CountPayoutRegionsRepository payoutRegionsRepository;
    private final CountPayoutProvincesRepository payoutProvincesRepository;

    private final CitysRepository citysRepository;
    private final GroupsRepository groupsRepository;
    private final SchoolRepository schoolRepository;
    private final RegionsRepository regionsRepository;
    private final ProvincesRepository provincesRepository;

    private final IncomeRepository incomeRepository;
    private final PayoutRepository payoutRepository;

    @Autowired
    public CountMoneyService(CountIncSchRepository incSchRepository, CountIncCitysRepository incCitysRepository, CountIncGroupsRepository incGroupsRepository, CountIncRegionsRepository incRegionsRepository, CountIncProvincesRepository incProvincesRepository, CountPayoutSchRepository payoutSchRepository, CountPayoutCitysRepository payoutCitysRepository, CountPayoutGroupsRepository payoutGroupsRepository, CountPayoutRegionsRepository payoutRegionsRepository, CountPayoutProvincesRepository payoutProvincesRepository, CitysRepository citysRepository, GroupsRepository groupsRepository, SchoolRepository schoolRepository, RegionsRepository regionsRepository, ProvincesRepository provincesRepository, IncomeRepository incomeRepository, PayoutRepository payoutRepository) {
        this.incSchRepository = incSchRepository;
        this.incCitysRepository = incCitysRepository;
        this.incGroupsRepository = incGroupsRepository;
        this.incRegionsRepository = incRegionsRepository;
        this.incProvincesRepository = incProvincesRepository;
        this.payoutSchRepository = payoutSchRepository;
        this.payoutCitysRepository = payoutCitysRepository;
        this.payoutGroupsRepository = payoutGroupsRepository;
        this.payoutRegionsRepository = payoutRegionsRepository;
        this.payoutProvincesRepository = payoutProvincesRepository;
        this.citysRepository = citysRepository;
        this.groupsRepository = groupsRepository;
        this.schoolRepository = schoolRepository;
        this.regionsRepository = regionsRepository;
        this.provincesRepository = provincesRepository;
        this.incomeRepository = incomeRepository;
        this.payoutRepository = payoutRepository;
    }

    private final String G = "G";
    private final String Q = "Q";
    private final String P = "P";
    private final String C = "C";
    private final String S = "S";


    public void countIncome() {
        // 查询所有省
        Collection<Groups> groups = groupsRepository.findAll();
        for (Groups group : groups){
            Integer groupId = group.getId();

            // 通过省ID找到所有省下面的大区
            Collection<Regions> regions = regionsRepository.findByGroupId(groupId);
            for (Regions region : regions){
                Integer regionId = region.getId();

                // 通过大区ID找到大区下面所有的省
                Collection<Provinces> provinces = provincesRepository.findByregionId(regionId);
                for (Provinces province : provinces){
                    Integer provinceId = province.getId();

                    // 通过省ID找到省下面所有城市
                    Collection<Citys> citys = citysRepository.findByProvinceId(provinceId);
                    for (Citys city : citys){
                        Integer cityId = city.getId();

                        // 通过城市ID找到城市下所有学校
                        Collection<School> schools = schoolRepository.findByCityId(cityId);
                        for (School school : schools){

                            // 处理统计学校收入支出
                            incomeSchools(school);
                            payoutSchool(school);
                        }


                        // 处理统计市收入支出
                        incomeCitys(city);
                        payoutCitys(city);
                    }

                    // 处理统计省收入支出
                    incomeProvinces(province);
                    payoutProvinces(province);
                }


                // 处理统计大区收入支出
                incomeRegions(region);
                payOutRegions(region);
            }


            // 处理统计集团收入支出
            incomeGroups(group);
            payoutGroups(group);
        }
    }

    /**
     * 统计集团支出
     * @param group
     */
    private void payoutGroups(Groups group) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的城市
        Collection<CountPayoutRegions> payout = payoutRegionsRepository.findAllByGroupId(group.getId());
        String getMonthValueStr = "";
        for (CountPayoutRegions obj : payout){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }

        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountPayoutGroups obj = payoutGroupsRepository.findByGroups(group);
        if (obj == null){
            obj = new CountPayoutGroups();
            obj.setGroups(group);
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        payoutGroupsRepository.save(obj);
    }

    /**
     * 统计集团收入
     * @param group
     */
    private void incomeGroups(Groups group) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的大区
        Collection<CountIncomeRegions> incomes= incRegionsRepository.findAllByGroupId(group.getId());
        String getMonthValueStr = "";
        for (CountIncomeRegions obj : incomes){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }
        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountIncomeGroups obj = incGroupsRepository.findByGroups(group);
        if (obj == null){
            obj = new CountIncomeGroups();
            obj.setGroups(group);
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        incGroupsRepository.save(obj);
    }

    /**
     * 统计大区支出
     * @param region
     */
    private void payOutRegions(Regions region) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的城市
        Collection<CountPayoutProvinces> payout = payoutProvincesRepository.findAllByRegionId(region.getId());
        String getMonthValueStr = "";
        for (CountPayoutProvinces obj : payout){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }

        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountPayoutRegions obj = payoutRegionsRepository.findByRegions(region);
        if (obj == null){
            obj = new CountPayoutRegions();
            obj.setRegions(region);
            obj.setGroupId(region.getGroupId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        payoutRegionsRepository.save(obj);
    }

    /**
     * 统计大区收入
     * @param region
     */
    private void incomeRegions(Regions region) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的省
        Collection<CountIncomeProvinces> incomes= incProvincesRepository.findAllByRegionId(region.getId());
        String getMonthValueStr = "";
        for (CountIncomeProvinces obj : incomes){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }
        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountIncomeRegions obj = incRegionsRepository.findByRegions(region);
        if (obj == null){
            obj = new CountIncomeRegions();
            obj.setRegions(region);
            obj.setGroupId(region.getGroupId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        incRegionsRepository.save(obj);
    }

    /**
     * 统计省收入
     * @param province
     */
    private void incomeProvinces(Provinces province) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的城市
        Collection<CountIncomeCitys> incomeSchools= incCitysRepository.findAllByProvinceId(province.getId());
        String getMonthValueStr = "";
        for (CountIncomeCitys obj : incomeSchools){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }
        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountIncomeProvinces obj = incProvincesRepository.findByProvinces(province);
        if (obj == null){
            obj = new CountIncomeProvinces();
            obj.setProvinces(province);
            obj.setRegionId(province.getRegionId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        incProvincesRepository.save(obj);
    }

    /**
     * 统计省支出
     * @param province
     */
    private void payoutProvinces(Provinces province) {
        Calendar calendar = Calendar.getInstance();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的城市
        Collection<CountPayoutCitys> payoutCitys = payoutCitysRepository.findAllByProvinceId(province.getId());
        String getMonthValueStr = "";
        for (CountPayoutCitys obj : payoutCitys){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }

        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountPayoutProvinces obj = payoutProvincesRepository.findByProvinces(province);
        if (obj == null){
            obj = new CountPayoutProvinces();
            obj.setProvinces(province);
            obj.setRegionId(province.getRegionId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        payoutProvincesRepository.save(obj);
    }

    /**
     * 统计城市支出
     * @param city
     */
    private void payoutCitys(Citys city) {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的学校
        Collection<CountPayoutSchools> payoutSchools = payoutSchRepository.findAllByCityId(city.getId());
        String getMonthValueStr = "";
        for (CountPayoutSchools obj : payoutSchools){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }

        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountPayoutCitys obj = payoutCitysRepository.findByCitys(city);
        if (obj == null){
            obj = new CountPayoutCitys();
            obj.setCitys(city);
            obj.setProvinceId(city.getProvinceId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        payoutCitysRepository.save(obj);
    }

    private void incomeCitys(Citys city) {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        BigDecimal bigOne = new BigDecimal(0);

        // 找到所有的学校
        Collection<CountIncomeSchools> incomeSchools= incSchRepository.findAllByCityId(city.getId());
        String getMonthValueStr = "";
        for (CountIncomeSchools obj : incomeSchools){
            switch (calendar.get(Calendar.MONTH)){
                case JANUARY:
                    getMonthValueStr = obj.getJanuary();
                    break;
                case FEBRUARY:
                    getMonthValueStr = obj.getFebruary();
                    break;
                case MARCH:
                    getMonthValueStr = obj.getMarch();
                    break;
                case APRIL:
                    getMonthValueStr = obj.getApril();
                    break;
                case MAY:
                    getMonthValueStr = obj.getMay();
                    break;
                case JUNE:
                    getMonthValueStr = obj.getJune();
                    break;
                case JULY:
                    getMonthValueStr = obj.getJuly();
                    break;
                case AUGUST:
                    getMonthValueStr = obj.getAuguest();
                    break;
                case SEPTEMBER:
                    getMonthValueStr = obj.getSeptember();
                    break;
                case OCTOBER:
                    getMonthValueStr = obj.getOctober();
                    break;
                case DECEMBER:
                    getMonthValueStr = obj.getDecember();
                    break;
                case NOVEMBER:
                    getMonthValueStr = obj.getNovember();
                    break;
            }
            bigOne = bigOne.add(new BigDecimal(getMonthValueStr));
        }
        bigOne = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setMonthValueStr = bigOne.toString();

        // 查询是否存在
        CountIncomeCitys obj = incCitysRepository.findByCitys(city);
        if (obj == null){
            obj = new CountIncomeCitys();
            obj.setCitys(city);
            obj.setProvinceId(city.getProvinceId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(setMonthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(setMonthValueStr);
                break;
            case MARCH:
                obj.setMarch(setMonthValueStr);
                break;
            case APRIL:
                obj.setApril(setMonthValueStr);
                break;
            case MAY:
                obj.setMay(setMonthValueStr);
                break;
            case JUNE:
                obj.setJune(setMonthValueStr);
                break;
            case JULY:
                obj.setJuly(setMonthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(setMonthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(setMonthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(setMonthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(setMonthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(setMonthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        incCitysRepository.save(obj);
    }

    /**
     * 统计每个学校支出
     * @param school
     */
    private void payoutSchool(School school) {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        BigDecimal bigOne = new BigDecimal(0);
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);

        Collection<Payout> payouts = payoutRepository.findAllBySchoolIdAndCreateAtBetween(school.getId(), first, second);

        for (Payout payout : payouts){
            bigOne = bigOne.add(new BigDecimal(payout.getMoney()));
        }
        String monthValueStr = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        // 查询是否存在
        CountPayoutSchools obj = payoutSchRepository.findBySchool(school);
        if (obj == null){
            obj = new CountPayoutSchools();
            obj.setSchool(school);
            obj.setCityId(school.getCityId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(monthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(monthValueStr);
                break;
            case MARCH:
                obj.setMarch(monthValueStr);
                break;
            case APRIL:
                obj.setApril(monthValueStr);
                break;
            case MAY:
                obj.setMay(monthValueStr);
                break;
            case JUNE:
                obj.setJune(monthValueStr);
                break;
            case JULY:
                obj.setJuly(monthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(monthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(monthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(monthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(monthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(monthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        payoutSchRepository.save(obj);
    }

    /**
     * 统计每个学校的收入
     * @param school
     */
    private void incomeSchools(School school) {
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();
        BigDecimal bigOne = new BigDecimal(0);
        Instant first = localDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant second = localDate.withDayOfMonth(localDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC);

        Collection<Income> incomes = incomeRepository.findAllBySchoolIdAndCreateAtBetween(school.getId(), first, second);

        for (Income income : incomes){
            bigOne = bigOne.add(new BigDecimal(income.getMoney()));
        }
        String monthValueStr = bigOne.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        // 查询是否存在
        CountIncomeSchools obj = incSchRepository.findBySchool(school);
        if (obj == null){
            obj = new CountIncomeSchools();
            obj.setSchool(school);
            obj.setCityId(school.getCityId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        switch (calendar.get(Calendar.MONTH)){
            case JANUARY:
                obj.setJanuary(monthValueStr);
                break;
            case FEBRUARY:
                obj.setFebruary(monthValueStr);
                break;
            case MARCH:
                obj.setMarch(monthValueStr);
                break;
            case APRIL:
                obj.setApril(monthValueStr);
                break;
            case MAY:
                obj.setMay(monthValueStr);
                break;
            case JUNE:
                obj.setJune(monthValueStr);
                break;
            case JULY:
                obj.setJuly(monthValueStr);
                break;
            case AUGUST:
                obj.setAuguest(monthValueStr);
                break;
            case SEPTEMBER:
                obj.setSeptember(monthValueStr);
                break;
            case OCTOBER:
                obj.setOctober(monthValueStr);
                break;
            case DECEMBER:
                obj.setDecember(monthValueStr);
                break;
            case NOVEMBER:
                obj.setNovember(monthValueStr);
                break;
        }

        // 统计从一月到现在
        BigDecimal bigTwo = new BigDecimal(obj.getJanuary())
                .add(new BigDecimal(obj.getFebruary()))
                .add(new BigDecimal(obj.getMarch()))
                .add(new BigDecimal(obj.getApril()))
                .add(new BigDecimal(obj.getMay()))
                .add(new BigDecimal(obj.getJune()))
                .add(new BigDecimal(obj.getJuly()))
                .add(new BigDecimal(obj.getAuguest()))
                .add(new BigDecimal(obj.getSeptember()))
                .add(new BigDecimal(obj.getOctober()))
                .add(new BigDecimal(obj.getNovember()))
                .add(new BigDecimal(obj.getDecember()));

        obj.setCount(bigTwo.toString());
        incSchRepository.save(obj);
    }

    public Map<String, Object> findIncomeCity(Actor actor, Integer cityId) {
        Map<String, Object> map = new HashMap<>();
        if (cityId == null){
            Collection<CountIncomeSchools> count = incSchRepository.findAllByCityId(Integer.valueOf(actor.getCityId()));
            map.put("count", count);
        }else if (cityId != null){
            CountIncomeCitys count = incCitysRepository.findAllByCitys(citysRepository.findById(cityId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findPayoutCitys(Actor actor, Integer cityId) {
        Map<String, Object> map = new HashMap<>();
        if (cityId == null){
            Collection<CountPayoutSchools> count = payoutSchRepository.findAllByCityId(Integer.valueOf(actor.getCityId()));
            map.put("count", count);
        }else if (cityId != null){
            CountPayoutCitys count = payoutCitysRepository.findAllByCitys(citysRepository.findById(cityId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findIncomeProvince(Actor actor, Integer provinceId) {
        Map<String, Object> map = new HashMap<>();
        if (provinceId == null){
            Collection<CountIncomeCitys> count = incCitysRepository.findAllByProvinceId(Integer.valueOf(actor.getProvinceId()));
            map.put("count", count);
        }else if (provinceId != null){
            CountIncomeProvinces count = incProvincesRepository.findAllByProvinces(provincesRepository.findById(provinceId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findPayoutProvince(Actor actor, Integer provinceId) {
        Map<String, Object> map = new HashMap<>();
        if (provinceId == null){
            Collection<CountPayoutCitys> count = payoutCitysRepository.findAllByProvinceId(Integer.valueOf(actor.getProvinceId()));
            map.put("count", count);
        }else if (provinceId != null){
            CountPayoutProvinces count = payoutProvincesRepository.findAllByProvinces(provincesRepository.findById(provinceId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findIncomeRegions(Actor actor, Integer regionId) {
        Map<String, Object> map = new HashMap<>();
        if (regionId == null){
            Collection<CountIncomeProvinces> count = incProvincesRepository.findAllByRegionId(Integer.valueOf(actor.getRegionId()));
            map.put("count", count);
        }else if (regionId != null){
            CountIncomeRegions count = incRegionsRepository.findAllByRegions(regionsRepository.findById(regionId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findPayoutRegions(Actor actor, Integer regionId) {
        Map<String, Object> map = new HashMap<>();
        if (regionId == null){
            Collection<CountPayoutProvinces> count = payoutProvincesRepository.findAllByRegionId(Integer.valueOf(actor.getRegionId()));
            map.put("count", count);
        }else if (regionId != null){
            CountPayoutRegions count = payoutRegionsRepository.findAllByRegions(regionsRepository.findById(regionId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findIncomeGroups(Actor actor, Integer groupId) {
        Map<String, Object> map = new HashMap<>();
        if (groupId == null){
            Collection<CountIncomeRegions> count = incRegionsRepository.findAllByGroupId(Integer.valueOf(actor.getGroupId()));
            map.put("count", count);
        }else if (groupId != null){
            CountIncomeGroups count = incGroupsRepository.findAllByGroups(groupsRepository.findById(groupId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findPayoutGroups(Actor actor, Integer groupId) {
        Map<String, Object> map = new HashMap<>();
        if (groupId == null){
            Collection<CountPayoutRegions> count = payoutRegionsRepository.findAllByGroupId(Integer.valueOf(actor.getGroupId()));
            map.put("count", count);
        }else if (groupId != null){
            CountPayoutGroups count = payoutGroupsRepository.findAllByGroups(groupsRepository.findById(groupId));
            map.put("count", count);
        }
        return map;
    }

    public Map<String, Object> findIncomeByDistrict(Actor actor, String code) {
        Map<String, Object> map = new HashMap<>();
        String indexStr = code.substring(0,1);
        switch (indexStr){
            case G:
                Groups groups = groupsRepository.findByCode(code);
                Collection<CountIncomeRegions> regionsInc = incRegionsRepository.findAllByGroupId(groups.getId());
                map.put("income", regionsInc);
                break;
            case Q:
                Regions regions = regionsRepository.findByCode(code);
                Collection<CountIncomeProvinces> provincesInc = incProvincesRepository.findAllByRegionId(regions.getId());
                map.put("income", provincesInc);
                break;
            case P:
                Provinces provinces = provincesRepository.findByCode(code);
                Collection<CountIncomeCitys> citysInc = incCitysRepository.findAllByProvinceId(provinces.getId());
                map.put("income", citysInc);
                break;
            case C:
                Citys citys = citysRepository.findByCode(code);
                Collection<CountIncomeSchools> schoolsInc = incSchRepository.findAllByCityId(citys.getId());
                map.put("income", schoolsInc);
                break;
            default:
        }
        return map;
    }

    public Map<String, Object> findPayoutByDistrict(Actor actor, String code) {
        Map<String, Object> map = new HashMap<>();
        String indexStr = code.substring(0,1);
        switch (indexStr){
            case G:
                Groups groups = groupsRepository.findByCode(code);
                Collection<CountPayoutRegions> regionsPayout = payoutRegionsRepository.findAllByGroupId(groups.getId());
                map.put("payout", regionsPayout);
                break;
            case Q:
                Regions regions = regionsRepository.findByCode(code);
                Collection<CountPayoutProvinces> provincesPayout = payoutProvincesRepository.findAllByRegionId(regions.getId());
                map.put("payout", provincesPayout);
                break;
            case P:
                Provinces provinces = provincesRepository.findByCode(code);
                Collection<CountPayoutCitys> citysPayout = payoutCitysRepository.findAllByProvinceId(provinces.getId());
                map.put("payout", citysPayout);
                break;
            case C:
                Citys citys = citysRepository.findByCode(code);
                Collection<CountPayoutSchools> schoolsPayout = payoutSchRepository.findAllByCityId(citys.getId());
                map.put("payout", schoolsPayout);
                break;
            default:
        }
        return map;
    }

    public Map<String, Object> findDistrictByCode(String code) {
        String firstCodeStr = code.substring(0,1);
        Map<String, String> map = new HashMap<>();
        Map<String, Object> mapPojo = new HashMap<>();
        DistrictPojo districtPojo = null;
        switch (firstCodeStr){
                    case G:
                        Groups groups = groupsRepository.findByCode(code);
                        CountIncomeGroups countIncomeGroups = incGroupsRepository.findByGroups(groups);
                        CountPayoutGroups countPayoutGroups = payoutGroupsRepository.findAllByGroups(groups);
                        map = getDistrictRate(countIncomeGroups.getCount(), countPayoutGroups.getCount());
                        districtPojo = new DistrictPojo(
                                groups.getName(),
                                groups.getCode(),
                                map.get("incomeRate"),
                                map.get("payoutRate")
                        );
                        break;
                    case Q:
                        Regions regions = regionsRepository.findByCode(code);
                        CountIncomeRegions  countIncomeRegions = incRegionsRepository.findByRegions(regions);
                        CountPayoutRegions  countPayoutRegions = payoutRegionsRepository.findByRegions(regions);
                        map = getDistrictRate(countIncomeRegions.getCount(), countPayoutRegions.getCount());
                        districtPojo = new DistrictPojo(
                                regions.getName(),
                                regions.getCode(),
                                map.get("incomeRate"),
                                map.get("payoutRate")
                        );
                        break;
                    case P:
                        Provinces provinces = provincesRepository.findByCode(code);
                        CountIncomeProvinces countIncomeProvinces = incProvincesRepository.findByProvinces(provinces);
                        CountPayoutProvinces countPayoutProvinces = payoutProvincesRepository.findByProvinces(provinces);
                        map = getDistrictRate(countIncomeProvinces.getCount(), countPayoutProvinces.getCount());
                        districtPojo = new DistrictPojo(
                                provinces.getName(),
                                provinces.getCode(),
                                map.get("incomeRate"),
                                map.get("payoutRate")
                        );
                        break;
                    case C:
                        Citys citys = citysRepository.findByCode(code);
                        CountIncomeCitys countIncomeCitys = incCitysRepository.findByCitys(citys);
                        CountPayoutCitys countPayoutCitys = payoutCitysRepository.findByCitys(citys);
                        map = getDistrictRate(countIncomeCitys.getCount(), countPayoutCitys.getCount());
                        districtPojo = new DistrictPojo(
                                citys.getName(),
                                citys.getCode(),
                                map.get("incomeRate"),
                                map.get("payoutRate")
                        );
                        break;
                }
        mapPojo.put("districtRate", districtPojo);
        return mapPojo;
    }


    public Map<String, Object> findAllDistrictByCode(String code) {
        String firstCodeStr = code.substring(0,1);
        Map<String, String> map = new HashMap<>();
        Map<String, Object> mapPojo = new HashMap<>();
        DistrictPojo districtPojo = null;
        int index = 0;
        switch (firstCodeStr){
            case G:
                Groups groups = groupsRepository.findByCode(code);
                Collection<Regions> regionList = regionsRepository.findByGroupId(groups.getId());
                for (Regions district : regionList){
                    CountIncomeRegions countIncomeRegions = incRegionsRepository.findByRegions(district);
                    CountPayoutRegions countPayoutRegions = payoutRegionsRepository.findByRegions(district);
                    map = getDistrictRate(countIncomeRegions.getCount(), countPayoutRegions.getCount());
                    districtPojo = new DistrictPojo(
                            district.getName(),
                            district.getCode(),
                            map.get("incomeRate"),
                            map.get("payoutRate")
                    );
                    mapPojo.put("districtRate" + index, districtPojo);
                    index++;
                }
                break;
            case Q:
                Regions regions = regionsRepository.findByCode(code);

                Collection<Provinces> provincesList = provincesRepository.findByregionId(regions.getId());

                for (Provinces district : provincesList) {
                    CountIncomeProvinces countIncomeProvinces = incProvincesRepository.findByProvinces(district);
                    CountPayoutProvinces countPayoutProvinces = payoutProvincesRepository.findByProvinces(district);
                    map = getDistrictRate(countIncomeProvinces.getCount(), countPayoutProvinces.getCount());
                    districtPojo = new DistrictPojo(
                            district.getName(),
                            district.getCode(),
                            map.get("incomeRate"),
                            map.get("payoutRate")
                    );
                    mapPojo.put("districtRate" + index, districtPojo);
                    index++;
                }
                break;
            case P:
                Provinces provinces = provincesRepository.findByCode(code);

                Collection<Citys> citysList = citysRepository.findByProvinceId(provinces.getId());
                for (Citys district : citysList) {
                    CountIncomeCitys countIncomeCitys = incCitysRepository.findByCitys(district);
                    CountPayoutCitys countPayoutCitys = payoutCitysRepository.findByCitys(district);
                    map = getDistrictRate(countIncomeCitys.getCount(), countPayoutCitys.getCount());
                    districtPojo = new DistrictPojo(
                            district.getName(),
                            district.getCode(),
                            map.get("incomeRate"),
                            map.get("payoutRate")
                    );
                    mapPojo.put("districtRate" + index, districtPojo);
                    index++;
                }
                break;
            case C:
                Citys citys = citysRepository.findByCode(code);

                Collection<School> schoolsList = schoolRepository.findByCityId(citys.getId());
                for (School district : schoolsList) {
                    CountIncomeSchools countIncomeSchools = incSchRepository.findBySchool(district);
                    CountPayoutSchools countPayoutSchools = payoutSchRepository.findBySchool(district);
                    map = getDistrictRate(countIncomeSchools.getCount(), countPayoutSchools.getCount());
                    districtPojo = new DistrictPojo(
                            district.getName(),
                            district.getCode(),
                            map.get("incomeRate"),
                            map.get("payoutRate")
                    );
                    mapPojo.put("districtRate" + index, districtPojo);
                    index++;
                }
                break;
        }

        return mapPojo;
    }

    /**
     * 支出率：(收入-支出)/支出
     * 收入率：(收入-支出)/收入
     * @param one
     * @param two
     * @return
     */
    public static Map<String, String> getDistrictRate(String one, String two){
        Map<String, String> map = new HashMap<>();
        BigDecimal big100 = new BigDecimal(100);
        BigDecimal bigOne = new BigDecimal(one);    // 收入
        BigDecimal bigTwo = new BigDecimal(two);    // 支出
        BigDecimal bigThree = new BigDecimal(0);// 收入率
        BigDecimal bigFour = new BigDecimal(0); // 支出率

        // 支出率, 保留两位小数
        bigFour = bigOne.subtract(bigTwo).divide(bigTwo).divide(big100, 2, BigDecimal.ROUND_HALF_UP);

        // 收入率
        bigThree = bigOne.subtract(bigTwo).divide(bigOne).divide(big100, 2, BigDecimal.ROUND_HALF_UP);

        map.put("incomeRate", bigThree.toString());
        map.put("payoutRate", bigFour.toString());
        return map;
    }
}
