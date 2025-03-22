package com.kimandkang.rouleatt.dto;

import com.kimandkang.rouleatt.domain.BizHour;

public record BizHourResponse(
        Long id,
        String day,
        String bizStart,
        String breakStart,
        String breakEnd,
        String lastOrder,
        String bizEnd
) {
    public static BizHourResponse from(BizHour bizHour) {
        return new BizHourResponse(
                bizHour.getId(),
                bizHour.getDay(),
                bizHour.getBizStart(),
                bizHour.getBreakStart(),
                bizHour.getBreakEnd(),
                bizHour.getLastOrder(),
                bizHour.getBizEnd()
        );
    }
}
