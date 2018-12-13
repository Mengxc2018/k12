package cn.k12soft.servo.module.rateRegions.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceStudentsRate;
import cn.k12soft.servo.module.rateProvince.domain.ProvinceTeachersRate;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceStudentsRateRepository;
import cn.k12soft.servo.module.rateProvince.repository.ProvinceTeachersRateRepository;
import cn.k12soft.servo.module.rateRegions.domain.RegionsStudentRate;
import cn.k12soft.servo.module.rateRegions.domain.RegionsTeacherRate;
import cn.k12soft.servo.module.rateRegions.repository.RegionsStudentRateRepository;
import cn.k12soft.servo.module.rateRegions.repository.RegionsTeacherRateRepository;
import cn.k12soft.servo.module.zone.domain.Regions;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional
public class RegionsRateService {

    private final RegionsTeacherRateRepository regionsTeacherRateRepository;
    private final RegionsStudentRateRepository regionsStudentRateRepository;
    private final ProvinceTeachersRateRepository provinceTeachersRateRepository;
    private final ProvinceStudentsRateRepository provinceStudentsRateRepository;
    private final RegionsRepository regionsRepository;
    private final ProvincesRepository provincesRepository;

    public RegionsRateService(RegionsTeacherRateRepository regionsTeacherRateRepository,
                              RegionsStudentRateRepository regionsStudentRateRepository,
                              ProvinceTeachersRateRepository provinceTeachersRateRepository,
                              ProvinceStudentsRateRepository provinceStudentsRateRepository,
                              RegionsRepository regionsRepository,
                              ProvincesRepository provincesRepository) {
        this.regionsTeacherRateRepository = regionsTeacherRateRepository;
        this.regionsStudentRateRepository = regionsStudentRateRepository;
        this.provinceTeachersRateRepository = provinceTeachersRateRepository;
        this.provinceStudentsRateRepository = provinceStudentsRateRepository;
        this.regionsRepository = regionsRepository;
        this.provincesRepository = provincesRepository;
    }

    public void regionsTeacherRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Regions> regions = regionsRepository.findAll();
        for (Regions region : regions){
            Integer RegionsId = region.getId();
            Collection<ProvinceTeachersRate> provinceTeachersRates = provinceTeachersRateRepository.findByRegionsId(RegionsId);
            if (provinceTeachersRates.size() == 0){
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(provinceTeachersRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);
            String monthValue = "";
            for (ProvinceTeachersRate ptr : provinceTeachersRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = ptr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = ptr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = ptr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = ptr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = ptr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = ptr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = ptr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = ptr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = ptr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = ptr.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = ptr.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = ptr.getDecember();
                        break;
                }
                if (StringUtils.isEmpty(monthValue)){
                    monthValue = "0.00";
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);
            RegionsTeacherRate rtr = regionsTeacherRateRepository.queryByRegions(region);
            if (rtr == null){
                rtr = new RegionsTeacherRate();
                rtr.setCreatedAt(Instant.now());
                rtr.setRegions(region);
                rtr.setGroupId(region.getGroupId());
            }
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    rtr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    rtr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    rtr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    rtr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    rtr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    rtr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    rtr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    rtr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    rtr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    rtr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    rtr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    rtr.setDecember(monthValue);
                    break;
            }
            regionsTeacherRateRepository.save(rtr);

            region.setAtteTeacher(String.valueOf(rtr.getId()));
            regionsRepository.save(region);
        }
    }

    public Collection<RegionsTeacherRate> findRegionsTeacherRate(Actor actor) {
        Regions regions = regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
        return regionsTeacherRateRepository.findByRegions(regions);
    }

    public Collection<ProvinceTeachersRate> findProvinceTeachersRate(Actor actor) {
        Regions regions = regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
        return provinceTeachersRateRepository.findByRegionsId(regions.getId());
    }

    public void regionsStudentRate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Collection<Regions> regions = regionsRepository.findAll();
        for (Regions region : regions){
            Integer RegionsId = region.getId();
            Collection<ProvinceStudentsRate> provinceStudentsRates = provinceStudentsRateRepository.findByRegionId(RegionsId);
            if (provinceStudentsRates.size() == 0){
                continue;
            }
            BigDecimal bigOne = new BigDecimal(0);
            BigDecimal bigTwo = new BigDecimal(provinceStudentsRates.size());    // 城市数量
            BigDecimal bigThree = new BigDecimal(0);
            String monthValue = "";
            for (ProvinceStudentsRate psr : provinceStudentsRates){
                switch (calendar.get(Calendar.MONTH)){
                    case Calendar.JANUARY:
                        monthValue = psr.getJanuary();
                        break;
                    case Calendar.FEBRUARY:
                        monthValue = psr.getFebruary();
                        break;
                    case Calendar.MARCH:
                        monthValue = psr.getMarch();
                        break;
                    case Calendar.APRIL:
                        monthValue = psr.getApril();
                        break;
                    case Calendar.MAY:
                        monthValue = psr.getMay();
                        break;
                    case Calendar.JUNE:
                        monthValue = psr.getJune();
                        break;
                    case Calendar.JULY:
                        monthValue = psr.getJuly();
                        break;
                    case Calendar.AUGUST:
                        monthValue = psr.getAuguest();
                        break;
                    case Calendar.SEPTEMBER:
                        monthValue = psr.getSeptember();
                        break;
                    case Calendar.OCTOBER:
                        monthValue = psr.getOctober();
                        break;
                    case Calendar.NOVEMBER:
                        monthValue = psr.getNovember();
                        break;
                    case Calendar.DECEMBER:
                        monthValue = psr.getDecember();
                        break;
                }
                if (StringUtils.isEmpty(monthValue)){
                    monthValue = "0.00";
                }
                bigOne = new BigDecimal(monthValue);
                bigThree = bigThree.add(bigOne);
            }
            bigThree = bigThree.divide(bigTwo, 2, BigDecimal.ROUND_HALF_UP);
            monthValue = String.valueOf(bigThree);

            RegionsStudentRate rsr = regionsStudentRateRepository.queryByRegions(region);
            if (rsr == null){
                rsr = new RegionsStudentRate();
                rsr.setCreatedAt(Instant.now());
                rsr.setRegions(region);
                rsr.setGroupId(region.getGroupId());
            }
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:
                    rsr.setJanuary(monthValue);
                    break;
                case Calendar.FEBRUARY:
                    rsr.setFebruary(monthValue);
                    break;
                case Calendar.MARCH:
                    rsr.setMarch(monthValue);
                    break;
                case Calendar.APRIL:
                    rsr.setApril(monthValue);
                    break;
                case Calendar.MAY:
                    rsr.setMay(monthValue);
                    break;
                case Calendar.JUNE:
                    rsr.setJune(monthValue);
                    break;
                case Calendar.JULY:
                    rsr.setJuly(monthValue);
                    break;
                case Calendar.AUGUST:
                    rsr.setAuguest(monthValue);
                    break;
                case Calendar.SEPTEMBER:
                    rsr.setSeptember(monthValue);
                    break;
                case Calendar.OCTOBER:
                    rsr.setOctober(monthValue);
                    break;
                case Calendar.NOVEMBER:
                    rsr.setNovember(monthValue);
                    break;
                case Calendar.DECEMBER:
                    rsr.setDecember(monthValue);
                    break;
            }
            regionsStudentRateRepository.save(rsr);
            region.setAtteStudent(String.valueOf(rsr.getId()));
            regionsRepository.save(region);
        }

    }

    public Collection<RegionsStudentRate> findRegionsStudentRate(Actor actor) {
        Regions regions = regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
        return regionsStudentRateRepository.findByRegions(regions);
    }

    public Collection<ProvinceStudentsRate> findProvinceStudentsRate(Actor actor) {
        Regions regions = regionsRepository.findById(Integer.valueOf(actor.getRegionId()));
        return provinceStudentsRateRepository.findByRegionId(regions.getId());
    }

    public Collection<ProvinceStudentsRate> getProvinceStuRate(String code) {
        Regions regions = regionsRepository.findByCode(code);
        Collection<ProvinceStudentsRate> provinceRates = provinceStudentsRateRepository.findByRegionId(regions.getId());
        return provinceRates;
    }

    public Collection<ProvinceTeachersRate> getProvinceTeaRate(String code) {
        Regions regions = regionsRepository.findByCode(code);
        Collection<ProvinceTeachersRate> provinceRates = provinceTeachersRateRepository.findByRegionsId(regions.getId());
        return provinceRates;
    }
}
