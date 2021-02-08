/**
 *
 */
package com.zhj.test.util;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.expression.Lists;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 判断是否为科学计数字符串
     *
     * @param s
     * @return
     */
    public static boolean isScientific(String number) {
        if (number == null || "".equals(number.trim())) {
            return false;
        }
        String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";// 科学计数法正则表达式
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(number.trim()).matches();
    }


    /**
     * 判断是否为数字(可以包含小数点)
     *
     * @param s
     * @return
     */
    public static boolean isNumberExt(String number) {
        if (number == null || "".equals(number.trim())) {
            return false;
        }
        int index = number.indexOf(".");
        if (index < 0) {
            return StringUtils.isNumeric(number);
        } else {
            String num1 = number.substring(0, index);
            String num2 = number.substring(index + 1);
            return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
        }
    }

    /**
     * 判断是否为数字
     *
     * @param s
     * @return
     */
    public static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str
     *            验证字符串
     * @param strs
     *            字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
    public static String getMessage(String code, Object[] args) {
        LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Locale localLocale = localLocaleResolver.resolveLocale(request);
        return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
     *         toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
     *         toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
     *         toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString
     *            对象串 例如：row.user.id
     *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i = 0; i < vals.length; i++) {
            val.append("." + vals[i]);
            result.append("!" + (val.substring(1)) + "?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    /**
     * 例如 aa,bb,cc-->'aa','bb','cc' 'aa','bb','cc'-->'aa','bb','cc' 'aa' -- > 'aa'
     *
     * @param s
     * @return
     */
    public static String formatForSQL(String s) {
        if (s.indexOf("'") != -1) {
            return s;
        }
        if (s.indexOf(",") != -1) {
            String[] tmp = s.split(",");
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < tmp.length; i++) {
                str.append("'").append(tmp[i].trim()).append("',");
            }
            return str.toString().substring(0, str.lastIndexOf(","));
        } else {
            return "'" + s + "'";
        }
    }

    public static String numberPrifxPadWithZero(String str) {
        if (str == null) {
            return null;
        }
        String s = str.replaceAll("[^a-zA-Z]", "");
        String n = str.replaceAll(s, "");
        String tmp = "00000000" + n;
        tmp = tmp.substring(tmp.length() - 8, tmp.length());
        tmp = s + tmp;
        return tmp;
    }

    public static String genStr(int gen) {
        String genStr = null;
        if (gen < 10) {
            genStr = "0" + gen;
        } else {
            genStr = gen + "";
        }
        return genStr;
    }


    public static void main(String[] args) {

        String aa = numberPrifxPadWithZero("EW100_2"); /**/
        System.out.println(aa);
        String a = "aa,bb,cc";
        System.out.println(a + "-->" + formatForSQL(a));
        System.out.println(formatForSQL(a) + "-->" + formatForSQL(formatForSQL(a)));
        int aqqa = Integer.parseInt("008");
        System.out.println(aqqa);
    }

}
