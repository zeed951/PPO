package Route_GPX;

//from   w w  w  .j a v  a  2  s.  c  o m
//import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

/**
 * Methods to encode and decode a polyline with Google polyline encoding/decoding scheme.
 * See https://developers.google.com/maps/documentation/utilities/polylinealgorithm
 */
public class GooglePolyline {

    private static StringBuffer encodeSignedNumber(int num) {
        int sgn_num = num << 1;
        if (num < 0) {
            sgn_num = ~(sgn_num);
        }
        return (encodeNumber(sgn_num));
    }

    private static StringBuffer encodeNumber(int num) {
        StringBuffer encodeString = new StringBuffer();
        while (num >= 0x20) {
            int nextValue = (0x20 | (num & 0x1f)) + 63;
            encodeString.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        encodeString.append((char) (num));
        return encodeString;
    }

    /**
     * Encode a polyline with Google polyline encoding method
     *
     * @param polyline  the polyline
     * @param precision 1 for a 6 digits encoding, 10 for a 5 digits encoding.
     * @return the encoded polyline, as a String
     */
    public static String encode(ArrayList<Point> polyline, int precision) {
        StringBuffer encodedPoints = new StringBuffer();
        int prev_lat = 0, prev_lng = 0;
        for (Point trackpoint : polyline) {
            int lat = trackpoint.getLatitudeE6() / precision;
            int lng = trackpoint.getLongitudeE6() / precision;
            encodedPoints.append(encodeSignedNumber(lat - prev_lat));
            encodedPoints.append(encodeSignedNumber(lng - prev_lng));
            prev_lat = lat;
            prev_lng = lng;
        }
        return encodedPoints.toString();
    }

    /**
     * Decode a "Google-encoded" polyline
     *
     * @param encodedString
     * @param precision     1 for a 6 digits encoding, 10 for a 5 digits encoding.
     * @return the polyline.
     */
    public static ArrayList<Point> decode(String encodedString, int precision) {
        int index = 0;
        int len = encodedString.length();
        double lat = 0, lng = 0;
        ArrayList<Point> polyline = new ArrayList<Point>(len / 3);
        //capacity estimate: polyline size is roughly 1/3 of string length for a 5digits encoding, 1/5 for 10digits.

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Point p = new Point(lat * precision / 100000, lng * precision / 100000,0);
            polyline.add(p);
        }

        //Log.d("BONUSPACK", "decode:string="+len+" points="+polyline.size());

        return polyline;
    }
}
