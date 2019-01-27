package cn.k12soft.servo.module.vedioMonitor.service;

import cn.k12soft.servo.domain.Klass;
import cn.k12soft.servo.domain.Student;
import cn.k12soft.servo.module.vedioMonitor.domain.HKDevice;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUser;
import cn.k12soft.servo.module.vedioMonitor.domain.HKUserDTO;
import cn.k12soft.servo.module.vedioMonitor.repository.HKDeviceRepository;
import cn.k12soft.servo.repository.KlassRepository;
import cn.k12soft.servo.repository.StudentRepository;
import cn.k12soft.servo.repository.UserRepository;
import cn.k12soft.servo.service.mapper.EntityMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class HKUserMapper extends EntityMapper<HKUser, HKUserDTO>{
    private final UserRepository userRepository;
    private final KlassRepository klassRepository;
    private final StudentRepository studentRepository;
    private final HKDeviceRepository hkDeviceRepository;

    public HKUserMapper(UserRepository userRepository, KlassRepository klassRepository, StudentRepository studentRepository, HKDeviceRepository hkDeviceRepository) {
        this.userRepository = userRepository;
        this.klassRepository = klassRepository;
        this.studentRepository = studentRepository;
        this.hkDeviceRepository = hkDeviceRepository;
    }

    @Override
    protected HKUserDTO convert(HKUser hkUser) {
        Klass klass = klassRepository.findOne(hkUser.getKlassId());
        Student student = studentRepository.findOne(hkUser.getStuId());
        String[] ida = hkUser.getHkDeviceIds().split(",");
        Set<HKDevice> hkDevices = new HashSet<>();

        if (ida.length != 0 && StringUtils.isEmpty(ida.toString())) {
            for (String id : ida) {
                HKDevice hkDevice = hkDeviceRepository.findOne(Integer.valueOf(id));
                hkDevices.add(hkDevice);
            }
        }

        HKUserDTO hkUserDTO = new HKUserDTO();
        hkUserDTO.setId(hkUser.getId());
        hkUserDTO.setAccountId(hkUser.getAccountId());
        hkUserDTO.setAccountName(hkUser.getAccountName());
        hkUserDTO.setPwd(hkUser.getPwd());
        hkUserDTO.setUser(userRepository.findOne(hkUser.getUserId()));
        hkUserDTO.setKlassId(klass.getId());
        hkUserDTO.setKlassName(klass.getName());
        hkUserDTO.setStuId(student.getId());
        hkUserDTO.setStuName(student.getName());
        hkUserDTO.setState(hkUser.getState());
        hkUserDTO.setStartTime(hkUser.getStartTime());
        hkUserDTO.setHkDevices(hkDevices);
        return hkUserDTO;
    }
}
