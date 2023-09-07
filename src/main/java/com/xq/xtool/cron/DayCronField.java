package com.xq.xtool.cron;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * 年字段解析类
 *
 * @author 熊强
 * @version 1.0
 * 2023/9/1 18:39
 * @description 年字段解析类
 * @projectName spring-task
 */
public class DayCronField extends CronField {

    private boolean isLast;
    private int lastDay;

    protected DayCronField(String cron) {
        super(1, 31, ChronoField.DAY_OF_MONTH, cron);
    }

    @Override
    protected List<Integer> readCron(String expression, List<Integer> allRange) {
        if (!expression.startsWith("L")) {
            isLast = false;
            return super.readCron(expression, allRange);
        }
        isLast = true;
        String day = expression.replace("L", "");
        if (!day.isEmpty()) {
            lastDay = Math.abs(Integer.parseInt(day)) - 1;
        }
        return allRange;

    }


    protected LocalDateTime reset(LocalDateTime temporal) {
        return temporal.with(TemporalAdjusters.lastDayOfMonth()).minusDays(lastDay);
    }

    protected int extractFieldValue(LocalDateTime temporal) {
        if (!isLast) {
            return super.extractFieldValue(temporal);
        }
        return temporal.with(TemporalAdjusters.lastDayOfMonth()).minusDays(lastDay).getDayOfMonth();
    }
}
