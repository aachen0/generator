package com.ahjrlc.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 */
public class CommonUtil {
    final static char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    final static char[] numberChars = "0123456789".toCharArray();
    final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG (jpg)
        FILE_TYPE_MAP.put("png", "89504E47");  //PNG (png)
        FILE_TYPE_MAP.put("gif", "47494638");  //GIF (gif)
        FILE_TYPE_MAP.put("tif", "49492A00");  //TIFF (tif)
        FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap (bmp)
        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD (dwg)
        FILE_TYPE_MAP.put("html", "68746D6C3E");  //HTML (html)
        FILE_TYPE_MAP.put("rtf", "7B5C727466");  //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");
        FILE_TYPE_MAP.put("zip", "504B0304");
        FILE_TYPE_MAP.put("rar", "52617221");
        FILE_TYPE_MAP.put("psd", "38425053");  //Photoshop (psd)
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");  //Email [thorough only] (eml)
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");  //Outlook Express (dbx)
        FILE_TYPE_MAP.put("pst", "2142444E");  //Outlook (pst)
        FILE_TYPE_MAP.put("xls", "D0CF11E0");  //MS Word
        FILE_TYPE_MAP.put("doc", "D0CF11E0");  //MS Excel 注意：word 和 excel的文件头一样
        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");  //MS Access (mdb)
        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("pdf", "255044462D312E");  //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F");  //Quicken (qdf)
        FILE_TYPE_MAP.put("pwl", "E3828596");  //Windows Password (pwl)
        FILE_TYPE_MAP.put("wav", "57415645");  //Wave (wav)
        FILE_TYPE_MAP.put("avi", "41564920");
        FILE_TYPE_MAP.put("ram", "2E7261FD");  //Real Audio (ram)
        FILE_TYPE_MAP.put("rm", "2E524D46");  //Real Media (rm)
        FILE_TYPE_MAP.put("mpg", "000001BA");  //
        FILE_TYPE_MAP.put("mov", "6D6F6F76");  //Quicktime (mov)
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media (asf)
        FILE_TYPE_MAP.put("mid", "4D546864");  //MIDI (mid)
    }

    public final static String getImageFileType(File f) {
        if (isImage(f)) {
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(f);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (!iter.hasNext()) {
                    return null;
                }
                ImageReader reader = iter.next();
                iis.close();
                return reader.getFormatName();
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public final static String getFileType(byte[] b) {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Iterator<Map.Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
        while (entryiterator.hasNext()) {
            Map.Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static final boolean isImage(File file) {
        boolean flag = false;
        try {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if (width == 0 || height == 0) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (IOException e) {
            flag = false;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public final static String getFileHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        int length = Math.max(b.length, 28);
        for (int i = 0; i < length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 对一个字符串序列对中间部分指定长度用指定字符替换，生产打码字符串
     *
     * @param oriString    原始字符串序列
     * @param mosaic       指定马赛克字符
     * @param mosaicLength 指定马赛克长度
     * @return 打码后打字符串
     */
    public static String mosaic(CharSequence oriString, char mosaic, int mosaicLength) {
        if (oriString != null) {
            int totalLength = oriString.length();
            mosaicLength = Math.min(mosaicLength, totalLength);
            int startIndex = Math.round((totalLength - mosaicLength) / 2.0f);
            int endIndex = startIndex + mosaicLength;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < totalLength; i++) {
                if (i >= startIndex && i < endIndex) {
                    sb.append(mosaic);
                } else {
                    sb.append(oriString.charAt(i));
                }
            }
            return new String(sb);
        } else {
            return null;
        }

    }

    public static String inString(List powerIds) {
        if (powerIds != null && powerIds.size() > 0) {
            String s = powerIds.toString();
            if (powerIds.get(0) instanceof Number) {
                return s.replace('[', '(').replace(']', ')');
            } else {
                return s.replace("[", "('").replace("]", "')").replace(",", "','").replace(" ", "");
            }
        }
        return null;
    }

    /**
     * 将下划线分割的命名转换为java的驼峰命名
     *
     * @param _name_ 下划线名称
     * @param isCap  首字母是否大写
     * @return 驼峰命名的实体类名
     */
    public static String camel(String _name_, boolean isCap) {
        if (_name_ != null && !"".equals(_name_.trim())) {
            int size = _name_.length();
            char[] chars = _name_.toCharArray();
            String firstChar = chars[0] + "";
            if (isCap) {
                firstChar = firstChar.toUpperCase();
            }
            StringBuffer entityName = new StringBuffer(firstChar);
            for (int i = 1; i < size; i++) {
                if (chars[i] != '_') {
                    entityName.append(chars[i]);
                } else {
                    i++;
                    if (i < size) {
                        entityName.append((chars[i] + "").toUpperCase());
                    }
                }
            }
            return new String(entityName);
        }
        return null;
    }

    /**
     * 比较某个对象的每个属性，并给出结果，如果有一个对象是null则返回null，否则返回比较结果
     *
     * @param src
     * @param target
     * @return
     */
    public static List<Change> compare(Object src, Object target) {
        if (src != null && target != null) {
            List<Change> changes = new ArrayList<>();
            Class srcClaz = src.getClass();
            if (!srcClaz.getName().equals(target.getClass().getName())) {
                throw new RuntimeException("两个实例类型不同！");
            }
            Class superclass = srcClaz.getSuperclass();
            Field[] fields = srcClaz.getDeclaredFields();
            Field[] superFields = superclass.getDeclaredFields();
            List<Field> allFields = new ArrayList<>();
            Collections.addAll(allFields, fields);
            Collections.addAll(allFields, superFields);
            for (Field field : allFields) {
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                String filedTitle = "";
                if (annotation != null) {
                    filedTitle = annotation.value();
                }
                String fieldName = field.getName();
                String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method method;
                    try {
                        method = srcClaz.getMethod(getterName);
                    } catch (NoSuchMethodException e) {
                        continue;
                    }
                    Object srcObject = method.invoke(src);
                    String oriValue = srcObject == null ? "null" : toStringPlus(srcObject);
                    Object targetObject = method.invoke(target);
                    String newValue = targetObject == null ? "null" : toStringPlus(targetObject);
                    if (!oriValue.equals(newValue)) {
                        Change change = new Change();
                        change.setFieldName(filedTitle);
                        change.setOriginValue(oriValue);
                        change.setNewValue(newValue);
                        changes.add(change);
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return changes;
        }
        return null;
    }

    @Data
    public static class Change {
        private String fieldName;
        private String originValue;
        private String newValue;
    }

    private static String toStringPlus(Object o) {
        if (o instanceof Date) {
            Date oriDate = (Date) o;
            return new SimpleDateFormat(CommonConst.DATE_PATTERN).format(oriDate);
        }
        if (o instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) o;
            return decimal.stripTrailingZeros().toString();
        }
        return o.toString();
    }


    public static String generatorQueryCode() {
        String code = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        code = code + generatorRandomCode(chars, 4);
        return code;
    }

    public static String verifyCode() {
        return generatorRandomCode(numberChars, 6);
    }

    private static String generatorRandomCode(char[] chars, int size) {
        if (size > 0) {
            int length = chars.length;
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < size; i++) {
                code.append(chars[new Random().nextInt(length)]);
            }
            return new String(code);
        } else {
            return "";
        }
    }

    public static com.ahjrlc.common.ResponseData getResponseData(String result) {
        String[] split = result.split(":");
        if (split.length == 2) {
            if ("1".equals(split[1])) {
                return new com.ahjrlc.common.ResponseData().setMsg(split[0]).setCode(1);
            } else {
                return new com.ahjrlc.common.ResponseData().setMsg(split[0]).setCode(0);
            }
        } else {
            return new ResponseData().setMsg(result).setCode(501);
        }
    }

    public static boolean luhn(String number) {
        if (number == null || number.trim().length() == 0) {
            return false;
        }
        if (number.length() < 16 || number.length() > 19) {
            return false;
        }

        //根据Luhm法则得到校验位
        char bit = getBankCardCheckCode(number.substring(0, number.length() - 1));
        if (bit == 'N') {
            return false;
        }

        //和银行卡号的校验位(最后一位)比较,相同为true 不同为false
        return number.charAt(number.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 该校验的过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字(0、2、4等等)，先乘以2（如果乘积为两位数，则将其减去9或个位与十位相加的和），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */
    private static char getBankCardCheckCode(String nonCheckCodenumber) {
        //如果传的不是数字返回N
        if (nonCheckCodenumber == null || nonCheckCodenumber.trim().length() == 0
                || !nonCheckCodenumber.matches("\\d+")) {
            return 'N';
        }
        char[] chs = nonCheckCodenumber.trim().toCharArray();
        int luhmSum = 0;
        /**
         * 注意是从下标为0处开始的
         */
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            /**
             * 是偶数位数字做处理先乘以2（如果乘积为两位数，则将其减去9或个位与十位相加的和），再求和。
             * 不是则不做处理
             */
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public static Calendar convertDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

//    public static boolean isEmpty(String string) {
//        return string == null || string.trim().length() == 0;
//    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Object object) {
        return object == null || object.toString().trim().length() == 0;
    }

    /**
     * 数组转换为汉字
     *
     * @param number 数组
     * @return 汉字
     */
    public String convertNumber(Integer number) {
        switch (number) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            default:
                return "";
        }
    }
}
