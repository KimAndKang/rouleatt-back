package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.BizHour;
import com.kimandkang.rouleatt.domain.Restaurant;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.MAX_VALUE;
import static java.time.DayOfWeek.*;

public record RestaurantResponse(
        Long id,
        String name,
        double x,
        double y,
        String category,
        String address,
        String roadAddress,
        String isOpen,
        List<MenuResponse> menus,
        List<ReviewResponse> reviews,
        List<BizHourResponse> bizHours
) {
    private static final String OVERNIGHT_TIME = "24:00";
    private static final String EVERYDAY = "매일";
    private static final String BIZ_HOUR_NOT_OPEN = "영업 준비 중";
    private static final String BIZ_HOUR_OPEN = "영업 중";
    private static final String BIZ_HOUR_BREAK = "브레이크 타임";
    private static final String BIZ_HOUR_CLOSED = "영업 종료";
    private static final String BIZ_HOUR_UNKNOWN = "영업 시간 정보가 없어요";
    private static final Map<String, Integer> DAY_ORDER = Map.of(
            "월", 1, "화", 2, "수", 3, "목", 4, "금", 5, "토", 6, "일", 7, "매일", 8
    );

    public static RestaurantResponse from(Restaurant restaurant, LocalDateTime now) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCoordinate().getX(),
                restaurant.getCoordinate().getY(),
                restaurant.getCategory(),
                restaurant.getAddress(),
                restaurant.getRoadAddress(),
                isOpen(restaurant, now),
                restaurant.getMenus().stream().map(MenuResponse::from).toList(),
                restaurant.getReviews().stream().map(ReviewResponse::from).toList(),
                sortBizHours(restaurant.getBizHours())
        );
    }

    private static String isOpen(Restaurant restaurant, LocalDateTime now) {
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
        // 영업시간
        if (bizStart == null || bizEnd == null) {
            return BIZ_HOUR_UNKNOWN; // 영업 시간 정보가 없어요
        }

        boolean isBizOvernight = OVERNIGHT_TIME.equals(bizEnd);
        int startHour = Integer.parseInt(bizStart.substring(0, 2));
        int startMinute = Integer.parseInt(bizStart.substring(3, 5));
        int endHour = isBizOvernight ? 0 : Integer.parseInt(bizEnd.substring(0, 2));
        int endMinute = isBizOvernight? 0 : Integer.parseInt(bizEnd.substring(3, 5));

        LocalDateTime startDateTime = now.withHour(startHour).withMinute(startMinute);
        LocalDateTime endDateTime = now.withHour(endHour).withMinute(endMinute);

        // 새벽장사라면 day+1 적용
        if (isBizOvernight || endDateTime.isBefore(startDateTime)) {
            endDateTime = endDateTime.plusDays(1);
        }

        LocalDateTime breakStartDateTime = null;
        LocalDateTime breakEndDateTime = null;
        boolean isBreakOvernight = false;

        // 휴식시간
        if (breakStart != null && breakEnd != null) {

            isBreakOvernight = OVERNIGHT_TIME.equals(breakEnd);
            int breakStartHour = Integer.parseInt(breakStart.substring(0, 2));
            int breakStartMinute = Integer.parseInt(breakStart.substring(3, 5));
            int breakEndHour = isBreakOvernight ? 0 : Integer.parseInt(breakEnd.substring(0, 2));
            int breakEndMinute = isBreakOvernight ? 0 : Integer.parseInt(breakEnd.substring(3, 5));

            breakStartDateTime = now.withHour(breakStartHour).withMinute(breakStartMinute);
            breakEndDateTime = now.withHour(breakEndHour).withMinute(breakEndMinute);

            // 새벽장사라면 day+1 적용
            if (isBreakOvernight || breakEndDateTime.isBefore(breakStartDateTime)) {
                breakEndDateTime = breakEndDateTime.plusDays(1);
            }
        }

        if (now.isBefore(startDateTime)) {
            return BIZ_HOUR_NOT_OPEN; // 영업 준비 중
        }
        if (now.isAfter(endDateTime)) {
            return BIZ_HOUR_CLOSED; // 영업 종료
        }

        if (breakStartDateTime != null && breakEndDateTime != null) {
            if (!now.isBefore(breakStartDateTime) && !now.isAfter(breakEndDateTime)) {
                return BIZ_HOUR_BREAK; // 브레이크 타임
            }
        }

        return BIZ_HOUR_OPEN; // 영업 중
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

    private static List<BizHourResponse> sortBizHours(List<BizHour> bizHourList) {
        return bizHourList.stream()
                .map(BizHourResponse::from)
                .sorted(Comparator.comparingInt(b -> DAY_ORDER.getOrDefault(b.day(), MAX_VALUE)))
                .toList();
    }
}
