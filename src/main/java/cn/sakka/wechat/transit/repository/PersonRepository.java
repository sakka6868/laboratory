package cn.sakka.wechat.transit.repository;

import cn.sakka.wechat.transit.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,String> {
}
