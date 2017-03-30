package be.flashapps.hyp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import be.flashapps.hyp.models.Author;
import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private Realm mRealm;
    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";
    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
//


    public DatabaseReference getFireBaseDataBaseInstance() {
        return mDatabase;
    }

    public FirebaseAuth getFireBaseAuthInstance() {
            return mAuth;
    }

    Snackbar snackbar;

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mRealm = null;
    }


    public Realm getRealm() {
        return mRealm;
    }


    public Author getHypUser(){
        Author hyp=new Author();
        hyp.setId(1+"");
        hyp.setName("Hyp");
        hyp.setAvatar("https://www.google.be/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjfoJHl0_7SAhXJmBoKHbrrDFwQjRwIBw&url=https%3A%2F%2Fbonnesaffairesjeanchristophedelhaye.wordpress.com%2F2016%2F09%2F29%2Fpoints-bonus-card-hyper-carrefour-a-imprimer%2F&psig=AFQjCNES12K2-Vz02upMi7e5AyVbJof0ig&ust=1490977935323906");

        return hyp;
    }

    public Author getLoggedInUser(){
        FirebaseUser firebaseUser = getFireBaseAuthInstance().getCurrentUser();
        Author author=new Author();
        author.setId(firebaseUser.getUid());
        author.setName(firebaseUser.getDisplayName());
        author.setAvatar(firebaseUser.getPhotoUrl().getPath());
        return  author;
    }
}
