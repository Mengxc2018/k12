package cn.k12soft.servo.module.rateProvince.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.rateCity.domain.CityStudentRate;
import cn.k12soft.servo.module.rateCity.domain.CityTeacherRate;
import cn.k12soft.servo.module.rateCity.repository.CityStudentRateRepository;
import cn.k12soft.servo.module.rateCity.repository.CityTeacherRateRepository;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceStudentsRateRepository;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceTeachersRateRepository;
import cn.k12soft.servo.module.zone.domain.Provinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional
public class ProvinceRateService {

    private final ProvincesRepository provincesRepository;
    private final CityTeacherRateRepository cityTeacherRateRepository;
    private final CityStudentRateRepository cityStudentRateRepository;
    private final ProvinceStudentsRateRepository provinceStudentsRateRepository;
    private final ProvinceTeachersRateRepository provinceTeachersRateRepository;

    @Autowired
    public ProvinceRateService(ProvincesRepository provincesRepository,
                               CityTeacherRateRepository cityTeacherRateRepository,
                               CityStudentRateRepository cityStudentRateRepository,
                               ProvinceStudentsRateRepository provinceStudentsRateRepository,
                               ProvinceTeachersRateRepository provinceTeachersRateRepository) {
        this.provincesRepository = provincesRepository;
        this.cityTeacherRateRepository = cityTeacherRateRepository;
        this.cityStudentRateRepository = cityStudentRateRepository;
        this.provinceStudentsRateRepository = provinceStudentsRateRepository;
        this.provinceTeachersRateRepository = provinceTeachersRateRepository;
    }


    public void provinceTeachersRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Provinces> provinces = provincesRepository.findAll();
        for (Provinces province : provinces){
            Integer provinceId = province.getId();
            String monthValue = "";
            Collection<CityTeacherRate> cityTeacherRates = cityTeacherRateRepository.findByProvinceId(provinceId);
            if (cityTeacherRates.size() == 0){
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(cityTeacherRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);

            for (CityTeacherRate ctr : cityTeacherRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = ctr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = ctr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = ctr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = ctr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = ctr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = ctr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = ctr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = ctr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = ctr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = ctr.getOctober();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = ctr.getDecember();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = ctr.getNovember();
                        break;
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);

            ProvinceTeachersRate ptr = provinceTeachersRateRepository.queryByProvinces(province);
            if (ptr == null){
                ptr = new ProvinceTeachersRate();
                ptr.setCreatedAt(Instant.now());
                ptr.setProvinces(province);
                ptr.setRegionsId(province.getRegionId());
            }

            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    ptr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    ptr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    ptr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    ptr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    ptr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    ptr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    ptr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    ptr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    ptr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    ptr.setOctober(monthValue);
                    break;
                case Calendar.DECEMBER:
                    ptr.setDecember(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    ptr.setNovember(monthValue);
                    break;
            }
            provinceTeachersRateRepository.save(ptr);

            province.setAtteTeacher(String.valueOf(ptr.getId()));
            provincesRepository.save(province);
        }
    }

    public Collection<ProvinceTeachersRate> findProvinceTeachersRate(Actor actor) {
        Provinces provinces = provincesRepository.findById(actor.getParentId());
        return provinceTeachersRateRepository.findByProvinces(provinces);
    }

    public Collection<CityTeacherRate> findCityTeacherRate(Actor actor) {
        Provinces provinces = provincesRepository.findById(Integer.valueOf(actor.getProvinceId()));
        return cityTeacherRateRepository.findByProvinceId(provinces.getId());
    }

    public void provinceStudentsRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Provinces> provinces = provincesRepository.findAll();
        for (Provinces province : provinces){
            Integer provinceId = province.getId();
            Collection<CityStudentRate> cityStudentRates = cityStudentRateRepository.findByProvincesId(provinceId);
            if (cityStudentRates.size() == 0){
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(cityStudentRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);
            String monthValue = "";
            for (CityStudentRate csr : cityStudentRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = csr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = csr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = csr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = csr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = csr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = csr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = csr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = csr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = csr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = csr.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = csr.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = csr.getDecember();
                        break;
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);
            ProvinceStudentsRate psr = provinceStudentsRateRepository.queryByProvinces(province);
            if (psr == null){
                psr = new ProvinceStudentsRate();
                psr.setCreatedAt(Instant.now());
                psr.setRegionId(province.getRegionId());
                psr.setProvinces(province);
            }
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    psr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    psr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    psr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    psr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    psr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    psr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    psr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    psr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    psr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    psr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    psr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    psr.setDecember(monthValue);
                    break;
            }
            provinceStudentsRateRepository.save(psr);

            province.setAtteStudent(String.valueOf(psr.getId()));
            provincesRepository.save(province);
        }
    }

    public Collection<ProvinceStudentsRate> findProvinceStudentsRate(Actor actor) {
        Provinces provinces = provincesRepository.findById(actor.getParentId());
        Collection<ProvinceStudentsRate> provinceStudentsRates = provinceStudentsRateRepository.findByProvinces(provinces);
        return provinceStudentsRates;
    }

    public Collection<CityStudentRate> findCityStudentRate(Actor actor) {
        Provinces provinces = provincesRepository.findById(Integer.valueOf(actor.getProvinceId()));
        return cityStudentRateRepository.findByProvincesId(provinces.getId());
    }

    public Collection<CityStudentRate> getCityStuRate(String code) {
        Provinces provinces = provincesRepository.findByCode(code);
        Collection<CityStudentRate> cityRates = cityStudentRateRepository.findByProvincesId(provinces.getId());
        return cityRates;
    }

    public Collection<CityTeacherRate> getCityTeaRate(String code) {
        Provinces provinces = provincesRepository.findByCode(code);
        Collection<CityTeacherRate> cityRates = cityTeacherRateRepository.findByProvinceId(provinces.getId());
        return cityRates;
    }
}
