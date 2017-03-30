package be.flashapps.hyp.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/**
 * Created by dietervaesen on 30/03/17.
 */

public class Message implements IMessage {

    String id;
    String text;
    IUser author;
    Date createdAt;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public IUser getAuthor() {
        return author;
    }

    public void setAuthor(IUser author) {
        this.author = author;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Message(Question question,IUser author,Date date){
        this.id=question.getId()+"";
        this.text=question.getQuestion();
        this.author=author;
        this.createdAt=date;
    }
}
