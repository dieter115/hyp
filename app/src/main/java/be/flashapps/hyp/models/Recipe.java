package be.flashapps.hyp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dietervaesen on 14/03/17.
 */

public class Recipe extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;

    public Recipe(int id,String name){
        this.id=id;
        this.name=name;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
