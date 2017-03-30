package be.flashapps.hyp.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;
import java.util.List;

import be.flashapps.hyp.R;
import be.flashapps.hyp.models.Author;
import be.flashapps.hyp.models.Conversation;
import be.flashapps.hyp.models.Message;
import be.flashapps.hyp.models.Question;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.messagesList)
    MessagesList messagerList;
    FirebaseUser firebaseUser;
    Realm realm;
    List<Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        realm=getRealm();
        firebaseUser = getFireBaseAuthInstance().getCurrentUser();
        Author author=getLoggedInUser();

        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(MessageActivity.this).load(url).placeholder(R.drawable.etediepizzajongen).into(imageView);
            }
        };
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(author.getId(), imageLoader);
        messagerList.setAdapter(adapter);

        questionList=realm.where(Conversation.class).findFirst().getQuestion();
        for (Question question:questionList) {
            Message message = new Message(question, question.getAuthor(),new Date());
            adapter.addToStart(message, true);
        }

    }

}
