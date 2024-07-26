package cn.sakka.wechat.transit.entity;

import cn.hutool.core.util.IdUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity
@Data
public class Report {
    @Id
    protected String id = IdUtil.nanoId();

    @Column(unique = true)
    protected String personId;

}
