package cn.sakka.wechat.transit.service;

import cn.sakka.wechat.transit.entity.Person;
import cn.sakka.wechat.transit.entity.Report;
import cn.sakka.wechat.transit.repository.PersonRepository;
import cn.sakka.wechat.transit.repository.ReportRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl {

    @Resource
    protected PersonRepository personRepository;
    @Resource
    protected ReportRepository reportRepository;


    @Transactional(rollbackFor = Exception.class)
    public Person createPerson() {
        Person entity = new Person();
        entity.setName("LI");
        return personRepository.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Report openReport(String id) {
        Person entity = personRepository.getReferenceById(id);
        Report report = new Report();
        report.setPersonId(entity.getId());
        return reportRepository.save(report);
    }


}
