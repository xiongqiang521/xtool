package com.xq.xtool.cron;

import java.time.temporal.ChronoField;

/**
 * 年字段解析类
 *
 * @author 熊强
 * @version 1.0
 * 2023/9/1 18:39
 * @description 年字段解析类
 * @projectName spring-task
 */
public class MinuteCronField extends CronField {

    protected MinuteCronField(String cron) {
        super(0, 59, ChronoField.MINUTE_OF_HOUR, cron);
    }
}
