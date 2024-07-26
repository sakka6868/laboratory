package cn.sakka.wechat.transit.controller;

import cn.sakka.wechat.transit.entity.Person;
import cn.sakka.wechat.transit.entity.Report;
import cn.sakka.wechat.transit.service.PersonServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final PersonServiceImpl personServiceImpl;

    public HomeController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @PostMapping("/person")
    public Person createPerson() {
        return personServiceImpl.createPerson();
    }

    @PostMapping("/report")
    public Report createPerson(@RequestParam String id) {
        return personServiceImpl.openReport(id);
    }

}
