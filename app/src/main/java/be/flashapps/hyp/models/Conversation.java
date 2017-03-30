package be.flashapps.hyp.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dietervaesen on 30/03/17.
 */

public class Conversation extends RealmObject {
    @PrimaryKey
    public int id;
    public boolean filledIn;
    public RealmList<Question> question;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFilledIn() {
        return filledIn;
    }

    public void setFilledIn(boolean filledIn) {
        this.filledIn = filledIn;
    }

    public RealmList<Question> getQuestion() {
        return question;
    }

    public void setQuestion(RealmList<Question> question) {
        this.question = question;
    }
}
