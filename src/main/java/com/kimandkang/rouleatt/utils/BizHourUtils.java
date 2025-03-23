package com.kimandkang.rouleatt.utils;

import static java.lang.Integer.MAX_VALUE;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import com.kimandkang.rouleatt.domain.BizHour;
import com.kimandkang.rouleatt.domain.Restaurant;
import com.kimandkang.rouleatt.dto.BizHourResponse;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BizHourUtils {

    private static final String OVERNIGHT_TIME = "24:00";
    private static final String EVERYDAY = "매일";
    private static final String BIZ_HOUR_NOT_OPEN = "영업 준비 중";
    private static final String BIZ_HOUR_OPEN = "영업 중";
    private static final String BIZ_HOUR_BREAK = "브레이크 타임";
    private static final String BIZ_HOUR_CLOSED = "영업 종료";
    private static final String BIZ_HOUR_UNKNOWN = "영업 시간 정보가 없어요";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final Map<String, Integer> DAY_ORDER = Map.of(
            "월", 1, "화", 2, "수", 3, "목", 4, "금", 5, "토", 6, "일", 7, "매일", 8
    );

    public static String isOpen(Restaurant restaurant, LocalDateTime now) {
        List<BizHour> bizHours = restaurant.getBizHours();
        if (bizHours == null || bizHours.isEmpty()) {
            return BIZ_HOUR_UNKNOWN; // 영업 시간 정보가 없어요
        }

        // 매일 케이스
        if (bizHours.size() == 1 && EVERYDAY.equals(bizHours.get(0).getDay())) {
            BizHour bizHour = bizHours.get(0);
            return calculateOvernight(
                    now,
                    bizHour.getBizStart(),
                    bizHour.getBreakStart(),
                    bizHour.getBreakEnd(),
                    bizHour.getBizEnd()
            );
        }

        // 요일별 케이스
        DayOfWeek currentDay = now.getDayOfWeek();
        for (BizHour bizHour : bizHours) {
            if (isSameDay(currentDay, bizHour.getDay())) {
                return calculateOvernight(
                        now,
                        bizHour.getBizStart(),
                        bizHour.getBreakStart(),
                        bizHour.getBreakEnd(),
                        bizHour.getBizEnd()
                );
            }
        }

        return BIZ_HOUR_UNKNOWN; // 영업 시간 정보가 없어요
    }

    private static String calculateOvernight(
            LocalDateTime now,
            String bizStart,
            String breakStart,
            String breakEnd,
            String bizEnd
    ) {
        if (bizStart == null || bizEnd == null) {
            return BIZ_HOUR_UNKNOWN;
        }

        int bizStartHour = Integer.parseInt(bizStart.substring(0, 2));
        int bizStartMinute = Integer.parseInt(bizStart.substring(3, 5));

        boolean isEndOvernight = OVERNIGHT_TIME.equals(bizEnd);
        int bizEndHour = isEndOvernight ? 0 : Integer.parseInt(bizEnd.substring(0, 2));
        int bizEndMinute = isEndOvernight ? 0 : Integer.parseInt(bizEnd.substring(3, 5));

        // 현재일을 기준으로 영업시작시간과 영업종료시간을 만들고 순서비교
        LocalDateTime bizStartTime = now.withHour(bizStartHour).withMinute(bizStartMinute);
        LocalDateTime bizEndTime = now.withHour(bizEndHour).withMinute(bizEndMinute);

        // 영업종료가 24:00 이거나 영업종료시간 -> 영업시작시간 이라면 새벽장사로 간주
        if (isEndOvernight || bizEndTime.isBefore(bizStartTime)) {
            bizEndTime = bizEndTime.plusDays(1);
        }

        // 영업 전
        if (now.isBefore(bizStartTime)) {
            return BIZ_HOUR_NOT_OPEN;
        }

        // 영업 종료
        if (now.isAfter(bizEndTime)) {
            return BIZ_HOUR_CLOSED;
        }

        // 브레이크 타임
        if (breakStart != null && breakEnd != null) {
            String currentTime = now.toLocalTime().format(TIME_FORMATTER);
            if (currentTime.compareTo(breakStart) >= 0 && currentTime.compareTo(breakEnd) <= 0) {
                return BIZ_HOUR_BREAK;
            }
        }

        // 영업 중
        return BIZ_HOUR_OPEN;
    }

    private static boolean isSameDay(DayOfWeek nowDay, String bizDay) {
        DayOfWeek day = parseDay(bizDay);
        return day != null && nowDay == day;
    }

    private static DayOfWeek parseDay(String day) {
        return switch (day) {
            case "월" -> MONDAY;
            case "화" -> TUESDAY;
            case "수" -> WEDNESDAY;
            case "목" -> THURSDAY;
            case "금" -> FRIDAY;
            case "토" -> SATURDAY;
            case "일" -> SUNDAY;
            default -> null;
        };
    }

    public static List<BizHourResponse> sortBizHours(List<BizHour> bizHourList) {
        return bizHourList.stream()
                .map(BizHourResponse::from)
                .sorted(Comparator.comparingInt(b -> DAY_ORDER.getOrDefault(b.day(), MAX_VALUE)))
                .toList();
    }
}
