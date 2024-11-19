import java.util.*;
import java.io.*;
import java.net.*;

// Interface for trackable entities
interface Trackable {
    String getId();
    String getName();
    Map<String, Object> getStatus();
}

// Base class for shared train properties
class TrainBase {
    protected String id;
    protected String name;

    public TrainBase(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

// Train class that extends TrainBase and implements Trackable
class Train extends TrainBase implements Trackable {
    public Train(String id, String name) {
        super(id, name);
    }

    @Override
    public Map<String, Object> getStatus() {
        return TrainTracker.getTrainStatus(this.id);
    }
}

// TrainTracker class for handling train tracking logic
class TrainTracker {
    public static Map<String, Object> getTrainStatus(String trainNo) {
        String url = "https://rappid.in/apis/train.php?train_no=" + trainNo;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return parseJSON(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Object> parseJSON(String json) {
        Map<String, Object> result = new HashMap<>();
        if (json.contains("\"success\":true")) {
            result.put("success", true);
            result.put("train_name", extractValue(json, "train_name"));
            result.put("message", extractValue(json, "message"));
            result.put("updated_time", extractValue(json, "updated_time"));

            String dataSection = extractArray(json, "data");
            List<Map<String, String>> stations = parseArray(dataSection);
            result.put("data", stations);
        } else {
            result.put("success", false);
        }
        return result;
    }

    private static String extractValue(String json, String key) {
        int start = json.indexOf("\"" + key + "\":\"") + key.length() + 4;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static String extractArray(String json, String key) {
        int start = json.indexOf("\"" + key + "\":[") + key.length() + 3;
        int end = json.indexOf("]", start) + 1;
        return json.substring(start, end);
    }

    private static List<Map<String, String>> parseArray(String array) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] items = array.split("\\},\\{");
        for (String item : items) {
            Map<String, String> map = new HashMap<>();
            String[] fields = item.replaceAll("[\\[\\]\\{\\}]", "").split(",");
            for (String field : fields) {
                String[] keyValue = field.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].replace("\"", "").trim();
                    String value = keyValue[1].replace("\"", "").trim();
                    map.put(key, value);
                }
            }
            list.add(map);
        }
        return list;
    }
}

// Main class for program execution
public class samp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Train Tracking System");
        System.out.print("Enter Train Number to Track: ");
        String trainId = scanner.nextLine();

        Train train = new Train(trainId, "Unknown Train");
        Map<String, Object> trainStatus = train.getStatus();

        if (trainStatus != null && (boolean) trainStatus.get("success")) {
            String trainName = (String) trainStatus.get("train_name");
            String message = (String) trainStatus.get("message");
            String updatedTime = (String) trainStatus.get("updated_time");

            System.out.println("\nTracking Train: " + trainName);
            System.out.println(message);
            System.out.println("Last Updated: " + updatedTime);

            List<Map<String, String>> stations = (List<Map<String, String>>) trainStatus.get("data");
            System.out.println("\nStation Details:");
            System.out.printf("%-20s %-10s %-10s %-10s %-10s %-10s%n", "Station Name", "Distance", "Timing", "Delay", "Platform", "Halt");

            for (Map<String, String> station : stations) {
                System.out.printf("%-20s %-10s %-10s %-10s %-10s %-10s%n",
                        station.get("station_name"),
                        station.getOrDefault("distance", "N/A"),
                        station.getOrDefault("timing", "N/A"),
                        station.getOrDefault("delay", "N/A"),
                        station.getOrDefault("platform", "N/A"),
                        station.getOrDefault("halt", "N/A"));
            }
        } else {
            System.out.println("Error fetching train status. Please try again later.");
        }

        scanner.close();
    }
}
