import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static Map<String, Object> parseSimpleJson(String json) {
        json = json.trim();
        Map<String, Object> result = new HashMap<>();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1).trim();
        }
        int brace = 0, bracket = 0, last = 0;
        String key = null;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') brace++;
            if (c == '}') brace--;
            if (c == '[') bracket++;
            if (c == ']') bracket--;
            if (c == ':' && key == null && brace == 0 && bracket == 0) {
                key = json.substring(last, i).replaceAll("\"", "").trim();
                last = i + 1;
            } else if ((c == ',' && brace == 0 && bracket == 0) || i == json.length() - 1) {
                int end = (i == json.length() - 1) ? i + 1 : i;
                String value = json.substring(last, end).trim();
                if (value.startsWith("{") && value.endsWith("}")) {
                    result.put(key, parseSimpleJson(value));
                } else if (value.startsWith("\"") && value.endsWith("\"")) {
                    result.put(key, value.substring(1, value.length() - 1));
                } else if (value.matches("-?\\d+")) {
                    result.put(key, Integer.parseInt(value));
                } else {
                    result.put(key, value);
                }
                key = null;
                last = i + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        // Read JSON file
        String jsonContent = new String(Files.readAllBytes(Paths.get("input.json")));
        Map<String, Object> obj = parseSimpleJson(jsonContent);

        // Extract n and k
        Map<String, Object> keys = (Map<String, Object>) obj.get("keys");
        int n = Integer.parseInt(keys.get("n").toString());
        int k = Integer.parseInt(keys.get("k").toString());

        // Extract shares
        List<Share> shares = new ArrayList<>();
        for (String key : obj.keySet()) {
            if (key.equals("keys")) continue;
            Object raw = obj.get(key);
            if (raw instanceof Map) {
                Map<String, Object> shareObj = (Map<String, Object>) raw;
                int x = Integer.parseInt(key);
                int base = Integer.parseInt(shareObj.get("base").toString());
                String valueStr = shareObj.get("value").toString();
                BigInteger y = new BigInteger(valueStr, base);
                shares.add(new Share(x, y));
            }
        }

        // Use Lagrange interpolation to find the constant term (secret)
        List<Share> kShares = shares.subList(0, k);
        BigInteger secret = MathUtils.lagrangeInterpolation(kShares);

        System.out.println("Secret: " + secret);
    }
}
