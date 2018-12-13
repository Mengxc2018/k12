package cn.k12soft.servo.module.commmontest;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentKlassRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.attendanceRate.domain.TeacherSchoolRate;
import cn.k12soft.servo.module.attendanceRate.repository.SchoolTeaRateRepository;
import cn.k12soft.servo.module.attendanceRate.repository.StudentKlassRateRepository;
import cn.k12soft.servo.module.attendanceRate.repository.StudentSchoolRateRepository;
import cn.k12soft.servo.module.attendanceRate.repository.TeacherSchoolRateRepository;
import cn.k12soft.servo.module.countIncomePayout.domain.income.*;
import cn.k12soft.servo.module.countIncomePayout.domain.payout.*;
import cn.k12soft.servo.module.countIncomePayout.repository.*;
import cn.k12soft.servo.module.countIncomePayout.service.CountMoneyService;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.empFlowRate.domain.*;
import cn.k12soft.servo.module.empFlowRate.repository.*;
import cn.k12soft.servo.module.empFlowRate.service.EmpFlowRateService;
import cn.k12soft.servo.module.employees.domain.Employee;
import cn.k12soft.servo.module.employees.repository.EmployeeRepository;
import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.rateCity.repository.CityStudentRateRepository;
import cn.k12soft.servo.module.rateCity.repository.CityTeacherRateRepository;
import cn.k12soft.servo.module.rateGroup.domain.GroupStudentRate;
import cn.k12soft.servo.module.rateGroup.domain.GroupTeacherRate;
import cn.k12soft.servo.module.rateGroup.repository.GroupStuRateRepositdry;
import cn.k12soft.servo.module.rateGroup.repository.GroupTeaRateRepositdry;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceStudentsRateRepository;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceTeachersRateRepository;
import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.module.rateRegions.repository.RegionsStudentRateRepository;
import cn.k12soft.servo.module.rateRegions.repository.RegionsTeacherRateRepository;
import cn.k12soft.servo.module.revenue.repository.IncomeRepository;
import cn.k12soft.servo.module.revenue.repository.PayoutRepository;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static cn.k12soft.servo.module.commmontest.Commonmxc.ramdom4Num;
import static cn.k12soft.servo.module.commmontest.Commonmxc.times;

@Service
@Transactional
public class CommonTestService {

    private final CountMoneyService countMoneyService;
    private final SchoolTeaRateRepository schoolTeaRateRepository;
    private final StudentKlassRateRepository studentKlassRateRepository;
    private final StudentSchoolRateRepository studentSchoolRateRepository;
    private final TeacherSchoolRateRepository teacherSchoolRateRepository;
    private final CityTeacherRateRepository cityTeacherRateRepository;
    private final CityStudentRateRepository cityStudentRateRepository;
    private final ProvinceStudentsRateRepository provinceStudentsRateRepository;
    private final ProvinceTeachersRateRepository provinceTeachersRateRepository;
    private final RegionsStudentRateRepository regionsStudentRateRepository;
    private final RegionsTeacherRateRepository regionsTeacherRateRepository;
    private final GroupTeaRateRepositdry groupTeaRateRepositdry;
    private final GroupStuRateRepositdry groupStuRateRepositdry;

    private final KlassRepository klassRepository;
    private final EmployeeRepository employeeRepository;


    private final CitysRepository citysRepository;
    private final ProvincesRepository provincesRepository;
    private final RegionsRepository regionsRepository;
    private final GroupsRepository groupsRepository;
    private final SchoolRepository schoolRepository;

    private final RateFolwSchoolRepository rateFolwSchoolRepository;
    private final EmpFlowRateService empFlowRateService;

    private final RateFolwCityRepository rateFolwCityRepository;
    private final RateFolwProvincesRepository rateFolwProvincesRepository;
    private final RateFolwRegionsRepository rateFolwRegionsRepository;
    private final RateFolwGroupsRepository rateFolwGroupsRepository;

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

    private final IncomeRepository incomeRepository;
    private final PayoutRepository payoutRepository;

    @Autowired
    public CommonTestService(CountMoneyService countMoneyService, SchoolTeaRateRepository schoolTeaRateRepository, StudentKlassRateRepository studentKlassRateRepository, StudentSchoolRateRepository studentSchoolRateRepository, TeacherSchoolRateRepository teacherSchoolRateRepository, CityTeacherRateRepository cityTeacherRateRepository, CityStudentRateRepository cityStudentRateRepository, ProvinceStudentsRateRepository provinceStudentsRateRepository, ProvinceTeachersRateRepository provinceTeachersRateRepository, RegionsStudentRateRepository regionsStudentRateRepository, RegionsTeacherRateRepository regionsTeacherRateRepository, GroupTeaRateRepositdry groupTeaRateRepositdry, GroupStuRateRepositdry groupStuRateRepositdry, KlassRepository klassRepository, EmployeeRepository employeeRepository, CitysRepository citysRepository, ProvincesRepository provincesRepository, RegionsRepository regionsRepository, GroupsRepository groupsRepository, SchoolRepository schoolRepository, RateFolwSchoolRepository rateFolwSchoolRepository, EmpFlowRateService empFlowRateService, RateFolwCityRepository rateFolwCityRepository, RateFolwProvincesRepository rateFolwProvincesRepository, RateFolwRegionsRepository rateFolwRegionsRepository, RateFolwGroupsRepository rateFolwGroupsRepository, CountIncSchRepository incSchRepository, CountIncCitysRepository incCitysRepository, CountIncGroupsRepository incGroupsRepository, CountIncRegionsRepository incRegionsRepository, CountIncProvincesRepository incProvincesRepository, CountPayoutSchRepository payoutSchRepository, CountPayoutCitysRepository payoutCitysRepository, CountPayoutGroupsRepository payoutGroupsRepository, CountPayoutRegionsRepository payoutRegionsRepository, CountPayoutProvincesRepository payoutProvincesRepository, IncomeRepository incomeRepository, PayoutRepository payoutRepository) {
        this.countMoneyService = countMoneyService;
        this.schoolTeaRateRepository = schoolTeaRateRepository;
        this.studentKlassRateRepository = studentKlassRateRepository;
        this.studentSchoolRateRepository = studentSchoolRateRepository;
        this.teacherSchoolRateRepository = teacherSchoolRateRepository;
        this.cityTeacherRateRepository = cityTeacherRateRepository;
        this.cityStudentRateRepository = cityStudentRateRepository;
        this.provinceStudentsRateRepository = provinceStudentsRateRepository;
        this.provinceTeachersRateRepository = provinceTeachersRateRepository;
        this.regionsStudentRateRepository = regionsStudentRateRepository;
        this.regionsTeacherRateRepository = regionsTeacherRateRepository;
        this.groupTeaRateRepositdry = groupTeaRateRepositdry;
        this.groupStuRateRepositdry = groupStuRateRepositdry;
        this.klassRepository = klassRepository;
        this.employeeRepository = employeeRepository;
        this.citysRepository = citysRepository;
        this.provincesRepository = provincesRepository;
        this.regionsRepository = regionsRepository;
        this.groupsRepository = groupsRepository;
        this.schoolRepository = schoolRepository;
        this.rateFolwSchoolRepository = rateFolwSchoolRepository;
        this.empFlowRateService = empFlowRateService;
        this.rateFolwCityRepository = rateFolwCityRepository;
        this.rateFolwProvincesRepository = rateFolwProvincesRepository;
        this.rateFolwRegionsRepository = rateFolwRegionsRepository;
        this.rateFolwGroupsRepository = rateFolwGroupsRepository;
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
        this.incomeRepository = incomeRepository;
        this.payoutRepository = payoutRepository;
    }


    private static final String LEAVE = "leave";
    private static final String JOIN = "join";

    public void countIncome() {
        countMoneyService.countIncome();
    }

    public void rateFolwSchool() {

        Collection<School> schools = schoolRepository.findAll();
        for (School school : schools){
            Collection<RateFolwSchool> rateFolwSchools = rateFolwSchoolRepository.findBySchool(school);
            if (rateFolwSchools.size() == 0) {
                rateFolwSchools.add(new RateFolwSchool(
                        "join",
                        school,
                        school.getCityId(),
                        Instant.now()
                ));
                rateFolwSchools.add(new RateFolwSchool(
                        "leave",
                        school,
                        school.getCityId(),
                        Instant.now()
                ));
            }
            for (RateFolwSchool rateFolwSchool : rateFolwSchools){
                rateFolwSchool.setUpdateAt(Instant.now());
                rateFolwSchool.setJanuary(ramdom4Num());
                rateFolwSchool.setFebruary(ramdom4Num());
                rateFolwSchool.setMarch(ramdom4Num());
                rateFolwSchool.setApril(ramdom4Num());
                rateFolwSchool.setMay(ramdom4Num());
                rateFolwSchool.setJune(ramdom4Num());
                rateFolwSchool.setJuly(ramdom4Num());
                rateFolwSchool.setAuguest(ramdom4Num());
                rateFolwSchool.setSeptember(ramdom4Num());
                rateFolwSchool.setOctober(ramdom4Num());
                rateFolwSchool.setNovember(ramdom4Num());
                rateFolwSchool.setDecember(ramdom4Num());

                rateFolwSchoolRepository.save(rateFolwSchool);
            }
        }
        empFlowRateService.rateJoin();

    }

    public void rateFlowTask() {

        empFlowRateService.joinSchoolsRate();   // 学校为单位所有员工入职率
        empFlowRateService.leaveSchoolsRate();  // 学校为单位所有员工离职率
        empFlowRateService.rateJoin();          // 人员入职离职汇总到省市大区

    }

    public void groupsList() {

        // 集团出勤率
        List<Groups> groups = groupsRepository.findAll();
        for (Groups group : groups){
            Integer groupId = group.getId();

            // 学生出勤率
            GroupStudentRate groupStudentRate = groupStuRateRepositdry.queryByGroups(group);
            if (groupStudentRate == null){
                groupStudentRate = new GroupStudentRate(
                        group, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                groupStudentRate.setJanuary(ramdom4Num());
                groupStudentRate.setFebruary(ramdom4Num());
                groupStudentRate.setMarch(ramdom4Num());
                groupStudentRate.setApril(ramdom4Num());
                groupStudentRate.setMay(ramdom4Num());
                groupStudentRate.setJune(ramdom4Num());
                groupStudentRate.setJuly(ramdom4Num());
                groupStudentRate.setAuguest(ramdom4Num());
                groupStudentRate.setSeptember(ramdom4Num());
                groupStudentRate.setOctober(ramdom4Num());
                groupStudentRate.setNovember(ramdom4Num());
                groupStudentRate.setDecember(ramdom4Num());
                groupStudentRate.setCreatedAt(Instant.now());
            }
            groupStuRateRepositdry.save(groupStudentRate);

            // 教师出勤率
            GroupTeacherRate groupTeaRate = groupTeaRateRepositdry.queryByGroups(group);
            if (groupTeaRate == null){
                groupTeaRate = new GroupTeacherRate(
                        group, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                groupTeaRate.setJanuary(ramdom4Num());
                groupTeaRate.setFebruary(ramdom4Num());
                groupTeaRate.setMarch(ramdom4Num());
                groupTeaRate.setApril(ramdom4Num());
                groupTeaRate.setMay(ramdom4Num());
                groupTeaRate.setJune(ramdom4Num());
                groupTeaRate.setJuly(ramdom4Num());
                groupTeaRate.setAuguest(ramdom4Num());
                groupTeaRate.setSeptember(ramdom4Num());
                groupTeaRate.setOctober(ramdom4Num());
                groupTeaRate.setNovember(ramdom4Num());
                groupTeaRate.setDecember(ramdom4Num());
                groupTeaRate.setCreatedAt(Instant.now());
            }
            groupTeaRateRepositdry.save(groupTeaRate);

            // 处理集团下的大区的出勤率
            regionRate(groupId);
        }
    }

    public void addRateJoin() {


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
        for (RateFolwGroups rateFolwGroup : rateFolwGroups) {
            rateFolwGroup.setUpdateAt(Instant.now());
            rateFolwGroup.setJanuary(ramdom4Num());
            rateFolwGroup.setFebruary(ramdom4Num());
            rateFolwGroup.setMarch(ramdom4Num());
            rateFolwGroup.setApril(ramdom4Num());
            rateFolwGroup.setMay(ramdom4Num());
            rateFolwGroup.setJune(ramdom4Num());
            rateFolwGroup.setJuly(ramdom4Num());
            rateFolwGroup.setAuguest(ramdom4Num());
            rateFolwGroup.setSeptember(ramdom4Num());
            rateFolwGroup.setOctober(ramdom4Num());
            rateFolwGroup.setNovember(ramdom4Num());
            rateFolwGroup.setDecember(ramdom4Num());
            // 结果保存
            rateFolwGroupsRepository.save(rateFolwGroup);
        }
        return rateFolwGroups;
    }

    // 大区入职率
    private Collection<RateFolwRegions> rateRegionsList(Regions region, Calendar calendar) {

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
        for (RateFolwRegions rateFolwRegion : rateFolwRegions) {
            rateFolwRegion.setUpdateAt(Instant.now());
            rateFolwRegion.setJanuary(ramdom4Num());
            rateFolwRegion.setFebruary(ramdom4Num());
            rateFolwRegion.setMarch(ramdom4Num());
            rateFolwRegion.setApril(ramdom4Num());
            rateFolwRegion.setMay(ramdom4Num());
            rateFolwRegion.setJune(ramdom4Num());
            rateFolwRegion.setJuly(ramdom4Num());
            rateFolwRegion.setAuguest(ramdom4Num());
            rateFolwRegion.setSeptember(ramdom4Num());
            rateFolwRegion.setOctober(ramdom4Num());
            rateFolwRegion.setNovember(ramdom4Num());
            rateFolwRegion.setDecember(ramdom4Num());
            // 结果保存
            rateFolwRegionsRepository.save(rateFolwRegion);
        }
        return rateFolwRegions;
    }

    // 省入职率
    private Collection<RateFolwProvinces> rateProvinces(Provinces province, Calendar calendar) {

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

            rateFolwProvince.setJanuary(ramdom4Num());
            rateFolwProvince.setFebruary(ramdom4Num());
            rateFolwProvince.setMarch(ramdom4Num());
            rateFolwProvince.setApril(ramdom4Num());
            rateFolwProvince.setMay(ramdom4Num());
            rateFolwProvince.setJune(ramdom4Num());
            rateFolwProvince.setJuly(ramdom4Num());
            rateFolwProvince.setAuguest(ramdom4Num());
            rateFolwProvince.setSeptember(ramdom4Num());
            rateFolwProvince.setOctober(ramdom4Num());
            rateFolwProvince.setNovember(ramdom4Num());
            rateFolwProvince.setDecember(ramdom4Num());
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
            rateFolwCity.setJanuary(ramdom4Num());
            rateFolwCity.setFebruary(ramdom4Num());
            rateFolwCity.setMarch(ramdom4Num());
            rateFolwCity.setApril(ramdom4Num());
            rateFolwCity.setMay(ramdom4Num());
            rateFolwCity.setJune(ramdom4Num());
            rateFolwCity.setJuly(ramdom4Num());
            rateFolwCity.setAuguest(ramdom4Num());
            rateFolwCity.setSeptember(ramdom4Num());
            rateFolwCity.setOctober(ramdom4Num());
            rateFolwCity.setNovember(ramdom4Num());
            rateFolwCity.setDecember(ramdom4Num());
            // 结果保存
            rateFolwCityRepository.save(rateFolwCity);
        }
        return rateFolwCitys;
    }

    // 大区出勤率
    public void regionRate(Integer groupId){
        Collection<Regions> regions = regionsRepository.findByGroupId(groupId);
        for (Regions region : regions){
            Integer regionId = region.getId();

            // 大区学生出勤率
            RegionsStudentRate regionsStudentRate = regionsStudentRateRepository.queryByRegions(region);
            if (regionsStudentRate == null){
                regionsStudentRate = new RegionsStudentRate(
                        region, groupId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                regionsStudentRate.setJanuary(ramdom4Num());
                regionsStudentRate.setFebruary(ramdom4Num());
                regionsStudentRate.setMarch(ramdom4Num());
                regionsStudentRate.setApril(ramdom4Num());
                regionsStudentRate.setMay(ramdom4Num());
                regionsStudentRate.setJune(ramdom4Num());
                regionsStudentRate.setJuly(ramdom4Num());
                regionsStudentRate.setAuguest(ramdom4Num());
                regionsStudentRate.setSeptember(ramdom4Num());
                regionsStudentRate.setOctober(ramdom4Num());
                regionsStudentRate.setNovember(ramdom4Num());
                regionsStudentRate.setDecember(ramdom4Num());
                regionsStudentRate.setCreatedAt(Instant.now());
            }
            regionsStudentRateRepository.save(regionsStudentRate);

            // 大区教师出勤率
            RegionsTeacherRate regionsTeacherRate = regionsTeacherRateRepository.queryByRegions(region);
            if (regionsTeacherRate == null){
                regionsTeacherRate = new RegionsTeacherRate(
                        region, groupId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                regionsTeacherRate.setJanuary(ramdom4Num());
                regionsTeacherRate.setFebruary(ramdom4Num());
                regionsTeacherRate.setMarch(ramdom4Num());
                regionsTeacherRate.setApril(ramdom4Num());
                regionsTeacherRate.setMay(ramdom4Num());
                regionsTeacherRate.setJune(ramdom4Num());
                regionsTeacherRate.setJuly(ramdom4Num());
                regionsTeacherRate.setAuguest(ramdom4Num());
                regionsTeacherRate.setSeptember(ramdom4Num());
                regionsTeacherRate.setOctober(ramdom4Num());
                regionsTeacherRate.setNovember(ramdom4Num());
                regionsTeacherRate.setDecember(ramdom4Num());
                regionsTeacherRate.setCreatedAt(Instant.now());
            }
            regionsTeacherRateRepository.save(regionsTeacherRate);

            // 省出勤率
            ProvinceRate(regionId);
        }
    }

    // 省出勤率
    private void ProvinceRate(Integer regionId) {
        Collection<Provinces> provinces = provincesRepository.findByregionId(regionId);
        for (Provinces province : provinces){
            Integer provinceId = province.getId();

            // 省学生出勤率
            ProvinceStudentsRate provinceStudentsRate = provinceStudentsRateRepository.queryByProvinces(province);
            if (provinceStudentsRate == null){
                provinceStudentsRate = new ProvinceStudentsRate(
                        province, regionId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                provinceStudentsRate.setJanuary(ramdom4Num());
                provinceStudentsRate.setFebruary(ramdom4Num());
                provinceStudentsRate.setMarch(ramdom4Num());
                provinceStudentsRate.setApril(ramdom4Num());
                provinceStudentsRate.setMay(ramdom4Num());
                provinceStudentsRate.setJune(ramdom4Num());
                provinceStudentsRate.setJuly(ramdom4Num());
                provinceStudentsRate.setAuguest(ramdom4Num());
                provinceStudentsRate.setSeptember(ramdom4Num());
                provinceStudentsRate.setOctober(ramdom4Num());
                provinceStudentsRate.setNovember(ramdom4Num());
                provinceStudentsRate.setDecember(ramdom4Num());
                provinceStudentsRate.setCreatedAt(Instant.now());
            }
            provinceStudentsRateRepository.save(provinceStudentsRate);

            // 省教师出勤率
            ProvinceTeachersRate provinceTeachersRate = provinceTeachersRateRepository.queryByProvinces(province);
            if (provinceTeachersRate == null){
                provinceTeachersRate = new ProvinceTeachersRate(
                        province, regionId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                provinceTeachersRate.setJanuary(ramdom4Num());
                provinceTeachersRate.setFebruary(ramdom4Num());
                provinceTeachersRate.setMarch(ramdom4Num());
                provinceTeachersRate.setApril(ramdom4Num());
                provinceTeachersRate.setMay(ramdom4Num());
                provinceTeachersRate.setJune(ramdom4Num());
                provinceTeachersRate.setJuly(ramdom4Num());
                provinceTeachersRate.setAuguest(ramdom4Num());
                provinceTeachersRate.setSeptember(ramdom4Num());
                provinceTeachersRate.setOctober(ramdom4Num());
                provinceTeachersRate.setNovember(ramdom4Num());
                provinceTeachersRate.setDecember(ramdom4Num());
                provinceTeachersRate.setCreatedAt(Instant.now());
            }
            provinceTeachersRateRepository.save(provinceTeachersRate);

            // 市出勤率
            cityRate(provinceId);
        }
    }

    // 市出勤率
    private void cityRate(Integer provinceId) {
        Collection<Citys> citys = citysRepository.findByProvinceId(provinceId);
        for (Citys city : citys){

            // 市学生出勤率
            CityStudentRate cityStudentRate = cityStudentRateRepository.queryByCitys(city);
            if (cityStudentRate == null){
                cityStudentRate = new CityStudentRate(
                        city, provinceId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                cityStudentRate.setJanuary(ramdom4Num());
                cityStudentRate.setFebruary(ramdom4Num());
                cityStudentRate.setMarch(ramdom4Num());
                cityStudentRate.setApril(ramdom4Num());
                cityStudentRate.setMay(ramdom4Num());
                cityStudentRate.setJune(ramdom4Num());
                cityStudentRate.setJuly(ramdom4Num());
                cityStudentRate.setAuguest(ramdom4Num());
                cityStudentRate.setSeptember(ramdom4Num());
                cityStudentRate.setOctober(ramdom4Num());
                cityStudentRate.setNovember(ramdom4Num());
                cityStudentRate.setDecember(ramdom4Num());
                cityStudentRate.setCreatedAt(Instant.now());
            }
            cityStudentRateRepository.save(cityStudentRate);

            // 市教师出勤率
            CityTeacherRate cityTeacherRate = cityTeacherRateRepository.queryByCitys(city);
            if (cityTeacherRate == null){
                cityTeacherRate = new CityTeacherRate(
                        city, provinceId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                cityTeacherRate.setJanuary(ramdom4Num());
                cityTeacherRate.setFebruary(ramdom4Num());
                cityTeacherRate.setMarch(ramdom4Num());
                cityTeacherRate.setApril(ramdom4Num());
                cityTeacherRate.setMay(ramdom4Num());
                cityTeacherRate.setJune(ramdom4Num());
                cityTeacherRate.setJuly(ramdom4Num());
                cityTeacherRate.setAuguest(ramdom4Num());
                cityTeacherRate.setSeptember(ramdom4Num());
                cityTeacherRate.setOctober(ramdom4Num());
                cityTeacherRate.setNovember(ramdom4Num());
                cityTeacherRate.setDecember(ramdom4Num());
                cityTeacherRate.setCreatedAt(Instant.now());
            }
            cityTeacherRateRepository.save(cityTeacherRate);


            // 学校出勤率
            schoolRate(city.getId());
        }
    }

    public void schoolRate(Integer cityId){
        Collection<School> schools = schoolRepository.findByCityId(cityId);
        for (School school : schools) {

            // 学校学生出勤率
            StudentSchoolRate studentSchoolRate = studentSchoolRateRepository.findBySchoolId(school.getId());
            if (studentSchoolRate == null) {
                studentSchoolRate = new StudentSchoolRate(
                        school, cityId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                studentSchoolRate.setJanuary(ramdom4Num());
                studentSchoolRate.setFebruary(ramdom4Num());
                studentSchoolRate.setMarch(ramdom4Num());
                studentSchoolRate.setApril(ramdom4Num());
                studentSchoolRate.setMay(ramdom4Num());
                studentSchoolRate.setJune(ramdom4Num());
                studentSchoolRate.setJuly(ramdom4Num());
                studentSchoolRate.setAuguest(ramdom4Num());
                studentSchoolRate.setSeptember(ramdom4Num());
                studentSchoolRate.setOctober(ramdom4Num());
                studentSchoolRate.setNovember(ramdom4Num());
                studentSchoolRate.setDecember(ramdom4Num());
                studentSchoolRate.setCreatedAt(Instant.now());
            }
            studentSchoolRateRepository.save(studentSchoolRate);

            // 学校教师出勤率
            SchoolTeaRate schoolTeaRate = schoolTeaRateRepository.findBySchool(school);
            if (schoolTeaRate == null) {
                schoolTeaRate = new SchoolTeaRate(
                        school, cityId, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                schoolTeaRate.setJanuary(ramdom4Num());
                schoolTeaRate.setFebruary(ramdom4Num());
                schoolTeaRate.setMarch(ramdom4Num());
                schoolTeaRate.setApril(ramdom4Num());
                schoolTeaRate.setMay(ramdom4Num());
                schoolTeaRate.setJune(ramdom4Num());
                schoolTeaRate.setJuly(ramdom4Num());
                schoolTeaRate.setAuguest(ramdom4Num());
                schoolTeaRate.setSeptember(ramdom4Num());
                schoolTeaRate.setOctober(ramdom4Num());
                schoolTeaRate.setNovember(ramdom4Num());
                schoolTeaRate.setDecember(ramdom4Num());
                schoolTeaRate.setCreatedAt(Instant.now());
            }
            schoolTeaRateRepository.save(schoolTeaRate);

            // 学校每个教师出勤率
            teacherRate(school.getId());


            // 班级学生出勤率
            klassRate(school.getId());

        }
    }

    public void teacherRate(Integer schoolId){
        School school = schoolRepository.findOne(schoolId);
        Collection<Employee> employees = employeeRepository.findBySchoolId(schoolId);
        for (Employee employee : employees){
            Integer actorId = employee.getActorId();
            TeacherSchoolRate teacherSchoolRate = teacherSchoolRateRepository.findByActorId(actorId);
            if (teacherSchoolRate == null){
                teacherSchoolRate = new TeacherSchoolRate(
                        school, actorId, school.getCityId(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                teacherSchoolRate.setJanuary(ramdom4Num());
                teacherSchoolRate.setFebruary(ramdom4Num());
                teacherSchoolRate.setMarch(ramdom4Num());
                teacherSchoolRate.setApril(ramdom4Num());
                teacherSchoolRate.setMay(ramdom4Num());
                teacherSchoolRate.setJune(ramdom4Num());
                teacherSchoolRate.setJuly(ramdom4Num());
                teacherSchoolRate.setAuguest(ramdom4Num());
                teacherSchoolRate.setSeptember(ramdom4Num());
                teacherSchoolRate.setOctober(ramdom4Num());
                teacherSchoolRate.setNovember(ramdom4Num());
                teacherSchoolRate.setDecember(ramdom4Num());
                teacherSchoolRate.setCreatedAt(Instant.now());
            }
            teacherSchoolRateRepository.save(teacherSchoolRate);
        }
    }

    public void klassRate(Integer schoolId) {
        Collection<Klass> klassList = klassRepository.findAllBySchoolId(schoolId);
        School school = schoolRepository.findOne(schoolId);

        // 班级学生出勤率
        for (Klass klass : klassList){
            StudentKlassRate studentKlassRate = studentKlassRateRepository.findByKlass(klass);
            if (studentKlassRate == null){
                studentKlassRate = new StudentKlassRate(
                        klass, school, ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), ramdom4Num(), Instant.now()
                );
            }else{
                studentKlassRate.setJanuary(ramdom4Num());
                studentKlassRate.setFebruary(ramdom4Num());
                studentKlassRate.setMarch(ramdom4Num());
                studentKlassRate.setApril(ramdom4Num());
                studentKlassRate.setMay(ramdom4Num());
                studentKlassRate.setJune(ramdom4Num());
                studentKlassRate.setJuly(ramdom4Num());
                studentKlassRate.setAuguest(ramdom4Num());
                studentKlassRate.setSeptember(ramdom4Num());
                studentKlassRate.setOctober(ramdom4Num());
                studentKlassRate.setNovember(ramdom4Num());
                studentKlassRate.setDecember(ramdom4Num());
                studentKlassRate.setCreatedAt(Instant.now());
            }
            studentKlassRateRepository.save(studentKlassRate);
        }
    }

    public void addDateIncomePayout() {

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

        // 查询是否存在
        CountPayoutGroups obj = payoutGroupsRepository.findByGroups(group);
        if (obj == null){
            obj = new CountPayoutGroups();
            obj.setGroups(group);
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());
        

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
        // 查询是否存在
        CountIncomeGroups obj = incGroupsRepository.findByGroups(group);
        if (obj == null){
            obj = new CountIncomeGroups();
            obj.setGroups(group);
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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
        // 查询是否存在
        CountPayoutRegions obj = payoutRegionsRepository.findByRegions(region);
        if (obj == null){
            obj = new CountPayoutRegions();
            obj.setRegions(region);
            obj.setGroupId(region.getGroupId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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
        // 查询是否存在
        CountIncomeRegions obj = incRegionsRepository.findByRegions(region);
        if (obj == null){
            obj = new CountIncomeRegions();
            obj.setRegions(region);
            obj.setGroupId(region.getGroupId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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
        // 查询是否存在
        CountIncomeProvinces obj = incProvincesRepository.findByProvinces(province);
        if (obj == null){
            obj = new CountIncomeProvinces();
            obj.setProvinces(province);
            obj.setRegionId(province.getRegionId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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
        // 查询是否存在
        CountPayoutProvinces obj = payoutProvincesRepository.findByProvinces(province);
        if (obj == null){
            obj = new CountPayoutProvinces();
            obj.setProvinces(province);
            obj.setRegionId(province.getRegionId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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

        // 查询是否存在
        CountPayoutCitys obj = payoutCitysRepository.findByCitys(city);
        if (obj == null){
            obj = new CountPayoutCitys();
            obj.setCitys(city);
            obj.setProvinceId(city.getProvinceId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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

        // 查询是否存在
        CountIncomeCitys obj = incCitysRepository.findByCitys(city);
        if (obj == null){
            obj = new CountIncomeCitys();
            obj.setCitys(city);
            obj.setProvinceId(city.getProvinceId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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

        // 查询是否存在
        CountPayoutSchools obj = payoutSchRepository.findBySchool(school);
        if (obj == null){
            obj = new CountPayoutSchools();
            obj.setSchool(school);
            obj.setCityId(school.getCityId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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

        // 查询是否存在
        CountIncomeSchools obj = incSchRepository.findBySchool(school);
        if (obj == null){
            obj = new CountIncomeSchools();
            obj.setSchool(school);
            obj.setCityId(school.getCityId());
            obj.setCreatedAt(Instant.now());
        }
        obj.setUpdateAt(Instant.now());

        obj.setUpdateAt(Instant.now());
        obj.setUpdateAt(Instant.now());
        obj.setJanuary(ramdom4Num());
        obj.setFebruary(ramdom4Num());
        obj.setMarch(ramdom4Num());
        obj.setApril(ramdom4Num());
        obj.setMay(ramdom4Num());
        obj.setJune(ramdom4Num());
        obj.setJuly(ramdom4Num());
        obj.setAuguest(ramdom4Num());
        obj.setSeptember(ramdom4Num());
        obj.setOctober(ramdom4Num());
        obj.setDecember(ramdom4Num());
        obj.setNovember(ramdom4Num());

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
}
