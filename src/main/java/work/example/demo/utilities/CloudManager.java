package work.example.demo.utilities;


import com.cloudinary.Cloudinary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudManager {

    private static Map<String, String> map;
    static{
        map = new HashMap<String, String>();
        map.put("cloud_name", "dijmdqpky");
        map.put("api_key", "659572112169481");
        map.put("api_secret", "x0m8gSwoLym2m47z9CalFKy1Yb8");
    }
    private static Cloudinary cloudinary = new Cloudinary(map);

    public static String uploadImage(File picture) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> result = cloudinary.uploader().upload(picture,map );
        return result.get("secure_url");
    }
}