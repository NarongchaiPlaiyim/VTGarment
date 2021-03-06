package com.vtgarment.utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public enum Utils {
    ;
    public final static boolean TRUE = true;

    public static List getEmptyList(){
        return Collections.EMPTY_LIST;
    }

    public static boolean isNull(final Object object){
        return object == null;
    }

    public static boolean isEmpty(final String string){
        return StringUtils.isEmpty(string);
    }

    public static boolean isZero(final String string){
        return 0 == string.length();
    }

    public static boolean equals(final String string, final String string2){
        return StringUtils.equals(string, string2);
    }

    public static<T> List<T> safetyList(final List<T> list) {
        return !isNull(list) && list.size() > 0 ? list : (List<T>) Collections.EMPTY_LIST;
    }

    public static<T> boolean isSafetyList(final List<T> list){
        return !isNull(list) && !isZero(list.size());
    }

    public static<T> boolean isCollection(final Collection collection){
        return !isNull(collection) && !isZero(collection.size());
    }

    public static boolean isZero(int id){
        try {
            return id == 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isZero(BigDecimal id){
        try {
            return id == BigDecimal.ZERO;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static BigDecimal divide(BigDecimal value, BigDecimal divisor) {
        if (value == null || divisor == null)
            return null;

        if (BigDecimal.ZERO.compareTo(divisor) == 0) {
            return BigDecimal.ZERO;
        }

        try {
            return value.divide(divisor, 4, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal divide(BigDecimal value, BigDecimal divisor, int round) {
        if (value == null || divisor == null)
            return null;

        if (BigDecimal.ZERO.compareTo(divisor) == 0) {
            return BigDecimal.ZERO;
        }

        try {
            return value.divide(divisor, round, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal divide(BigDecimal value, int divisor) {
        if (value == null)
            return null;

        if (divisor == 0) {
            return BigDecimal.ZERO;
        }
        try {
            return value.divide(BigDecimal.valueOf(divisor), 4, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private static Calendar getCalendar(){
        return Calendar.getInstance();
    }

    public static Date currentDate(){
       return getCalendar().getTime();
    }

    public static Date previousDate(){
        Calendar c = Calendar.getInstance();
        Date date = currentDate();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    public static boolean isTrue(int value) {
        return value == 1;
    }

    public static int isTrue(boolean value) {
        return value == true ? 1 : 0 ;
    }

    public static int parseInt(Object input, int defaultValue){
        if(input == null)
            return defaultValue;
        else if (input instanceof Integer)
            return (Integer) input;
        else {
            String inputStr = input.toString();
            if(isEmpty(inputStr))
                return defaultValue;
            try{
                return Integer.parseInt(inputStr);
            } catch (ClassCastException e){
                return defaultValue;
            } catch (NumberFormatException e){
                return NumberUtils.createInteger(NumberUtils.createDouble(inputStr).intValue() + "");
            }
        }
    }

    public static int parseInt(Object input){
        return parseInt(input, 0);
    }

    public static int multiply(int value, int multiplier){
        if (value == 0 || multiplier == 0){
            return 0;
        }

        try{
            return value * multiplier;
        } catch (Exception e){
            return 0;
        }
    }

    public static BigDecimal multiply(BigDecimal value, int multiplier){
        if(value == null)
            return null;

        try {
            return value.multiply(BigDecimal.valueOf(multiplier));
        } catch (Exception e){
            return null;
        }
    }

    public static BigDecimal multiply(BigDecimal value, BigDecimal multiplier){
        if(value == null || multiplier == null)
            return null;

        try {
            return value.multiply(multiplier);
        } catch (Exception e){
            return null;
        }
    }

    public static BigDecimal parseBigDecimal(Object input, BigDecimal defaultValue){
        if(input == null)
            return defaultValue;
        else if (input instanceof BigDecimal)
            return (BigDecimal) input;
        else {
            String inputStr = input.toString();
            if(isEmpty(inputStr))
                return defaultValue;
            try{
                return BigDecimal.valueOf(parseLong(inputStr, 0));
            }catch (ClassCastException e){
                return defaultValue;
            }
        }
    }

    public static BigDecimal parseBigDecimal(Object input){
        return parseBigDecimal(input, BigDecimal.ZERO);
    }

    public static long parseLong(Object input,long defaultValue) {
        if (input == null)
            return defaultValue;
        else if (input instanceof Long)
            return (Long) input;
        else {
            String inputStr = input.toString();
            if (isEmpty(inputStr))
                return defaultValue;
            try {
                return Long.parseLong(inputStr);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }

    public static String parseString(Object input, String defaultValue){
        if(input == null)
            return defaultValue;
        else if (input instanceof String)
            return (String) input;
        else {
            try{
                if(isEmpty(input.toString())){
                    return defaultValue;
                } else {
                    return input.toString();
                }
            } catch (ClassCastException e){
                return defaultValue;
            }
        }
    }

    public static String parseString(Object input){
        return parseString(input, "");
    }

    public static Date parseDate(Object input, Date defaultValue){
        if(input == null)
            return defaultValue;
        else if (input instanceof Date)
            return (Date) input;
        else {
            try{
                if(isNull(input)){
                    return defaultValue;
                } else {
                    return null;
                }
            } catch (ClassCastException e){
                return defaultValue;
            }
        }
    }

    public static boolean parseBoolean(Object input, boolean defaultValue){
        if(input == null)
            return defaultValue;
        else if (input instanceof Boolean)
            return (Boolean) input;
        else {
            try{
                if(isNull(input)){
                    return defaultValue;
                } else {
                    return false;
                }
            } catch (ClassCastException e){
                return defaultValue;
            }
        }
    }

    public static String convertCurrentDateToStringDDMMYYYY(Date date){
        return convertToStringDDMMYYYY(date);
    }

    public static String convertToStringDDMMYYYY(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static String convertCurrentDateToStringDDMMYYYYHHmmss(){
        return convertToStringDDMMYYYYHHmmss(currentDate());
    }

    public static String convertToStringDDMMYYYYHHmmss(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static String genDateReportStringDDMMYYYY(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static String convertToStringYYYYMMDDHHmmss(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static String convertToStringYYYYMMDDHHmm(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static Date convertStringToDate(String stringDate) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = null;

        if (!Utils.isNull(stringDate.trim()) && !Utils.isZero(stringDate.length())){
            date = format.parse(stringDate);
        }

        return date;
    }

    public static String EmptyString(String field) {
        return isNull(field) ? "" : field;
    }

    public static String EmptyInteget(int field){
        return isZero(field) ? "0" : new StringBuffer().append(field).toString();
    }

    public static Date minDateTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static Date maxDateTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static Date minDateTime(Date date){
        Calendar calendar = Calendar.getInstance();
        String strDate = convertDateToStringDDMYYYY(date);
        String[] sp = strDate.split("/");
        calendar.set(Calendar.DATE, Integer.parseInt(sp[0]));
        calendar.set(Calendar.MONTH,Integer.parseInt(sp[1])-1);
        calendar.set(Calendar.YEAR,Integer.parseInt(sp[2]));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
//        System.out.println("form "+calendar.getTime());
        return calendar.getTime();
    }

    public static Date maxDateTime(Date date){
        Calendar calendar = Calendar.getInstance();
        String strDate = convertDateToStringDDMYYYY(date);
        String[] sp = strDate.split("/");
        calendar.set(Calendar.DATE, Integer.parseInt(sp[0]));
        calendar.set(Calendar.MONTH,Integer.parseInt(sp[1])-1);
        calendar.set(Calendar.YEAR,Integer.parseInt(sp[2]));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);
//        System.out.println("to "+calendar.getTime());
        return calendar.getTime();
    }

    public static String convertDateToStringDDMYYYY(Date date){
//        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
        if (Utils.isNull(date)){
            return "";
        } else {
            String dateString = simpleDateFormat.format(date);
            return dateString;
        }
    }

    public static boolean compareMoreBigDecimal(BigDecimal value1, BigDecimal value2){
        if (value1.compareTo(value2) >= 0){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    public static boolean compareLessBigDecimal(BigDecimal value1, BigDecimal value2){
        if (value1.compareTo(value2) <= 0){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    public static boolean compareInt(int value1, int value2){
        if (value2 <= value1){
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        String strDate = convertDateToStringDDMYYYY(new Date());
        String[] sp = strDate.split("/");
        calendar.set(Calendar.DATE, Integer.parseInt(sp[0]));
        calendar.set(Calendar.MONTH,Integer.parseInt(sp[1])-1);
        calendar.set(Calendar.YEAR,Integer.parseInt(sp[2]));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);
        System.out.println(calendar.getTime());

        BigDecimal a = new BigDecimal(0.00); //today
        BigDecimal b = new BigDecimal(16); //testerday
        System.out.println(a.compareTo(b));

        MathContext mc = new MathContext(2);
        System.out.println(a.subtract(b, mc));
    }
}
