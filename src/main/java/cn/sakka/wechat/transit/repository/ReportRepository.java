package cn.sakka.wechat.transit.repository;

import cn.sakka.wechat.transit.entity.Person;
import cn.sakka.wechat.transit.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,String> {
}
