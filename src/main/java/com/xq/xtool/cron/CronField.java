package com.xq.xtool.cron;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * cron表达式 单个属性
 *
 * @author 熊强
 * @version 1.0
 * 2023/9/1 17:38
 * @description cron表达式 单个属性
 * @projectName spring-task
 */
@Getter
public abstract class CronField {

    private final String cron;
    private final List<Integer> all;
    private final List<Integer> range;
    private final int min;
    private final int max;
    private final int start;
    private final int stop;

    private final ChronoField chronoField;


    protected CronField(int min, int max, ChronoField chronoField, String cron) {
        this.chronoField = chronoField;
        this.cron = cron.trim();
        this.min = min;
        this.max = max;
        this.all = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
        this.range = readCron(cron, all);
        this.start = range.get(0);
        this.stop = range.get(range.size() - 1);
    }

    /**
     * 是否 在当前范围内，包含边界
     * @param value
     * @return
     */
    boolean current(LocalDateTime temporal) {
        int value = this.extractFieldValue(temporal);
        return getRange().stream().anyMatch(i -> i >= value);
    }

    /**
     * 获取下一个值，环状结构，超过最大值返回最小值
     * @param start
     * @return
     */
    protected LocalDateTime next(LocalDateTime temporal) {
        return getRange().stream().filter(i -> i > this.extractFieldValue(temporal)).findFirst()
                .map(integer -> temporal.with(chronoField, integer))
                .filter(now -> now.isAfter(temporal))
                .orElseGet(() -> temporal.with(chronoField, start).plus(1, chronoField.getRangeUnit()));

    }

    protected LocalDateTime same(LocalDateTime temporal) {

        return getRange().stream().filter(i -> i >= this.extractFieldValue(temporal)).findFirst()
                .map(integer -> temporal.with(chronoField, integer))
                .filter(now -> now.isEqual(temporal) || now.isAfter(temporal))
                .orElseGet(() -> temporal.with(chronoField, start).plus(1, chronoField.getRangeUnit()));

    }

    protected LocalDateTime reset(LocalDateTime temporal) {
        return temporal.with(chronoField, start);
    }

    protected int extractFieldValue(LocalDateTime temporal) {
        return temporal.get(chronoField);
    }

    protected List<Integer> readCron(final String expression, List<Integer> allRange) {
        final List<Integer> range = new ArrayList<>(allRange);
        String expr = expression;
        if (expr == null || expr.isEmpty()) {
            return range;
        }

        expr = expr.trim();
        if ("*".equals(expr)) {
            return range;
        }

        // 解析 1,2,3 的表达式
        if (expr.contains(",")) {
            return Stream.of(expr.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .peek(i -> checkValue(i, range))
                    .boxed()
                    .collect(Collectors.toList());

        }

        // 提取cron中的间隔，即 / 后的数值
        int step;
        if (expr.contains("/")) {
            String[] split = expr.split("/");
            if (split.length != 2) {
                throw new IllegalArgumentException("cron expression contains an invalid value: " + expr);
            }
            step = Integer.parseInt(split[1]);
            expr = split[0];
        } else {
            return Collections.singletonList(Integer.parseInt(expr));
        }

        if (step < 1 || step >= max - min) {
            throw new IllegalArgumentException("cron expression contains an invalid value: " + step);
        }

        // 解析 */2 的表达式
        if ("*".equals(expr)) {
            return range.stream().filter(i -> i % step == 0).collect(Collectors.toList());
        }

        // 解析 1-10/2 的表达式
        if (expr.contains("-")) {
            String[] split = expr.split("-");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            checkValue(start, range);
            checkValue(end, range);

            return range.stream().filter(i -> i >= start)
                    .filter(i -> i <= end)
                    .filter(i -> (i - start) % step == 0)
                    .collect(Collectors.toList());
        }

        int start = Integer.parseInt(expr);
        checkValue(start, range);

        // 解析 1/2 的表达式
        return range.stream().filter(i -> i >= start)
                .filter(i -> (i - start) % step == 0)
                .collect(Collectors.toList());
    }

    public static void checkValue(int val, Collection<Integer> collection) {
        if (!collection.contains(val)) {
            throw new IllegalArgumentException("cron expression contains an invalid value: " + val);
        }
    }

}
