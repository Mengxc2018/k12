package cn.k12soft.servo.module.empFlowRate.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.empFlowRate.domain.*;
import cn.k12soft.servo.module.empFlowRate.repository.*;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.util.Times;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class EmpFlowRateService {

    private final CitysRepository citysRepository;
    private final GroupsRepository groupsRepository;
    private final SchoolRepository schoolRepository;
    private final RegionsRepository regionsRepository;
    private final ProvincesRepository provincesRepository;

    private final RateFolwSchoolRepository rateFolwSchoolRepository;
    private final RateFolwCityRepository rateFolwCityRepository;
    private final RateFolwProvincesRepository rateFolwProvincesRepository;
    private final RateFolwRegionsRepository rateFolwRegionsRepository;
    private final RateFolwGroupsRepository rateFolwGroupsRepository;

    @Autowired
    public EmpFlowRateService(CitysRepository citysRepository,
                              GroupsRepository groupsRepository,
                              SchoolRepository schoolRepository,
                              RegionsRepository regionsRepository,
                              ProvincesRepository provincesRepository,
                              RateFolwSchoolRepository rateFolwSchoolRepository,
                              RateFolwCityRepository rateFolwCityRepository,
                              RateFolwProvincesRepository rateFolwProvincesRepository,
                              RateFolwRegionsRepository rateFolwRegionsRepository,
                              RateFolwGroupsRepository rateFolwGroupsRepository) {
        this.citysRepository = citysRepository;
        this.groupsRepository = groupsRepository;
        this.schoolRepository = schoolRepository;
        this.regionsRepository = regionsRepository;
        this.provincesRepository = provincesRepository;
        this.rateFolwSchoolRepository = rateFolwSchoolRepository;
        this.rateFolwCityRepository = rateFolwCityRepository;
        this.rateFolwProvincesRepository = rateFolwProvincesRepository;
        this.rateFolwRegionsRepository = rateFolwRegionsRepository;
        this.rateFolwGroupsRepository = rateFolwGroupsRepository;
    }

    private static final String LEAVE = "leave";
    private static final String JOIN = "join";

    public static Map<String, Object> times(){
        Map<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        map.put("calendar", calendar);

        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(-1);
        map.put("localDate", localDate);
        return map;
    }

    public void joinSchoolsRate() {
        Calendar calendar = (Calendar) times().get("calendar");
        LocalDate localDate = (LocalDate)times().get("localDate");

        Pair<Instant, Instant> pair = Times.getFirstAndSecond(localDate);
        Instant first = pair.getFirst();
        Instant second = pair.getSecond();

        BigDecimal bigo = new BigDecimal(100);

        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools) {
            Integer schoolId = school.getId();
            String valueStr = "0.00";
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(0);
            BigDecimal bigThree = new BigDecimal(0);
            // 自然月新加入的员工数量
//            Integer joinInt = employeeBasicRepository.countAllBySchoolIdAndJoinAtBetween(schoolId, first, second);
//            Integer allTeaInt = employeeBasicRepository.countAllBySchoolId(schoolId);
//            if (joinInt != 0){
//                bigOne = new BigDecimal(joinInt);
//                bigTwo = new BigDecimal(allTeaInt);
//                // 计算：本月新加入的老师/所有的老师（包括本月新加入的）
//                bigThree = bigOne.divide(bigTwo, 4, BigDecimal.ROUND_HALF_UP).multiply(bigo);
//                valueStr = bigThree.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//            }

            RateFolwSchool rf = rateFolwSchoolRepository.findAllBySchoolAndStatus(school, JOIN);
            if (rf == null) {
                rf = new RateFolwSchool();
                rf.setCreatedAt(Instant.now());
                rf.setSchool(school);
                rf.setCityId(school.getCityId());
                rf.setStatus(JOIN);
            }
            rf.setUpdateAt(Instant.now());
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    rf.setJanuary(valueStr);
                    break;
                case Calendar.FEBRUARY:
                    rf.setFebruary(valueStr);
                    break;
                case Calendar.MARCH:
                    rf.setMarch(valueStr);
                    break;
                case Calendar.APRIL:
                    rf.setApril(valueStr);
                    break;
                case Calendar.MAY:
                    rf.setMay(valueStr);
                    break;
                case Calendar.JUNE:
                    rf.setJune(valueStr);
                    break;
                case Calendar.JULY:
                    rf.setJuly(valueStr);
                    break;
                case Calendar.AUGUST:
                    rf.setAuguest(valueStr);
                    break;
                case Calendar.SEPTEMBER:
                    rf.setSeptember(valueStr);
                    break;
                case Calendar.OCTOBER:
                    rf.setOctober(valueStr);
                    break;
                case Calendar.NOVEMBER:
                    rf.setNovember(valueStr);
                    break;
                case Calendar.DECEMBER:
                    rf.setDecember(valueStr);
                    break;
            }
            rateFolwSchoolRepository.save(rf);
        }

    }

    public void leaveSchoolsRate() {
        Calendar calendar = (Calendar) times().get("calendar");
        LocalDate localDate = (LocalDate)times().get("localDate");

        Pair<Instant, Instant> pair = Times.getFirstAndSecond(localDate);
        Instant first = pair.getFirst();
        Instant second = pair.getSecond();

        BigDecimal bigo = new BigDecimal(100);

        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools) {
            Integer schoolId = school.getId();
            String valueStr = "0.00";
            // 自然月新加入的员工数量
//            Integer leaveInt = employeeBasicRepository.countAllBySchoolIdAndLeaveAtBetween(schoolId, first, second);
//            Integer allTeaInt = employeeBasicRepository.countAllBySchoolId(schoolId);
            Integer leaveInt = 0;
            Integer allTeaInt = 0;
            if (allTeaInt != 0) {
                BigDecimal bigOne = new BigDecimal(0);
                BigDecimal bigTwo = new BigDecimal(allTeaInt);
                BigDecimal bigThree = new BigDecimal(0);
                bigOne = new BigDecimal(leaveInt);
                // 计算：本月离职的老师/所有的老师（包括本月离职的）
                bigThree = bigOne.divide(bigTwo, 4, BigDecimal.ROUND_HALF_UP).multiply(bigo);
                valueStr = bigThree.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }

            RateFolwSchool rf = rateFolwSchoolRepository.findAllBySchoolAndStatus(school, LEAVE);
            if (rf == null) {
                rf = new RateFolwSchool();
                rf.setCreatedAt(Instant.now());
                rf.setSchool(school);
                rf.setCityId(school.getCityId());
                rf.setStatus(LEAVE);
            }
            rf.setUpdateAt(Instant.now());
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    rf.setJanuary(valueStr);
                    break;
                case Calendar.FEBRUARY:
                    rf.setFebruary(valueStr);
                    break;
                case Calendar.MARCH:
                    rf.setMarch(valueStr);
                    break;
                case Calendar.APRIL:
                    rf.setApril(valueStr);
                    break;
                case Calendar.MAY:
                    rf.setMay(valueStr);
                    break;
                case Calendar.JUNE:
                    rf.setJune(valueStr);
                    break;
                case Calendar.JULY:
                    rf.setJuly(valueStr);
                    break;
                case Calendar.AUGUST:
                    rf.setAuguest(valueStr);
                    break;
                case Calendar.SEPTEMBER:
                    rf.setSeptember(valueStr);
                    break;
                case Calendar.OCTOBER:
                    rf.setOctober(valueStr);
                    break;
                case Calendar.NOVEMBER:
                    rf.setNovember(valueStr);
                    break;
                case Calendar.DECEMBER:
                    rf.setDecember(valueStr);
                    break;
            }
            rateFolwSchoolRepository.save(rf);
        }
    }

    // 统计入职率
    public void rateJoin() {
        Calendar calendar = (Calendar) times().get("calendar");
        Collection<Groups> groups = groupsRepository.findAll();
        for (Groups group : groups){
            Integer groupId = group.getId();

//           集团下面的大区，处理完大区后集团再汇总保存
            Collection<Regions> regions = regionsRepository.findByGroupId(groupId);
            List<RateFolwRegions> rateFolwRegions = new ArrayList<>();
            for (Regions region : regions){
                Integer regionId = region.getId();


//                大区下面的省，处理完省后大区再汇总保存
                Collection<Provinces> provinces = provincesRepository.findByregionId(regionId);
                List<RateFolwProvinces> rateFolwProvinces = new ArrayList<>();
                for (Provinces province : provinces){
                    Integer provinceId = province.getId();


//                    省下面的市，处理完市后大区再处理保存
                    Collection<Citys> citys = citysRepository.findByProvinceId(provinceId);
                    List<RateFolwCity> rateFolwCities = new ArrayList<>();
                    for (Citys city : citys){
                        // 处理市
                        Collection<RateFolwCity> rateFolwCitys = rateCity(city, calendar);
                        rateFolwCities.addAll(rateFolwCitys);
                    }


                    // 处理省
                    Collection<RateFolwProvinces> rateFolwProvince = rateProvinces(province, calendar);
                    rateFolwProvinces.addAll(rateFolwProvince);
                }


                // 处理大区
                Collection<RateFolwRegions> rateFolwRegion = rateRegionsList(region, calendar);
                rateFolwRegions.addAll(rateFolwRegion);
            }


            // 处理集团
            Collection<RateFolwGroups> rateFolwGroup = rateGroupsList(group, calendar);
        }
    }

    private Collection<RateFolwGroups> rateGroupsList(Groups group, Calendar calendar) {
        Collection<RateFolwRegions> rateFolwRegions = rateFolwRegionsRepository.findByGroupId(group.getId());

        BigDecimal bigOne = new BigDecimal(0);                      // 总和
        BigDecimal bigTwo = new BigDecimal(rateFolwRegions.size());     // 数量
        BigDecimal bigThree = new BigDecimal(0);                    // 商

        // 判断当前集团区数据库中是否存在
        Collection<RateFolwGroups> rateFolwGroups = rateFolwGroupsRepository.findByGroups(group);
        if (rateFolwGroups.size() == 0){
            rateFolwGroups.add(new RateFolwGroups(
                    JOIN,
                    group,
                    Instant.now()
            ));
            rateFolwGroups.add(new RateFolwGroups(
                    LEAVE,
                    group,
                    Instant.now()
            ));
        }
//        if (rateFolwGroups == null){
//            rateFolwGroups = new RateFolwGroups();
//            rateFolwGroups.setStatus(JOIN);
//            rateFolwGroups.setGroups(group);
//            rateFolwGroups.setCreatedAt(Instant.now());
//        }
        for (RateFolwGroups rateFolwGroup : rateFolwGroups) {
            rateFolwGroup.setUpdateAt(Instant.now());
            bigOne = new BigDecimal(0);
            for (RateFolwRegions rf : rateFolwRegions) {
                String monthValueStr = "";
                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        monthValueStr = rf.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValueStr = rf.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValueStr = rf.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValueStr = rf.getApril();
                        break;
                    case Calendar.MAY:
                        monthValueStr = rf.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.JULY:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.AUGUST:
                        monthValueStr = rf.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValueStr = rf.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValueStr = rf.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValueStr = rf.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValueStr = rf.getDecember();
                        break;
                }
                bigOne = bigOne.add(new BigDecimal(monthValueStr));
            }

            String valueStr = "0.00";
            if (!(bigOne.toString().equals("0.00") || bigOne.toString().equals("0"))) {
                bigThree = bigOne.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
                valueStr = bigThree.toString();
            }

            switch (calendar.get(Calendar.MONTH)) {
                case Calendar.JANUARY:
                    rateFolwGroup.setJanuary(valueStr);
                    break;
                case Calendar.FEBRUARY:
                    rateFolwGroup.setFebruary(valueStr);
                    break;
                case Calendar.MARCH:
                    rateFolwGroup.setMarch(valueStr);
                    break;
                case Calendar.APRIL:
                    rateFolwGroup.setApril(valueStr);
                    break;
                case Calendar.MAY:
                    rateFolwGroup.setMay(valueStr);
                    break;
                case Calendar.JUNE:
                    rateFolwGroup.setJune(valueStr);
                    break;
                case Calendar.JULY:
                    rateFolwGroup.setJuly(valueStr);
                    break;
                case Calendar.AUGUST:
                    rateFolwGroup.setAuguest(valueStr);
                    break;
                case Calendar.SEPTEMBER:
                    rateFolwGroup.setSeptember(valueStr);
                    break;
                case Calendar.OCTOBER:
                    rateFolwGroup.setOctober(valueStr);
                    break;
                case Calendar.NOVEMBER:
                    rateFolwGroup.setNovember(valueStr);
                    break;
                case Calendar.DECEMBER:
                    rateFolwGroup.setDecember(valueStr);
                    break;
            }
            // 结果保存
            rateFolwGroupsRepository.save(rateFolwGroup);
        }
        return rateFolwGroups;
    }

    // 大区入职率
    private Collection<RateFolwRegions> rateRegionsList(Regions region, Calendar calendar) {
        Collection<RateFolwProvinces> rateFolwProvinces = rateFolwProvincesRepository.findByRegionId(region.getId());

        BigDecimal bigOne = new BigDecimal(0);                      // 总和
        BigDecimal bigTwo = new BigDecimal(rateFolwProvinces.size());     // 数量
        BigDecimal bigThree = new BigDecimal(0);                    // 商

        // 判断当前大区数据库中是否存在
        Collection<RateFolwRegions> rateFolwRegions = rateFolwRegionsRepository.findByRegions(region);
        if (rateFolwRegions.size() == 0){
            rateFolwRegions.add(new RateFolwRegions(
                    JOIN,
                    region,
                    region.getGroupId(),
                    Instant.now()
            ));
            rateFolwRegions.add(new RateFolwRegions(
                    LEAVE,
                    region,
                    region.getGroupId(),
                    Instant.now()
            ));
        }
//        if (rateFolwRegions == null){
//            rateFolwRegions = new RateFolwRegions();
//            rateFolwRegions.setStatus(JOIN);
//            rateFolwRegions.setRegions(region);
//            rateFolwRegions.setGroupId(region.getGroupId());
//            rateFolwRegions.setCreatedAt(Instant.now());
//        }
            for (RateFolwRegions rateFolwRegion : rateFolwRegions) {
                rateFolwRegion.setUpdateAt(Instant.now());
                bigOne = new BigDecimal(0);
                for (RateFolwProvinces rf : rateFolwProvinces) {
                    String monthValueStr = "";
                    switch (calendar.get(Calendar.MONTH)) {
                        case Calendar.JANUARY:
                            monthValueStr = rf.getJanuary();
                            break;
                        case Calendar.FEBRUARY:
                            monthValueStr = rf.getFebruary();
                            break;
                        case Calendar.MARCH:
                            monthValueStr = rf.getMarch();
                            break;
                        case Calendar.APRIL:
                            monthValueStr = rf.getApril();
                            break;
                        case Calendar.MAY:
                            monthValueStr = rf.getMay();
                            break;
                        case Calendar.JUNE:
                            monthValueStr = rf.getJune();
                            break;
                        case Calendar.JULY:
                            monthValueStr = rf.getJune();
                            break;
                        case Calendar.AUGUST:
                            monthValueStr = rf.getAuguest();
                            break;
                        case Calendar.SEPTEMBER:
                            monthValueStr = rf.getSeptember();
                            break;
                        case Calendar.OCTOBER:
                            monthValueStr = rf.getOctober();
                            break;
                        case Calendar.NOVEMBER:
                            monthValueStr = rf.getNovember();
                            break;
                        case Calendar.DECEMBER:
                            monthValueStr = rf.getDecember();
                            break;
                    }
                    bigOne = bigOne.add(new BigDecimal(monthValueStr));
                }

                String valueStr = "0.00";
                if (!(bigOne.toString().equals("0.00") || bigOne.toString().equals("0"))) {
                    bigThree = bigOne.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
                    valueStr = bigThree.toString();
                }

                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        rateFolwRegion.setJanuary(valueStr);
                        break;
                    case Calendar.FEBRUARY:
                        rateFolwRegion.setFebruary(valueStr);
                        break;
                    case Calendar.MARCH:
                        rateFolwRegion.setMarch(valueStr);
                        break;
                    case Calendar.APRIL:
                        rateFolwRegion.setApril(valueStr);
                        break;
                    case Calendar.MAY:
                        rateFolwRegion.setMay(valueStr);
                        break;
                    case Calendar.JUNE:
                        rateFolwRegion.setJune(valueStr);
                        break;
                    case Calendar.JULY:
                        rateFolwRegion.setJuly(valueStr);
                        break;
                    case Calendar.AUGUST:
                        rateFolwRegion.setAuguest(valueStr);
                        break;
                    case Calendar.SEPTEMBER:
                        rateFolwRegion.setSeptember(valueStr);
                        break;
                    case Calendar.OCTOBER:
                        rateFolwRegion.setOctober(valueStr);
                        break;
                    case Calendar.NOVEMBER:
                        rateFolwRegion.setNovember(valueStr);
                        break;
                    case Calendar.DECEMBER:
                        rateFolwRegion.setDecember(valueStr);
                        break;
                }
                // 结果保存
                rateFolwRegionsRepository.save(rateFolwRegion);
            }
        return rateFolwRegions;
    }

    // 省入职率
    private Collection<RateFolwProvinces> rateProvinces(Provinces province, Calendar calendar) {
        Collection<RateFolwCity> rateFolwCities = rateFolwCityRepository.findByProvinceId(province.getId());

        BigDecimal bigOne = new BigDecimal(0);                      // 总和
        BigDecimal bigTwo = new BigDecimal(rateFolwCities.size());     // 数量
        BigDecimal bigThree = new BigDecimal(0);                    // 商

        // 判断当前省数据库中是否存在
        Collection<RateFolwProvinces> rateFolwProvinces = rateFolwProvincesRepository.findByProvinces(province);
        // 应该返回两条，如果返回0条，就创建两条
        if (rateFolwProvinces.size() == 0){
            rateFolwProvinces.add(new RateFolwProvinces(
                    JOIN,
                    province,
                    province.getRegionId(),
                    Instant.now()
            ));
            rateFolwProvinces.add(new RateFolwProvinces(
                    LEAVE,
                    province,
                    province.getRegionId(),
                    Instant.now()
            ));
        }
        for (RateFolwProvinces rateFolwProvince : rateFolwProvinces) {
            rateFolwProvince.setUpdateAt(Instant.now());

            for (RateFolwCity rf : rateFolwCities) {
                String monthValueStr = "";
                bigOne = new BigDecimal(0);
                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        monthValueStr = rf.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValueStr = rf.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValueStr = rf.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValueStr = rf.getApril();
                        break;
                    case Calendar.MAY:
                        monthValueStr = rf.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.JULY:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.AUGUST:
                        monthValueStr = rf.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValueStr = rf.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValueStr = rf.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValueStr = rf.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValueStr = rf.getDecember();
                        break;
                }
                bigOne = bigOne.add(new BigDecimal(monthValueStr));
            }

            String valueStr = "0.00";
            if (!(bigOne.toString().equals("0.00") || bigOne.toString().equals("0"))) {
                bigThree = bigOne.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
                valueStr = bigThree.toString();
            }

            switch (calendar.get(Calendar.MONTH)) {
                case Calendar.JANUARY:
                    rateFolwProvince.setJanuary(valueStr);
                    break;
                case Calendar.FEBRUARY:
                    rateFolwProvince.setFebruary(valueStr);
                    break;
                case Calendar.MARCH:
                    rateFolwProvince.setMarch(valueStr);
                    break;
                case Calendar.APRIL:
                    rateFolwProvince.setApril(valueStr);
                    break;
                case Calendar.MAY:
                    rateFolwProvince.setMay(valueStr);
                    break;
                case Calendar.JUNE:
                    rateFolwProvince.setJune(valueStr);
                    break;
                case Calendar.JULY:
                    rateFolwProvince.setJuly(valueStr);
                    break;
                case Calendar.AUGUST:
                    rateFolwProvince.setAuguest(valueStr);
                    break;
                case Calendar.SEPTEMBER:
                    rateFolwProvince.setSeptember(valueStr);
                    break;
                case Calendar.OCTOBER:
                    rateFolwProvince.setOctober(valueStr);
                    break;
                case Calendar.NOVEMBER:
                    rateFolwProvince.setNovember(valueStr);
                    break;
                case Calendar.DECEMBER:
                    rateFolwProvince.setDecember(valueStr);
                    break;
            }
            // 结果保存
            rateFolwProvincesRepository.save(rateFolwProvince);
        }
        return rateFolwProvinces;
    }

    private Collection<RateFolwCity> rateCity(Citys city, Calendar calendar) {
        Collection<RateFolwSchool> rateFolwSchools = rateFolwSchoolRepository.findByCityId(city.getId());

        BigDecimal bigOne = new BigDecimal(0);                      // 总和
        BigDecimal bigTwo = new BigDecimal(rateFolwSchools.size());     // 数量
        BigDecimal bigThree = new BigDecimal(0);                    // 商

        // 判断数据库中是否存在，一般情况下，会存在两个，一个入职、一个离职
        Collection<RateFolwCity> rateFolwCitys = rateFolwCityRepository.findByCitys(city);

        // 如果为空，一条都没有
        if (rateFolwCitys.size() == 0){
            rateFolwCitys.add(new RateFolwCity(
                    LEAVE,
                    city,
                    city.getProvinceId(),
                    Instant.now()
            ));
            rateFolwCitys.add(new RateFolwCity(
                    JOIN,
                    city,
                    city.getProvinceId(),
                    Instant.now()
            ));
        }
        for (RateFolwCity rateFolwCity : rateFolwCitys){
            rateFolwCity.setUpdateAt(Instant.now());
            bigOne = new BigDecimal(0);
            for (RateFolwSchool rf : rateFolwSchools) {
                String monthValueStr = "";
                switch (calendar.get(Calendar.MONTH)) {
                    case Calendar.JANUARY:
                        monthValueStr = rf.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValueStr = rf.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValueStr = rf.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValueStr = rf.getApril();
                        break;
                    case Calendar.MAY:
                        monthValueStr = rf.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.JULY:
                        monthValueStr = rf.getJune();
                        break;
                    case Calendar.AUGUST:
                        monthValueStr = rf.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValueStr = rf.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValueStr = rf.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValueStr = rf.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValueStr = rf.getDecember();
                        break;
                }
                bigOne = bigOne.add(new BigDecimal(monthValueStr));
            }

            String valueStr = "0.00";
            if (!(bigOne.toString().equals("0.00") || bigOne.toString().equals("0"))) {
                bigThree = bigOne.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
                valueStr = bigThree.toString();
            }

            switch (calendar.get(Calendar.MONTH)) {
                case Calendar.JANUARY:
                    rateFolwCity.setJanuary(valueStr);
                    break;
                case Calendar.FEBRUARY:
                    rateFolwCity.setFebruary(valueStr);
                    break;
                case Calendar.MARCH:
                    rateFolwCity.setMarch(valueStr);
                    break;
                case Calendar.APRIL:
                    rateFolwCity.setApril(valueStr);
                    break;
                case Calendar.MAY:
                    rateFolwCity.setMay(valueStr);
                    break;
                case Calendar.JUNE:
                    rateFolwCity.setJune(valueStr);
                    break;
                case Calendar.JULY:
                    rateFolwCity.setJuly(valueStr);
                    break;
                case Calendar.AUGUST:
                    rateFolwCity.setAuguest(valueStr);
                    break;
                case Calendar.SEPTEMBER:
                    rateFolwCity.setSeptember(valueStr);
                    break;
                case Calendar.OCTOBER:
                    rateFolwCity.setOctober(valueStr);
                    break;
                case Calendar.NOVEMBER:
                    rateFolwCity.setNovember(valueStr);
                    break;
                case Calendar.DECEMBER:
                    rateFolwCity.setDecember(valueStr);
                    break;
            }
            // 结果保存
            rateFolwCityRepository.save(rateFolwCity);
        }
        return rateFolwCitys;
    }

    public Collection<RateFolwSchool> findRateSchool(Actor actor, FolwEnum folwEnum) {
        School school = schoolRepository.findOne(actor.getSchoolId());
        String status = getStatus(folwEnum);
        return rateFolwSchoolRepository.findBySchoolAndStatus(school, status);
    }

    public Collection<RateFolwCity> findRateLeaveCity(Actor actor, FolwEnum folwEnum) {
        String status = getStatus(folwEnum);
        if (Strings.isNullOrEmpty(actor.getProvinceId())){
            return null;
        }
        Integer provinceId = Integer.valueOf(actor.getProvinceId());
        return rateFolwCityRepository.findByProvinceIdAndStatus(provinceId, status);
    }

    public Collection<RateFolwProvinces> findRateProvinces(Actor actor, FolwEnum folwEnum) {
        String status = getStatus(folwEnum);
        return rateFolwProvincesRepository.findByRegionIdAndStatus(Integer.getInteger(actor.getRegionId()), status);
    }

    public Collection<RateFolwRegions> findRateRegions(Actor actor, FolwEnum folwEnum) {
        String status = getStatus(folwEnum);
        return rateFolwRegionsRepository.findByGroupIdAndStatus(Integer.valueOf(actor.getGroupId()), status);
    }

//    public void addVirtualDate() {
//
//    }

    public static String getStatus(FolwEnum folwEnum){
        String status = "";
        if (folwEnum.equals(FolwEnum.JOINBY)){
            status = JOIN;
        }else if (folwEnum.equals(FolwEnum.LEAVEBY)){
            status = LEAVE;
        }
        return status;
    }

    public Map<String, Object> joinFlowDistrict(String code) {
        Map<String, Object> map = new HashMap<>();
        String index0 = code.substring(0,1);

        switch (index0){
            case "G":
                Groups groups = groupsRepository.findByCode(code);
                Collection<RateFolwRegions> rateFolwRegions = rateFolwRegionsRepository.findByGroupIdAndStatus(groups.getId(), JOIN);
                map.put("joinFlow", rateFolwRegions);
                break;
            case "Q":
                Regions regions = regionsRepository.findByCode(code);
                Collection<RateFolwProvinces> rateFolwProvinces = rateFolwProvincesRepository.findByRegionIdAndStatus(regions.getId(), JOIN);
                map.put("joinFlow", rateFolwProvinces);
            case "P":
                Provinces provinces = provincesRepository.findByCode(code);
                Collection<RateFolwCity> rateFolwCities = rateFolwCityRepository.findByProvinceIdAndStatus(provinces.getId(), JOIN);
                map.put("joinFlow", rateFolwCities);
            case "C":
                Citys citys = citysRepository.findByCode(code);
                Collection<RateFolwSchool> rateFolwSchools = rateFolwSchoolRepository.findByCityIdAndStatus(citys.getId(), JOIN);
                map.put("joinFlow", rateFolwSchools);
        }
        return map;
    }

    public Map<String, Object> leaveFlowDistrict(String code) {
        Map<String, Object> map = new HashMap<>();
        String index0 = code.substring(0,1);

        switch (index0){
            case "G":
                Groups groups = groupsRepository.findByCode(code);
                Collection<RateFolwRegions> rateFolwRegions = rateFolwRegionsRepository.findByGroupIdAndStatus(groups.getId(), LEAVE);
                map.put("leaveFlow", rateFolwRegions);
                break;
            case "Q":
                Regions regions = regionsRepository.findByCode(code);
                Collection<RateFolwProvinces> rateFolwProvinces = rateFolwProvincesRepository.findByRegionIdAndStatus(regions.getId(), LEAVE);
                map.put("leaveFlow", rateFolwProvinces);
            case "P":
                Provinces provinces = provincesRepository.findByCode(code);
                Collection<RateFolwCity> rateFolwCities = rateFolwCityRepository.findByProvinceIdAndStatus(provinces.getId(), LEAVE);
                map.put("leaveFlow", rateFolwCities);
            case "C":
                Citys citys = citysRepository.findByCode(code);
                Collection<RateFolwSchool> rateFolwSchools = rateFolwSchoolRepository.findByCityIdAndStatus(citys.getId(), LEAVE);
                map.put("leaveFlow", rateFolwSchools);
        }
        return map;
    }
}
