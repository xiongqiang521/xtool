package com.xq.xtool.cron;

import java.time.LocalDateTime;

/**
 * @author 熊强
 * @version 1.0
 * 2023/9/1 23:44
 * @description
 * @projectName spring-task
 */
public class CronUtil {

    private final String cron;
    private final CronField[] fields = new CronField[6];
    private final WeekCronField weekCronField;

    private CronUtil(String cron) {
        if (cron == null || cron.isEmpty()) {
            throw new IllegalArgumentException("cron表达式不能为空");
        }

        String[] cronFields = cron.split(" ");
        if (cronFields.length > 7 || cronFields.length < 6) {
            throw new IllegalArgumentException("cron表达式格式错误");
        }
        this.cron = cron;

        fields[0] = new SecondCronField(cronFields[0]);
        fields[1] = new MinuteCronField(cronFields[1]);
        fields[2] = new HourCronField(cronFields[2]);
        fields[3] = new DayCronField(cronFields[3]);
        fields[4] = new MonthCronField(cronFields[4]);
        fields[5] = cronFields.length == 7 ? new YearCronField(cronFields[6]) : new YearCronField();
        // 单独处理周字段
        weekCronField = new WeekCronField(cronFields[5]);
    }

    /**
     * 获取下一个时间
     *
     * @param cron      cron表达式
     * @param temporal  当前时间
     * @return
     */
    public static LocalDateTime next(String cron, LocalDateTime temporal) {
        return parse(cron).next(temporal);
    }

    public static CronUtil parse(String cron) {
        return new CronUtil(cron);
    }

    /**
     * 获取下一个时间
     *
     * @param temporal
     * @return
     */
    public LocalDateTime next(LocalDateTime temporal) {
        // 清理纳秒值
        LocalDateTime time = LocalDateTime.from(temporal).withNano(0);

        boolean current = true;
        for (int i = 0; i < 6; i++) {
            CronField field = fields[i];
            // 当前字段是否满足规则
            boolean nowCurrent = field.current(time);
            if (current) {
                time = field.same(time);
            } else {
                time = field.next(time);

                // 从后往前清零, 将之前结果集值取最小值
                for (int r = 0; r < i; r++) {
                    time = fields[r].reset(time);
                }
            }
            current = nowCurrent;
        }
        // 年字段不满足规则，即没有时间符合cron，返回null
        if (!current) {
            return null;
        }

        // 处理周字段
        if (this.weekCronField.current(time)) {
            return time;
        } else {
            time = this.weekCronField.next(time);
            return this.next(time);
        }
    }

}
