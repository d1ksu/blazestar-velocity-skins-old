package pl.blazestar.velocity.skins.authentication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import pl.blazestar.velocity.skins.configuration.SkinConfiguration;

public final  class AuthenticationService {

    private Logger logger;
    private SkinConfiguration skinConfiguration;

    public AuthenticationService(Logger logger, SkinConfiguration skinConfiguration) {
        this.logger = logger;
        this.skinConfiguration = skinConfiguration;
    }

    public ResponseData check(String username) {
        long start = System.currentTimeMillis();
        try {
            URL url = new URL("https://api.ashcon.app/mojang/v2/user/" + username);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader input = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader output = new BufferedReader(input);
                StringBuilder stringBuilder = new StringBuilder();
                Objects.requireNonNull(stringBuilder);
                output.lines().forEach(stringBuilder::append);
                JsonObject jsonObject = (new JsonParser()).parse(stringBuilder.toString()).getAsJsonObject();
                input.close();
                output.close();
                this.logger.info(String.format("Ashcon API checking %s in %s", username, System.currentTimeMillis() - start));
                httpURLConnection.disconnect();
                JsonObject textures = jsonObject.getAsJsonObject("textures").getAsJsonObject("raw");
                return new ResponseData(true,
                        UUID.fromString(jsonObject.get("uuid").getAsString()), true, textures
                        .get("value").getAsString(), textures
                        .get("signature").getAsString());
            }
            if (httpURLConnection.getResponseCode() == 404) {
                UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
                this.logger.info(String.format("Ashcon API checking %s in %s", username, System.currentTimeMillis() - start));
                httpURLConnection.disconnect();
                if (this.skinConfiguration.isDefaultSkinEnabled())
                    return new ResponseData(true, uuid, false, this.skinConfiguration.getTexture(), this.skinConfiguration.getSignature());
                return new ResponseData(true, uuid, false, null, null);
            }
            httpURLConnection.disconnect();
            return ResponseData.notResponse();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseData.notResponse();
        }
    }

}