package cn.sakka.wechat.transit.entity;

import cn.hutool.core.util.IdUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity
@Data
public class Person {
    @Id
    protected String id = IdUtil.nanoId();

    protected String name;

}
