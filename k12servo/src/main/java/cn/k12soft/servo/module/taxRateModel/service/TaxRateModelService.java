package cn.k12soft.servo.module.taxRateModel.service;

import cn.k12soft.servo.domain.Actor;
import cn.k12soft.servo.module.taxRateModel.domain.TaxRateModel;
import cn.k12soft.servo.module.taxRateModel.domain.TaxRateMoney;
import cn.k12soft.servo.module.taxRateModel.domain.form.TaxRateModelForm;
import cn.k12soft.servo.module.taxRateModel.repository.TaxRateModelRespository;
import cn.k12soft.servo.service.AbstractRepositoryService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class TaxRateModelService extends AbstractRepositoryService<TaxRateModel, Long, TaxRateModelRespository>{
    protected TaxRateModelService(TaxRateModelRespository repository) {
        super(repository);
    }

    public TaxRateModel created(Actor actor, TaxRateModelForm form) {
        TaxRateModel taxRateModel = new TaxRateModel(
                form.getTaxIncome(),
                form.getEndowmentInsurance(),
                form.getUnemploymentInsurance(),
                form.getMedicalInsurance(),
                form.getMaternityInsurance(),
                form.getInjuryInsurance(),
                form.getAccumulationFund(),
                form.getTax(),
                form.getTaxRateModelMoneys(),
                Instant.now()
        );
        return getRepository().save(taxRateModel);
    }

    public TaxRateModel update(Actor actor, Integer id, TaxRateModelForm form) {
        TaxRateModel taxRateModel = getRepository().findOne(Long.parseLong(id.toString()));
        Collection<TaxRateMoney> taxRateMoneyList = taxRateModel.getTaxRateModelMoneys();
        if (form.getTaxIncome() != null){
            taxRateModel.setTaxIncome(form.getTaxIncome());
        }
        if (form.getEndowmentInsurance() != null){
            taxRateModel.setEndowmentInsurance(form.getEndowmentInsurance());
        }
        if (form.getUnemploymentInsurance() != null){
            taxRateModel.setUnemploymentInsurance(form.getUnemploymentInsurance());
        }
        if (form.getMedicalInsurance() != null){
            taxRateModel.setMedicalInsurance(form.getMedicalInsurance());
        }
        if (form.getMaternityInsurance() != null){
            taxRateModel.setMaternityInsurance(form.getMaternityInsurance());
        }
        if (form.getInjuryInsurance() != null){
            taxRateModel.setInjuryInsurance(form.getInjuryInsurance());
        }
        if (form.getAccumulationFund() != null){
            taxRateModel.setAccumulationFund(form.getAccumulationFund());
        }
        if (form.getTax() != null){
            taxRateModel.setTax(form.getTax());
        }
        if (form.getTaxRateModelMoneys() != null){
            Collection<TaxRateMoney> taxRMoney = new ArrayList<>();
            for(TaxRateMoney tax : form.getTaxRateModelMoneys()){
                Long taxid = tax.getId();
                for (TaxRateMoney taxR : taxRateModel.getTaxRateModelMoneys()){
                    Long taxRid = taxR.getId();
                    if (taxid.equals(taxRid)){
                        taxR.setPassManey(tax.getPassManey());
                        taxR.setToManey(tax.getToManey());
                        taxR.setTaxRate(tax.getTaxRate());
                        taxR.setTheNo(tax.getTheNo());
                        taxRMoney.add(taxR);
                    }
                }
            }
            taxRateModel.setTaxRateModelMoneys(taxRMoney);
            taxRateModel.setTaxRateModelMoneys(form.getTaxRateModelMoneys());
        }



        return taxRateModel;
    }

    public Collection<TaxRateModel> findAll() {
        return getRepository().findAll(new Sort(Sort.Direction.DESC, "created_at"));
    }

    public void deleteBy(Integer id) {
        getRepository().delete(get(Long.parseLong(id.toString())));
    }
}
