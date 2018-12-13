package cn.k12soft.servo.module.supplier.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.revenue.domain.PayoutSubType;
import cn.k12soft.servo.module.revenue.repository.PayoutSubTypeRepository;
import cn.k12soft.servo.module.supplier.domain.Supplier;
import cn.k12soft.servo.module.supplier.domain.form.SupplierForm;
import cn.k12soft.servo.module.supplier.repository.SupplierRepository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class SupplierService extends AbstractRepositoryService<Supplier, Long, SupplierRepository> {
    private final PayoutSubTypeRepository payoutSubTypeRepository;
    protected SupplierService(SupplierRepository repository, PayoutSubTypeRepository payoutSubTypeRepository) {
        super(repository);
        this.payoutSubTypeRepository = payoutSubTypeRepository;
    }

    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    public void createMany(List<SupplierForm> forms) {
        for (SupplierForm form : forms){
            CompletableFuture future = CompletableFuture.supplyAsync(() -> {
                create(form);
                return null;
            });
        }
    }

    public void create(SupplierForm form){
        try {
            Set<PayoutSubType> type = getPayoutSubType(form.getType());

            Supplier supplier = new Supplier(
                    form.getName(),
                    form.getContacts(),
                    form.getMobile(),
                    form.getTelephone(),
                    form.getAddress(),
                    form.getDistrictCode(),
                    Instant.now()
            );
            Supplier supplier1 = this.getRepository().save(supplier);
            supplier.getType().addAll(type);
            supplier1 = this.getRepository().save(supplier);
            System.out.println(supplier1.getType().toString());
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    public Set<PayoutSubType> getPayoutSubType(Set<Integer> type){
        Iterator<Integer> iterator = type.iterator();
        Set<PayoutSubType> setType = new HashSet<>();
        while(iterator.hasNext()){
            Integer id = iterator.next();
            PayoutSubType payoutSubType = this.payoutSubTypeRepository.findOne(id);
            setType.add(payoutSubType);
        }
        return setType;
    }

    public Supplier update(Integer id, SupplierForm form) {
        Supplier supplier = this.getRepository().findOne(Long.parseLong(id.toString()));

        if (!StringUtils.isEmpty(form.getName())){
            supplier.setName(form.getName());
        }

        if (!StringUtils.isEmpty(form.getContacts())){
            supplier.setContacts(form.getContacts());
        }

        if (!StringUtils.isEmpty(form.getMobile())){
            supplier.setMobile(form.getMobile());
        }

        if (!StringUtils.isEmpty(form.getTelephone())){
            supplier.setTelephone(form.getTelephone());
        }

        if (!StringUtils.isEmpty(form.getAddress())){
            supplier.setAddress(form.getAddress());
        }

        if (!StringUtils.isEmpty(form.getDistrictCode())){
            supplier.setDistrictCode(form.getDistrictCode());
        }

        if (!StringUtils.isEmpty(form.getName())){
            supplier.setName(form.getName());
        }

        if (form.getType().size() != 0){
            supplier.setType(getPayoutSubType(form.getType()));
        }

        return this.getRepository().save(supplier);
    }

    public Collection<Supplier> find(Actor actor, String name, String contacts, String mobile, String telephone) {

        // 查询条件
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setContacts(contacts);
        supplier.setMobile(mobile);
        supplier.setTelephone(telephone);

        // 创建匹配器，就是查询条件设置
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("contacts", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("mobile", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withMatcher("telephone", ExampleMatcher.GenericPropertyMatchers.startsWith());

        // 创建匹配器实例
        Example<Supplier> example = Example.of(supplier, exampleMatcher);

        return this.getRepository().findAll(example);
    }

    public void deleteBy(String ids) {
        String[] ida = ids.split(",");
        for (String id : ida){
            Supplier supplier = this.getRepository().findOne(Long.parseLong(id));
            this.getRepository().delete(supplier);
        }
    }
}
