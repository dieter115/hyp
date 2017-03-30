package be.flashapps.hyp.models;

import com.stfalcon.chatkit.commons.models.IUser;

import io.realm.RealmObject;

/**
 * Created by dietervaesen on 30/03/17.
 */

public class Author extends RealmObject implements IUser {

    String id;
    String name;
    String avatar;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
