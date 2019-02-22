package cn.k12soft.servo.module.district.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.domain.School;
import cn.k12soft.servo.domain.enumeration.ActorType;
import cn.k12soft.servo.module.district.form.CityForm;
import cn.k12soft.servo.module.district.form.ProvincesForm;
import cn.k12soft.servo.module.district.form.RegionsForm;
import cn.k12soft.servo.module.district.form.dto.CitysDTO;
import cn.k12soft.servo.module.district.form.dto.ProvincesDTO;
import cn.k12soft.servo.module.district.form.dto.RegionsDTO;
import cn.k12soft.servo.module.district.repository.CitysRepository;
import cn.k12soft.servo.module.district.repository.GroupsRepository;
import cn.k12soft.servo.module.district.repository.ProvincesRepository;
import cn.k12soft.servo.module.district.repository.RegionsRepository;
import cn.k12soft.servo.module.district.form.dto.GroupsDTO;
import cn.k12soft.servo.module.district.service.mapper.CitysMapper;
import cn.k12soft.servo.module.district.service.mapper.GroupsMapper;
import cn.k12soft.servo.module.district.service.mapper.ProvincesMapper;
import cn.k12soft.servo.module.district.service.mapper.RegionsMapper;
import cn.k12soft.servo.module.zone.domain.Citys;
import cn.k12soft.servo.module.zone.domain.Groups;
import cn.k12soft.servo.module.zone.domain.Provinces;
import cn.k12soft.servo.module.zone.domain.Regions;
import cn.k12soft.servo.repository.SchoolRepository;
import cn.k12soft.servo.service.dto.SchoolPojoDTO;
import cn.k12soft.servo.service.mapper.SchoolPojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.base.Strings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class DistrictService {

    private final CitysRepository citysRepository;
    private final ProvincesRepository provincesRepository;
    private final RegionsRepository regionsRepository;
    private final GroupsRepository groupsRepository;
    private final SchoolRepository schoolRepository;
    private final GroupsMapper groupsMapper;
    private final CitysMapper citysMapper;
    private final ProvincesMapper provincesMapper;
    private final RegionsMapper regionsMapper;
    private final SchoolPojoMapper schoolPojoMapper;

    @Autowired
    public DistrictService(CitysRepository citysRepository,
                           ProvincesRepository provincesRepository,
                           RegionsRepository regionsRepository,
                           GroupsRepository groupsRepository,
                           SchoolRepository schoolRepository,
                           GroupsMapper groupsMapper,
                           CitysMapper citysMapper,
                           ProvincesMapper provincesMapper,
                           RegionsMapper regionsMapper, SchoolPojoMapper schoolPojoMapper) {
        this.citysRepository = citysRepository;
        this.provincesRepository = provincesRepository;
        this.regionsRepository = regionsRepository;
        this.groupsRepository = groupsRepository;
        this.schoolRepository = schoolRepository;
        this.groupsMapper = groupsMapper;
        this.citysMapper = citysMapper;
        this.provincesMapper = provincesMapper;
        this.regionsMapper = regionsMapper;
        this.schoolPojoMapper = schoolPojoMapper;
    }

    private static final ActorType GROUP = ActorType.GROUP;   // 集团G
    private static final ActorType REGION = ActorType.REGION;   // 大区Q
    private static final ActorType PROVINCE = ActorType.PROVINCE;   // 省P
    private static final ActorType CITY = ActorType.CITY;   // 市C
    private static final ActorType MANAGER  = ActorType.MANAGER;   // 学校S
    private static final String G = "G";   // 集团G
    private static final String Q = "Q";   // 大区Q
    private static final String P = "P";   // 省P
    private static final String C = "C";   // 市C
    private static final String S  = "S";   // 学校S


    private static final int PARENTID = 0;

    /**
     * 默认创建学校
     * @param city
     */
    public void defaultSchool(Citys city){
        // 生成城市的时候如果没有学校默认生成一个学校
        Collection<School> schools = schoolRepository.findByCityId(city.getId());
        if (schools.size() <= 0) {
            School school = new School(
                    "默认学校",
                    "默认学校",
                    city.getId(),
                    city.getName(),
                    codeDistrict(ActorType.MANAGER)
            );
            schoolRepository.save(school);
        }
    }

    /**
     * 默认创建城市及学校
     * @param provinces
     */
    public void defaultCity(Provinces provinces){
        Integer countCity = citysRepository.countByProvinceId(provinces.getId());
        if (countCity <= 0){
            Citys citys = new Citys();
            citys.setName("默认城市");
            citys.setCode(codeDistrict(CITY));
            citys.setProvinceId(provinces.getId());
            citys.setCreateAt(Instant.now());
            citysRepository.save(citys);
            // 创建默认学校
            defaultSchool(citys);
        }
    }

    /**
     * 创建默认省市学校
     * @param regions
     */
    public void defaultProvinces(Regions regions){
        Collection<Provinces> provinceList = provincesRepository.findByregionId(regions.getId());
        if (provinceList.size() <= 0){

            Provinces provinces = new Provinces(
                    "默认省",
                    codeDistrict(PROVINCE),
                    regions.getId(),
                    regions.getName(),
                    Instant.now()
            );
            provincesRepository.save(provinces);
            defaultCity(provinces);
        }
    }

    /**
     * 创建默认大区及下级
     * @param groups
     */
    private void defaultGroups(Groups groups) {
        Collection<Regions> regionsList = regionsRepository.findByGroupId(groups.getId());
        if (regionsList.size() <= 0){
            Regions regions = new Regions(
                    "默认省",
                    groups.getId(),
                    groups.getName(),
                    codeDistrict(REGION),
                    Instant.now()
            );
            regionsRepository.save(regions);
            defaultProvinces(regions);
        }
    }

    public Citys CitySive(CityForm form) {

        Collection<Citys> citysRep = citysRepository.findByName(form.getName());
        // 区域名字效验
        if (citysRep.size() > 0){
            return null;
        }

        Provinces provinces = provincesRepository.findById(form.getProvinceId());

        Citys citys = new Citys();
        citys.setName(form.getName());
        citys.setCode(codeDistrict(CITY));
        citys.setCreateAt(Instant.now());
        citys.setProvinceName(provinces.getName());
        citys.setProvinceId(provinces.getId());
        citys = citysRepository.save(citys);

        // 生成城市的时候如果没有学校默认生成一个学校
        defaultSchool(citys);
        return citys;
    }

    public Citys CityUpdate(CityForm form, Integer id) {

        Collection<Citys> citysRep = citysRepository.findByName(form.getName());
        // 区域名字效验
        if (citysRep.size() > 0){
            return null;
        }


        Citys citys = citysRepository.findById(id);
        if (!Strings.isNullOrEmpty(form.getName())){
            citys.setName(form.getName());
        }
        if (!Strings.isNullOrEmpty(toString().valueOf(form.getProvinceId()))){
            citys.setProvinceId(form.getProvinceId());
        }
        return citysRepository.save(citys);
    }

    public void deleteCitys(Integer id) {
        Citys citys = citysRepository.findById(id);
        citysRepository.delete(citys);
    }

    public Collection<Citys> findCitys(Integer parentId) {
        Collection<Citys> citys = parentId == null ? citysRepository.findAll(new Sort(Sort.Direction.ASC, "provinceId"))
                : citysRepository.findByProvinceId(parentId);
        return citys;
    }

    public Provinces saveProvinces(ProvincesForm form) {

        // 区域城市名字效验
        Collection<Provinces> provincesRep = provincesRepository.findByName(form.getName());
        if (provincesRep.size() > 0){
            return null;
        }

        Regions regions = regionsRepository.findById(form.getRegionId());

        Provinces provinces = new Provinces(
                form.getName(),
                codeDistrict(PROVINCE),
                regions.getId(),
                regions.getName(),
                Instant.now()
        );
        provincesRepository.save(provinces);
        // 如果该省下面没有市，则新建一个默认的
        defaultCity(provinces);

        return provinces;
    }

    public Provinces provincesUpdate(ProvincesForm form, Integer id) {

        // 区域城市名字效验
        Collection<Provinces> provincesRep = provincesRepository.findByName(form.getName());
        if (provincesRep.size() > 0){
            return null;
        }

        Provinces provinces = provincesRepository.findById(id);
        if (!Strings.isNullOrEmpty(form.getName())){
            provinces.setName(form.getName());
        }
        if (!Strings.isNullOrEmpty(form.getRegionId().toString())){
            Regions regions = regionsRepository.findById(form.getRegionId());
            if (regions != null){
                provinces.setRegionId(form.getRegionId());
                provinces.setRegionName(regions.getName());
            }
        }
        return provincesRepository.save(provinces);
    }

    public void deleteProvinces(Integer id) {
        Provinces provinces = provincesRepository.findById(id);
        provincesRepository.delete(provinces);
    }

    public Collection<Provinces> findProvinces(Integer regionId) {
        Collection<Provinces> provinces = regionId == null ? provincesRepository.findAll(new Sort(Sort.Direction.ASC, "regionId"))
                : provincesRepository.findByregionId(regionId);
        return provinces;
    }

    public Regions createRegions(RegionsForm form) {

        // 名字重复效验
        Collection<Regions> regionsList = regionsRepository.findByName(form.getName());
        if (regionsList.size() > 0){
            return null;
        }

        Groups groups = groupsRepository.findById(form.getGroupId());
        if (groups == null){
            groups = new Groups();
            groups.setId(0);
            groups.setName("");
        }
        Regions regions = new Regions(
                form.getName(),
                groups.getId(),
                groups.getName(),
                codeDistrict(REGION),
                Instant.now()
        );
        regions = regionsRepository.save(regions);
        // 添加默认省市
        defaultProvinces(regions);
        return regions;
    }

    public Regions updateRegions(Integer id, RegionsForm form) {

        // 名字重复效验
        Collection<Regions> regionsList = regionsRepository.findByName(form.getName());
        if (regionsList.size() > 0){
            return null;
        }

        Regions regions = regionsRepository.findById(id);
        if (!Strings.isNullOrEmpty(form.getName())){
            regions.setName(form.getName());
        }
        if (!Strings.isNullOrEmpty(form.getGroupId().toString())){
            Groups group = groupsRepository.findById(form.getGroupId());
            if (group == null){
                group = new Groups();
                group.setId(0);
                group.setName("");
            }
            regions.setGroupName(group.getName());
            regions.setGroupId(group.getId());
        }
        return regionsRepository.save(regions);
    }

    public void deleteRegions(Integer id) {

        Regions regions = regionsRepository.findById(id);
        regionsRepository.delete(regions);

    }

    public Collection<Regions> findRegions(Integer groupsId){
        Collection<Regions> regionsList = groupsId == null ? regionsRepository.findAll()
                : regionsRepository.findByGroupId(groupsId);
        return regionsList;
    }

    public Groups createdGroups(String groupsName) {

        Collection<Groups> groupsList = groupsRepository.findByName(groupsName);
        if (groupsList.size() > 0){
            return null;
        }

        Groups groups = new Groups();
        groups.setName(groupsName);
        groups.setCode(codeDistrict(GROUP));
        groups.setCreatedAt(Instant.now());
        groups = groupsRepository.save(groups);

        // 新建集团，创建默认省市学校
        defaultGroups(groups);

        return groups;
    }


    public Groups updateGroups(Integer id, String groupsName) {

        Groups groups = groupsRepository.findById(id);

        Collection<Groups> groupsList = groupsRepository.findByName(groupsName);
        if (groupsList.size() > 0){
            return null;
        }

        if (!Strings.isNullOrEmpty(groupsName)){
            groups.setName(groupsName);
        }
        groups = groupsRepository.save(groups);
        return groups;
    }

    public void deleteGroups(Integer id) {
        Groups groups = groupsRepository.findById(id);
        groupsRepository.delete(groups);
    }

    public Collection<Groups> findGroups() {
        return groupsRepository.findAll();
    }

    /**
     * 集团关联大区
     * @param actor
     * @param code
     * @return
     */
    public Regions relevanceRegions(Actor actor, String code) {
        // 集团的id
        Integer groupId = Integer.valueOf(actor.getGroupId());
        // 根据code找到大区数据
        Regions regions = regionsRepository.findByCode(code);
        Groups groups = groupsRepository.findById(groupId);
        regions.setGroupId(groups.getId());
        regions.setGroupName(groups.getName());
        return regionsRepository.save(regions);
    }

    public Provinces relevanceProvinces(Actor actor, String code) {
        Integer regionId = Integer.valueOf(actor.getRegionId());
        Regions regions = regionsRepository.findById(regionId);
        Provinces provinces = provincesRepository.findByCode(code);
        provinces.setRegionName(regions.getName());
        provinces.setRegionId(regions.getId());
        return provincesRepository.save(provinces);
    }

    public Citys relevanceCity(Actor actor, String code) {
        Integer provincesId = Integer.valueOf(actor.getProvinceId());
        Provinces provinces = provincesRepository.findById(provincesId);
        Citys citys = citysRepository.findByCode(code);
        citys.setProvinceId(provinces.getId());
        citys.setProvinceName(provinces.getName());
        return citysRepository.save(citys);
    }

    public Citys relevanceSchool(Actor actor, String code) {
        Integer citysId = Integer.valueOf(actor.getCityId());
        Citys citys = citysRepository.findById(citysId);
        School school = schoolRepository.findByCode(code);
        school.setCityId(citys.getId());
        school.setCityName(citys.getName());
        return citysRepository.save(citys);
    }

    public void cancel(String code) {
        String one = code.substring(0,1);
        switch (one){
            case G:
                break;
            case Q:
                cancelRegions(code);
                break;
            case P:
                cancelProvince(code);
                break;
            case C:
                cancelCitys(code);
                break;
            case S:
                cancelSchool(code);
                break;
        }
    }

    public Regions cancelRegions(String code) {
        Regions regions = regionsRepository.findByCode(code);
        regions.setGroupName("");
        regions.setGroupId(0);
        return regionsRepository.save(regions);
    }

    public Provinces cancelProvince(String code) {
        Provinces provinces = provincesRepository.findByCode(code);
        provinces.setRegionName("");
        provinces.setRegionId(0);
        return provincesRepository.save(provinces);
    }

    public Citys cancelCitys(String code) {
        Citys citys = citysRepository.findByCode(code);
        citys.setProvinceId(0);
        citys.setProvinceName("");
        return citysRepository.save(citys);
    }

    public School cancelSchool(String code) {
        School school = schoolRepository.findByCode(code);
        school.setCityId(0);
        school.setCityName("");
        return schoolRepository.save(school);
    }

    public Collection<Regions> queryRegions(Actor actor) {
        return regionsRepository.findByGroupId(PARENTID);
    }

    public Collection<Provinces> queryProvinces(Actor actor) {
        return provincesRepository.findByregionId(PARENTID);
    }

    public Collection<Citys> queryCitys(Actor actor) {
        return citysRepository.findByProvinceId(PARENTID);
    }

    public Collection<School> querySchool(Actor actor) {
        return schoolRepository.findByCityId(PARENTID);
    }

    public Map<String, Object> findTree(Actor actor) {
        Map<String, Object> map = new HashMap<>();
        actor.getTypes().forEach(actorType -> {
            switch (actorType) {
                case CITY:
                    // 查询城市下面的学校
                    CitysDTO citysDTO = citysList(actor.getCityId());
                    map.put("citysDTO", citysDTO);
                    break;
                case PROVINCE:
                    // 查询省下面的市
                    ProvincesDTO provincesDTO = provinceList(actor.getProvinceId());
                    map.put("provincesDTO", provincesDTO);
                    break;
                case REGION:
                    RegionsDTO regionsDTO = regionList(actor.getRegionId());
                    map.put("regionsDTO", regionsDTO);
                    break;
                case GROUP:
                    GroupsDTO groupsDTO = groupsist(actor.getGroupId());
                    map.put("groupsDTO", groupsDTO);
                    break;
                default:
                    //do nothing
            }
        });
        return map;
    }

    public void correlation(String first, String second) {
        // G Q P C S
        String one = first.substring(0, 1);
        String two = second.substring(0, 1);
        if (one.equals(G) && two.equals(Q)){
            groupsToRegions(first, second);
        }else if (one.equals(Q) && two.equals(P)){
            regionsToProvinces(first, second);
        }else if (one.equals(P) && two.equals(C)){
            provincesToCitys(first, second);
        }else if (one.equals(C) && two.equals(S)){
            CitysToSchools(first, second);
        }else{
            throw new IllegalArgumentException("请检查上下级关系重新分配！");
        }
    }

    private void CitysToSchools(String first, String second) {
        Citys citys = citysRepository.findByCode(first);
        School school = schoolRepository.findByCode(second);
        school.setCityId(citys.getId());
        school.setCityName(citys.getName());
        schoolRepository.save(school);
    }

    private void provincesToCitys(String first, String second) {
        Provinces provinces = provincesRepository.findByCode(first);
        Citys citys = citysRepository.findByCode(second);
        citys.setProvinceId(provinces.getId());
        citys.setProvinceName(provinces.getName());
        citysRepository.save(citys);
    }

    private void regionsToProvinces(String first, String second) {
        Regions regions = regionsRepository.findByCode(first);
        Provinces provinces = provincesRepository.findByCode(second);
        provinces.setRegionId(regions.getId());
        provinces.setRegionName(regions.getName());
        provincesRepository.save(provinces);
    }

    private void groupsToRegions(String first, String second) {
        Groups groups = groupsRepository.findByCode(first);
        Regions regions = regionsRepository.findByCode(second);
        regions.setGroupId(groups.getId());
        regions.setGroupName(groups.getName());
        regionsRepository.save(regions);
    }


    public CitysDTO citysList(String cityId) {
        Integer cityIdInt = Integer.valueOf(cityId);
        CitysDTO citysDTO = citysMapper.toDTO(citysRepository.findById(cityIdInt));
        Collection<SchoolPojoDTO> schools = schoolPojoMapper.toDTOs(schoolRepository.findByCityId(cityIdInt));
        List<SchoolPojoDTO> schoolList = new ArrayList<>(schools);
        System.out.println(schoolList.size());
        if (schools.size() != 0){
            citysDTO.setChildren(schools);
        }
        return citysDTO;
    }

    public ProvincesDTO provinceList(String provinceId){
        Integer provinceIdInt = Integer.valueOf(provinceId);
        ProvincesDTO provincesDTO = provincesMapper.toDTO(provincesRepository.findById(provinceIdInt));
        Collection<CitysDTO> citys = citysMapper.toDTOs(citysRepository.findByProvinceId(provinceIdInt));
        List<CitysDTO> citysDTOS = new ArrayList<>(citys);
        for (int i = 0; i < citysDTOS.size(); i++){
            String regionId = String.valueOf(citysDTOS.get(i).getId());
            CitysDTO region1 = citysList(regionId);
            if (region1 != null){
                citysDTOS.get(i).setChildren(region1.getChildren());
            }
        }
        provincesDTO.setChildren(citys);
        return provincesDTO;
    }

    public RegionsDTO regionList(String regionsId){
        Integer regionIdInt = Integer.valueOf(regionsId);
        RegionsDTO regionDTO = regionsMapper.toDTO(regionsRepository.findById(regionIdInt));
        Collection<ProvincesDTO> provinces = provincesMapper.toDTOs(provincesRepository.findByregionId(regionIdInt));
        List<ProvincesDTO> provincesDTOS = new ArrayList<>(provinces);
        for (int i = 0; i < provincesDTOS.size(); i++){
            String regionId = String.valueOf(provincesDTOS.get(i).getId());
            ProvincesDTO region1 = provinceList(regionId);
            if (region1 != null){
                provincesDTOS.get(i).setChildren(region1.getChildren());
            }
        }
        regionDTO.setProvinces(provincesDTOS);
        return regionDTO;
    }

    public GroupsDTO groupsist(String groupId){
        Integer groupIdInt = Integer.valueOf(groupId);
        GroupsDTO groups = groupsMapper.toDTO(groupsRepository.findById(groupIdInt));
        Collection<RegionsDTO> regions = regionsMapper.toDTOs(regionsRepository.findByGroupId(groupIdInt));
        List<RegionsDTO> regionsDTOS = new ArrayList<>(regions);
        for (int i = 0; i < regionsDTOS.size(); i++){
            String regionId = String.valueOf(regionsDTOS.get(i).getId());
            RegionsDTO region1 = regionList(regionId);
            if (region1 != null){
                regionsDTOS.get(i).setProvinces(region1.getProvinces());
            }
        }
        groups.setChildren(regionsDTOS);
        return groups;
    }

    /**
     * 各区域编号生成规则
     * @param actorType
     * @return
     */
    public String codeDistrict(ActorType actorType){

        String result = "";
        String codeStr = "";
        boolean isRepet = false;
        do {
            result = String.valueOf(((int)((Math.random()*2+1)*10000)));
            String localDateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            if (CITY.equals(actorType)){
                codeStr = "C";
            }else if (PROVINCE.equals(actorType)){
                codeStr = "P";
            }else if(REGION.equals(actorType)){
                codeStr = "Q";
            }else if(GROUP.equals(actorType)){
                codeStr = "G";
            }else if(MANAGER.equals(actorType)){
                codeStr = "S";
            }
            codeStr = codeStr + localDateStr + result;

            isRepet = isRepet(codeStr, actorType);
        }while(isRepet);
        return codeStr;
    }

    /**
     * 区号重复校验
     * @param codeStr
     * @param actorType
     * @return
     */
    public boolean isRepet(String codeStr, ActorType actorType){
        boolean isRepet = false;    // 默认不重复
        Integer count = 0;
        if (CITY.equals(actorType)){
            count = citysRepository.countByCode(codeStr);
        }else if (PROVINCE.equals(actorType)){
            count = provincesRepository.countByCode(codeStr);
        }else if(REGION.equals(actorType)){
            count = regionsRepository.countByCode(codeStr);
        }else if(GROUP.equals(actorType)){
            count = groupsRepository.countByCode(codeStr);
        }else if(MANAGER.equals(actorType)){
            count = schoolRepository.countByCode(codeStr);
        }
        if (count > 0){
            isRepet = true;
        }
        return isRepet;
    }


}
