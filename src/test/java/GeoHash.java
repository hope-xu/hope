/**
 * @author Hope
 * Date： 2020/07/27  18:04
 * 描述：
 */
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeoHash {

    //经纬度单独编码长度
    private static int numbits = 6 * 5;

    //32位编码对应字符
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    //定义编码映射关系
    final static HashMap<Character, Integer> lookup = new HashMap<>();

    //初始化编码映射内容
    static {
        int i = 0;
        for (char c : digits)
            lookup.put(c, i++);
    }

    //对编码后的字符串解码
    public double[] decode(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append( Integer.toString(i, 2).substring(1) );
        }

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        //even bits 偶数位，经度
        int j =0;
        for (int i=0; i< numbits*2;i+=2) {
            boolean isSet = false;
            if ( i < buffer.length() )
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        //odd bits 奇数位，纬度
        j=0;
        for (int i=1; i< numbits*2;i+=2) {
            boolean isSet = false;
            if ( i < buffer.length() )
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        double lon = decode(lonset, -180, 180);
        double lat = decode(latset, -90, 90);

        return new double[] {lat, lon};
    }

    //根据二进制和范围解码
    private double decode(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i=0; i<bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }


    //对经纬度进行编码
    public String encode(double lat, double lon) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append( (lonbits.get(i))?'1':'0');
            buffer.append( (latbits.get(i))?'1':'0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    //根据经纬度和范围，获取对应二进制
    private BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    //将经纬度合并后的二进制进行指定的32位编码
    public static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }

    public static void main(String[] args)  throws Exception{

        GeoHash geohash = new GeoHash();
        String s = geohash.encode(40, 120);
        System.out.println(s);
        double[] geo = geohash.decode(s);
        System.out.println(geo[0]+" "+geo[1]);



        //yb0bh2n0p058
        //45.0 124.9999999254942
    }



    public static <A,B> Map<A,B> list2Map(List<B> list,String methodName,Class<B> c){
        Map<A,B> map = new HashMap<A,B>();
        if (list != null) {
            try{
                Method method = c.getMethod(methodName);
                for (int i = 0 ,j = list.size(); i < j; i++) {
                    B v = list.get(i);
                    A k = (A)method.invoke(list.get(i));
                    map.put(k,v);
                }
            }catch (Exception e){
                //抛异常或者其它
            }
        }

        return map;
    }




}
