package com.xq.xtool.cron;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
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
public class WeekCronField extends CronField {

    protected WeekCronField(String cron) {
        super(0, 6, ChronoField.DAY_OF_WEEK, cron);
    }

    @Override
    protected List<Integer> readCron(String expression, List<Integer> allRange) {
        expression = weekStr(expression);
        return super.readCron(expression, allRange);
    }

    private String weekStr(String expression) {
        expression = expression.replace("?", "*");
        expression = expression.replace("7", "0");

        expression = expression.replace("MON", "1");
        expression = expression.replace("TUE", "2");
        expression = expression.replace("WED", "3");
        expression = expression.replace("THU", "4");
        expression = expression.replace("FRI", "5");
        expression = expression.replace("SAT", "6");
        expression = expression.replace("SUN", "0");

        expression = expression.replace("一", "1");
        expression = expression.replace("二", "2");
        expression = expression.replace("三", "3");
        expression = expression.replace("四", "4");
        expression = expression.replace("五", "5");
        expression = expression.replace("六", "6");
        expression = expression.replace("天", "0");
        expression = expression.replace("日", "0");
        expression = expression.replace("七", "0");

        return expression;
    }

    boolean current(LocalDateTime temporal) {
        int value = temporal.get(getChronoField());
        return getRange().stream().anyMatch(i -> i == value);
    }

    @Override
    public LocalDateTime next(LocalDateTime temporal) {
        int start = temporal.get(getChronoField());
        Integer value = getRange().stream().filter(i -> i > start).findFirst().orElse(getStart());
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.nextOrSame(DayOfWeek.of(value));
        return temporal.with(temporalAdjuster);
    }
}
