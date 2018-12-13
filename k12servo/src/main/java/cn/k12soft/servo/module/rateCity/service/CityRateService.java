package cn.k12soft.servo.module.rateCity.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.attendanceRate.domain.SchoolTeaRate;
import cn.k12soft.servo.module.attendanceRate.domain.StudentSchoolRate;
import cn.k12soft.servo.module.attendanceRate.repository.SchoolTeaRateRepository;
import cn.k12soft.servo.module.attendanceRate.repository.StudentSchoolRateRepository;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.rateCity.repository.CityStudentRateRepository;
import cn.k12soft.servo.module.rateCity.repository.CityTeacherRateRepository;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional
public class CityRateService {

    private final CitysRepository citysRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolTeaRateRepository schoolTeaRateRepository;
    private final CityStudentRateRepository cityStudentRateRepository;
    private final CityTeacherRateRepository cityTeacherRateRepository;
    private final StudentSchoolRateRepository studentSchoolRateRepository;

    @Autowired
    public CityRateService(CitysRepository citysRepository,
                           SchoolRepository schoolRepository,
                           SchoolTeaRateRepository schoolTeaRateRepository,
                           CityStudentRateRepository cityStudentRateRepository,
                           CityTeacherRateRepository cityTeacherRateRepository,
                           StudentSchoolRateRepository studentSchoolRateRepository) {
        this.citysRepository = citysRepository;
        this.schoolRepository = schoolRepository;
        this.schoolTeaRateRepository = schoolTeaRateRepository;
        this.cityStudentRateRepository = cityStudentRateRepository;
        this.cityTeacherRateRepository = cityTeacherRateRepository;
        this.studentSchoolRateRepository = studentSchoolRateRepository;
    }


    public void cityTeacherRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Citys> citys = citysRepository.findAll();
        for (Citys city : citys) {
            Integer cityId = city.getId();

            Collection<SchoolTeaRate> schoolTeaRates = schoolTeaRateRepository.findByCityId(cityId);
            if (schoolTeaRates.size() == 0){
                continue;
            }

            System.out.println("+++++");
            BigDecimal bigOne = new BigDecimal(0);  // 每个学校的出勤率
            BigDecimal bigTwo = new BigDecimal(schoolTeaRates.size());  // 学校数量
            BigDecimal bigThree = new BigDecimal(0);// 总出勤率、市出勤率
            String monthValue = "";
            // 循环相加
            for (SchoolTeaRate schoolTeaRate : schoolTeaRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = schoolTeaRate.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = schoolTeaRate.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = schoolTeaRate.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = schoolTeaRate.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = schoolTeaRate.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = schoolTeaRate.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = schoolTeaRate.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = schoolTeaRate.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = schoolTeaRate.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = schoolTeaRate.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = schoolTeaRate.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = schoolTeaRate.getDecember();
                        break;
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
                monthValue = String.valueOf(bigThree);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);

            CityTeacherRate ctr = cityTeacherRateRepository.queryByCitys(city);
            if (ctr == null){
                ctr = new CityTeacherRate();
                ctr.setCitys(city);
                ctr.setProvinceId(city.getProvinceId());
                ctr.setCreatedAt(Instant.now());
            }

            switch(calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    ctr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    ctr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    ctr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    ctr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    ctr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    ctr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    ctr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    ctr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    ctr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    ctr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    ctr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    ctr.setDecember(monthValue);
                    break;
            }
            cityTeacherRateRepository.save(ctr);

            city.setAtteTeacher(String.valueOf(ctr.getId()));
            citysRepository.save(city);
        }
    }

    public Collection<CityTeacherRate> findCityTeacherRates(Actor actor) {
        Integer cityId = Integer.valueOf(actor.getCityId());
        Citys citys = citysRepository.findById(cityId);
        return cityTeacherRateRepository.findByCitys(citys);
    }

    public void cityStudentRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,-1);
        Collection<Citys> citys = citysRepository.findAll();
        for (Citys city : citys){
            Integer cityId = Integer.valueOf(city.getId());
            String monthValue = "";
            Collection<StudentSchoolRate> studentSchoolRates = studentSchoolRateRepository.findByCityId(cityId);
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(studentSchoolRates.size());  // 学校数量
            BigDecimal bigThree = new BigDecimal(0);
            for (StudentSchoolRate ssr : studentSchoolRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = ssr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = ssr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = ssr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = ssr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = ssr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = ssr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = ssr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = ssr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = ssr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = ssr.getOctober();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = ssr.getDecember();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = ssr.getNovember();
                        break;
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
                bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
                monthValue = String.valueOf(bigThree);

                CityStudentRate csr = cityStudentRateRepository.queryByCitys(city);
                if (csr == null){
                    csr = new CityStudentRate();
                    csr.setCitys(city);
                    csr.setProvincesId(city.getProvinceId());
                    csr.setCreatedAt(Instant.now());
                }
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        csr.setJanuary(monthValue);
                        break;
                    case Calendar.FEBRUARY:
                        csr.setFebruary(monthValue);
                        break;
                    case Calendar.MARCH:
                        csr.setMarch(monthValue);
                        break;
                    case Calendar.APRIL:
                        csr.setApril(monthValue);
                        break;
                    case Calendar.MAY:
                        csr.setMay(monthValue);
                        break;
                    case Calendar.JUNE:
                        csr.setJune(monthValue);
                        break;
                    case Calendar.JULY:
                        csr.setJuly(monthValue);
                        break;
                    case Calendar.AUGUST:
                        csr.setAuguest(monthValue);
                        break;
                    case Calendar.SEPTEMBER:
                        csr.setSeptember(monthValue);
                        break;
                    case Calendar.OCTOBER:
                        csr.setOctober(monthValue);
                        break;
                    case Calendar.DECEMBER:
                        csr.setDecember(monthValue);
                        break;
                    case Calendar.NOVEMBER:
                        csr.setNovember(monthValue);
                        break;
                }

                cityStudentRateRepository.save(csr);
                city.setAtteStudent(String.valueOf(csr.getId()));

                city.setAtteStudent(String.valueOf(csr.getId()));
                citysRepository.save(city);
            }
        }
    }

    public Collection<CityStudentRate> findCityStudentRate(Actor actor) {
        Integer citysId = Integer.valueOf(actor.getCityId());
        Citys citys = citysRepository.findById(citysId);
        Collection<CityStudentRate> cityStudentRates = cityStudentRateRepository.findByCitys(citys);
        return cityStudentRates;
    }

    public Collection<SchoolTeaRate> findSchoolTeaRates(Actor actor) {
        Citys citys = citysRepository.findById(Integer.valueOf(actor.getCityId()));
        return schoolTeaRateRepository.findByCityId(citys.getId());
    }

    public Collection<StudentSchoolRate> getCityStudentRate(String code, Actor actor) {
        Citys citys = citysRepository.findByCode(code);
        Collection<StudentSchoolRate> studentSchoolRates = studentSchoolRateRepository.findByCityId(citys.getId());
        return studentSchoolRates;
    }

    public Collection<SchoolTeaRate> getCityTeacherRate(String code, Actor actor) {
        Citys citys = citysRepository.findByCode(code);
        Collection<SchoolTeaRate> schoolTeaRates = schoolTeaRateRepository.findByCityId(citys.getId());
        return schoolTeaRates;
    }

//    public Collection<SchoolStuRate> findSchoolStuRates(Actor actor) {
//        return null;
//    }
}
