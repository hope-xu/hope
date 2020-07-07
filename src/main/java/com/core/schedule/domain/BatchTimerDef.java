package com.core.schedule.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@NameStyle(value = Style.camelhumpAndLowercase)
@Table(name = "batch_timer_def")
@Alias("batchTimerDef")
@Getter
@Setter
public class BatchTimerDef implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String timerId;
    @Column
    private String timerName;
    @Column
    private String cron;
    @Column
    private String systemId;
    @Column
    private String className;
    @Column
    private String method;
    @Column
    private String status;
    @Column
    private Long expireTime;
    @Column
    private Date createTime;

}