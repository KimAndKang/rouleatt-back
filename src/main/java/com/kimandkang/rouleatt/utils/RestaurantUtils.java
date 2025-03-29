package com.kimandkang.rouleatt.utils;

import java.util.List;

public class RestaurantUtils {

    private static final List<String> CHICKEN = List.of("치킨", "닭강정", "후라이드", "통닭");
    private static final List<String> FAST_FOOD = List.of("햄버거", "샌드위치", "라면", "밀키트", "푸드트럭", "도시락", "패밀리레스토랑", "편의점", "밥버거");
    private static final List<String> CAFE_DESSERT = List.of("카페", "커피", "와플", "빙수", "케이크", "초콜릿", "마카롱", "크레페", "디저트", "브런치", "떡카페", "스터디카페", "ABC커피", "베이커리");
    private static final List<String> CHINESE = List.of("중식", "중식당", "마라", "양꼬치", "딤섬");
    private static final List<String> STEW_SOUP = List.of("찜", "탕", "감자탕", "국밥", "곰탕", "매운탕", "추어탕", "갈비탕", "해장국", "아귀찜", "부대찌개", "뼈다귀탕", "신사골");
    private static final List<String> JOKBAL_BOSSAM = List.of("족발", "보쌈");
    private static final List<String> BUNSIK = List.of("분식", "김밥", "종합분식", "감성분식", "주먹밥", "떡", "찐빵", "튀김", "문빵");
    private static final List<String> SNACKS = List.of("호떡", "핫도그", "도넛", "호두과자", "베이글", "후렌치후라이", "과일");
    private static final List<String> JAPANESE = List.of("일식", "돈까스", "초밥", "우동", "라멘", "사케");
    private static final List<String> MEAT = List.of("돼지", "오리", "소고기", "육류", "곱창", "양갈비", "양푼이막창", "정육식당", "불닭", "갈비", "고기");
    private static final List<String> TTEOKBOKKI = List.of("짬떡", "떡볶이", "떡뽀끼", "떡볶이앤카페", "국물떡볶이", "신가네", "스푼떡볶이", "달떡", "앙마", "자매떡볶이", "달볶이", "인정국물", "신떡");
    private static final List<String> ASIAN = List.of("태국", "베트남", "아시아", "인도", "터키", "아프리카", "미스포");
    private static final List<String> YANGSIK = List.of("이탈리아", "스파게티", "파스타", "피자", "양식");
    private static final List<String> GLOBAL = List.of("프랑스", "독일", "스페인", "그리스", "아메리칸", "퓨전", "멕시코", "그로서란트", "레스토랑", "존슨");
    private static final List<String> SEAFOOD = List.of("해물", "오징어", "낙지", "주꾸미", "조개", "대게", "랍스타", "전복", "굴", "복어", "킹크랩", "바닷가재", "해산물", "생선회");
    private static final List<String> KOREAN = List.of("한식", "생선", "장어", "정식", "백반", "비빔밥", "쌈밥", "국수", "칼국수", "막국수", "죽", "두부요리", "보리밥", "찌개", "전", "이북음식", "향토", "기사식당", "사찰음식");
    private static final List<String> YASIK = List.of("야식");

    public static String normalize(String category) {
        if (category == null || category.isBlank()) return "기타";

        if (matches(category, CHICKEN)) return "치킨";
        if (matches(category, FAST_FOOD)) return "패스트푸드";
        if (matches(category, CAFE_DESSERT)) return "카페·디저트";
        if (matches(category, CHINESE)) return "중식";
        if (matches(category, STEW_SOUP)) return "찜·탕";
        if (matches(category, JOKBAL_BOSSAM)) return "족발·보쌈";
        if (matches(category, BUNSIK)) return "분식";
        if (matches(category, SNACKS)) return "간식";
        if (matches(category, JAPANESE)) return "일식";
        if (matches(category, MEAT)) return "고기";
        if (matches(category, TTEOKBOKKI)) return "떡볶이";
        if (matches(category, ASIAN)) return "아시안";
        if (matches(category, YANGSIK)) return "양식";
        if (matches(category, GLOBAL)) return "세계음식";
        if (matches(category, SEAFOOD)) return "해산물";
        if (matches(category, YASIK)) return "야식";
        if (matches(category, KOREAN)) return "한식";

        return "기타";
    }

    private static boolean matches(String category, List<String> keywords) {
        return keywords.stream().anyMatch(category::contains);
    }
}
