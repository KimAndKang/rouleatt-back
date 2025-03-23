package com.kimandkang.rouleatt.utils;

import com.kimandkang.rouleatt.domain.Menu;
import com.kimandkang.rouleatt.domain.Restaurant;

public class MenuUtils {

    private static final String NO_MENU_RECOMMENDED = "가격 정보 없음";

    public static String avgPrice(Restaurant restaurant) {
        return restaurant.getMenus().stream()
                .filter(Menu::isRecommended)
                .findFirst()
                .map(Menu::getPrice)
                .orElseGet(() -> restaurant.getMenus().stream()
                        .findFirst()
                        .map(Menu::getPrice)
                        .orElse(NO_MENU_RECOMMENDED));
    }
}
