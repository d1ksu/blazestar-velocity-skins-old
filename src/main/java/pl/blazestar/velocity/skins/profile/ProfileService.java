package pl.blazestar.velocity.skins.profile;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.slf4j.Logger;
import pl.blazestar.velocity.skins.authentication.AuthenticationService;
import pl.blazestar.velocity.skins.authentication.ResponseData;
import pl.blazestar.velocity.skins.database.Database;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public final class ProfileService {


    private final AuthenticationService authenticationService;
    private final Database database;
    private final Logger logger;

    public ProfileService(AuthenticationService authenticationService, Database database, Logger logger){
        this.authenticationService = authenticationService;
        this.database = database;
        this.logger = logger;
    }

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public Optional<Profile> getProfile(UUID uniqueId) {
        return Optional.ofNullable(this.profiles.get(uniqueId));
    }

    public Optional<Profile> getProfile(String nickname){
        return this.profiles
                .values()
                .stream()
                .filter(profile -> profile.getNickname().equalsIgnoreCase(nickname))
                .findFirst();
    }

    public void loadAll(){
        FindIterable<Document> documents = database.getCollection("profiles-skins").find();
        MongoCursor<Document> it = documents.iterator();
        int loaded = 0;
        long start = System.currentTimeMillis();
        while (it.hasNext()){
            loaded++;
            Profile profile = database.getGson().fromJson(it.next().toJson(), Profile.class);
            profiles.put(profile.getUniqueId(), profile);
        }
        logger.info("Loaded " + loaded + " profiles in " + (System.currentTimeMillis() - start) + "ms");
        it.close();
    }
    public Optional<Profile> createProfile(String username) {
        ResponseData responseData = this.authenticationService.check(username);
        if (!responseData.isConnected())
            return Optional.empty();
        Profile profile = new Profile(username, responseData.getUniqueId(), responseData.isPaid(), (responseData.getTexture() == null) ? null : new ProfileSkin(responseData.getTexture(), responseData.getSignature()));
        CompletableFuture.runAsync(() -> database.insert(profile));
        this.profiles.put(profile.getUniqueId(), profile);
        return Optional.of(profile);
    }

    public Map<UUID, Profile> getProfiles() {
        return profiles;
    }
}
