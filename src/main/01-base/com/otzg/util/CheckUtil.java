package com.otzg.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author G.
 * version 2018-09-28
 * 输入数据验证
 */
public final class CheckUtil {

    private final static Pattern DatePattern;
    private final static Pattern DatetimePattern;

    static {
        DatePattern = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
        DatetimePattern = Pattern.compile("((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
    }

    /**
     * 校验金额
     *
     * @param amount
     * @return
     */
    public static boolean isAmount(Double amount) {
        if (null == amount) {
            return true;
        }
        if (amount.compareTo(new Double(0)) < 1) {
            return true;
        }
        return false;
    }

    /**
     * 校验参数
     *
     * @param param
     * @param length
     * @return
     */
    public static boolean isCorrect(String param, int length) {
        if (isEmpty(param)) {
            return true;
        }
        if (param.length() > length) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @author G/2018-10-25 9:58
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        } else if (collection.size() > 0) {
            collection.removeAll(Collections.singleton(null));
            return collection == null || collection.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * 判断所有的参数中是否有null或空值
     *
     * @param args
     * @return
     */
    public final static boolean isEmpty(Object args) {
        if (args == null) {
            return true;
        } else if (args instanceof String) {
            return ((String) args).isEmpty();
        } else if (args instanceof JSONObject) {
            return ((JSONObject) args).isNullObject() || ((JSONObject) args).isEmpty();
        } else if (args instanceof JSONArray) {
            return ((JSONArray) args).isEmpty();
        } else if (args instanceof Collection<?>) {
            return isEmpty((Collection<?>) args);
        } else {
            return false;
        }
    }

    public final static boolean isNotStringArray(String[] args) {
        boolean flag = false;
        if (args != null || args.length > 0) {
            for (String a : args) {
                if (isEmpty(a)) {
                    flag = true;
                    break;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断输入是否是一个整形的字符串
     *
     * @param args
     * @return
     */
    public static boolean isInteger(String args) {
        try {
            new Integer(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是一个整数
     *
     * @param args
     * @return
     */
    public final static boolean isInteger(Integer args) {
        return args == null ? false : true;
    }

    /**
     * 判断是否是一个整形数组
     *
     * @param args
     * @return
     */
    public final static boolean isIntegerArray(Integer[] args) {
        return isNotIntegerArray(args) ? false : true;
    }

    /**
     * 判断是否是一个整形数组
     *
     * @param args
     * @return
     */
    public final static boolean isNotIntegerArray(Integer[] args) {
        if (args == null || args.length < 1) {
            return true;
        }

        for (Integer a : args) {
            if (!isInteger(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是一个整数
     *
     * @param args
     * @return
     */
    public final static boolean isLong(Long args) {
        return args == null ? false : true;
    }

    public final static boolean isLong(String args) {
        try {
            new Long(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final static boolean isLongArray(Long[] args) {
        return isNotLongArray(args) ? false : true;
    }

    /**
     * 判断是否是一个Long型数组
     *
     * @param args
     * @return
     */
    public final static boolean isNotLongArray(Long[] args) {
        if (args == null || args.length < 1) {
            return true;
        }
        return false;
    }

    public final static boolean isBigDecimal(String args) {
        try {
            new BigDecimal(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final static boolean isBigDecimal(BigDecimal args) {
        return args == null ? false : true;
    }

    public static boolean isBigDecimalArray(BigDecimal[] args) {
        if (args == null || args.length < 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public final static boolean isEmail(String email) {
        if (email == null || email.length() < 9) return false;
        email = email.toLowerCase();
        if (email.endsWith(".con")) return false;
        if (email.endsWith(".cm")) return false;
        if (email.endsWith("@gmial.com")) return false;
        if (email.endsWith("@gamil.com")) return false;
        if (email.endsWith("@gmai.com")) return false;
        Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是否是一个合法的手机号
     *
     * @param mobile
     * @return
     * @author G/2016-3-17 下午2:59:07
     */
    public final static boolean isMobile(String mobile) {
        if (mobile == null || mobile.length() < 10) return false;
        Pattern p = Pattern.compile("^(1[123456789][\\d]{9})$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断是否是一个名字包含中英文，英文中可以有空格，中文不允许有空格
     *
     * @param name
     * @return
     * @author G/2016-3-17 下午3:02:11
     */
    public final static boolean isUserName(String name) {
        if (isEmpty(name)) return false;
        Pattern p = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z\\d]{3,15}$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 中国身份证号码验证
     *
     * @param cardNo
     * @author G/2018/6/22 11:03
     */
    public final static boolean isIDCard(String cardNo) {
        if (isEmpty(cardNo)) return false;
        Pattern p = Pattern.compile("^\\d{15}$|^\\d{17}[0-9Xx]$");
        Matcher m = p.matcher(cardNo);
        return m.matches();
    }

    /**
     * 护照及港澳台通行证
     * 规则参照:
     * P:P开头的是因公普通护照
     * D:外交护照是D开头
     * E:有电子芯片的普通护照为“E”字开头，
     * S:后接8位阿拉伯数字公务护照
     * G:因私护照G开头
     * 14:
     * 15:
     * e.g.
     * G28233515
     * s28233515
     * 141234567
     *
     * @param number
     * @author G/2018/6/22 11:03
     */
    public final static boolean isPassport(String number) {
        if (isEmpty(number)) return false;
        Pattern p = Pattern.compile("^1[45][0-9]{7}|([P|p|S|s]\\d{7})|([S|s|G|g]\\d{8})|([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\\d{8})|([H|h|M|m]\\d{8,10})$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public final static boolean isNickName(String name) {
        if (isEmpty(name)) return false;
        Pattern p = Pattern.compile("(^[a-zA-Z]{1}[a-zA-Z\\d]{3,15}$)|(^[\\u4e00-\\u9fa5]{4,16})$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 是否是中文单词
     *
     * @param name
     * @author G/2018/3/19 12:05
     */
    public final static boolean isChineseWord(String name) {
        if (isEmpty(name)) return false;
        Pattern p = Pattern.compile("(^[\\u4e00-\\u9fa5]+)$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 密码规则
     *
     * @param password
     * @return
     * @author G/2015-4-17 上午10:26:05
     */
    public final static boolean isPassword(String password) {
        if (isEmpty(password)
                || password.length() < 6
                || password.length() > 16
        ) {
            return false;
        }
        return true;
    }

    /**
     * 判断输入的参数是否是一个合法的数字型字符串
     *
     * @param arg
     * @return
     */
    public static boolean isNumber(String arg) {
        if (isEmpty(arg)) {
            return false;
        }
        for (int i = 0; i < arg.length(); i++) {
            char ch = arg.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较两个字符串忽略大小写、
     * 比如图片验证码
     *
     * @param a
     * @param b
     * @return
     * @author G/2017年4月17日 上午8:34:30
     */
    public final static boolean isSameCode(String a, String b) {
        boolean f = false;
        if (!isEmpty(a)
                && !isEmpty(b)
                && a.equalsIgnoreCase(b)
        ) {
            f = true;
        }
        return f;
    }

    /******************************************************************************************************************
     * <P>验证是否［日期］格式（yyyy-MM-dd）</P>
     * @param arg
     * @return
     *****************************************************************************************************************/
    public static boolean isDate(String arg) {
        if (isEmpty(arg)) return false;
        return DatePattern.matcher(arg).matches();
    }

    public static boolean isDate(Date date) {
        if (date == null) return false;
        return isDate(DateUtil.date2Str(date));
    }

    /******************************************************************************************************************
     * <P>验证是否［日期＋时间］格式（yyyy-MM-dd hh:mm:ss）</P>
     * @param arg
     * @return
     *****************************************************************************************************************/
    public static boolean isDatetime(String arg) {
        if (isEmpty(arg)) return false;
        return DatetimePattern.matcher(arg).matches();
    }

    /**
     * 判断是否是图片
     *
     * @param contentType
     * @author G/2018/3/19 14:11
     */
    public static boolean isImage(String contentType) {
        if (contentType == null
                || contentType.isEmpty()
                || contentType.indexOf("image") < 0
        ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 微信账号判断
     * openid=28,unionid=29
     *
     * @param unionId
     * @param openId
     * @return
     */
    public final static boolean isWxAccount(String unionId, String openId) {
        if (isEmpty(unionId) && isEmpty(openId)) {
            return false;
        } else if (!isEmpty(unionId) && unionId.length() < 29) {
            return false;
        } else if (!isEmpty(openId) && openId.length() < 28) {
            return false;
        } else {
            return true;
        }
    }

    public final static boolean isOpenId(String openId) {
        if (isEmpty(openId)) {
            return false;
        } else if (openId.length() < 28) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String args[]) {
//        System.out.println("is mobile="+isOpenId("o8EmL5RwSol3smvBN28xhJs6IWZI"));
    }
}
