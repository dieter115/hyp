package be.flashapps.hyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.orhanobut.logger.Logger;
import com.pixplicity.easyprefs.library.Prefs;

import be.flashapps.hyp.Helper_Fragments;
import be.flashapps.hyp.R;
import be.flashapps.hyp.fragments.AllergiesFragment;
import be.flashapps.hyp.fragments.RecipieFragment;
import be.flashapps.hyp.models.Conversation;
import be.flashapps.hyp.models.Question;
import be.flashapps.hyp.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends BaseActivity {
    public static final int IDENTIFIER_HYP = 0, IDENTIFIER_SHOPPINGLIST = 1, IDENTIFIER_ALLERGIES = 2, IDENTIFIER_CARREFOURGO = 3, IDENTIFIER_SETTINGS = 4, IDENTIFIER_LOGOUT = 5;
    private static final String TAG = "MAINTAG";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawer drawer;

    Realm realm;
    FirebaseUser firebaseUser;
    RecipieFragment recipieFragment;
    private DatabaseReference fireBaseInstance;
    private AllergiesFragment allergyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        realm = getRealm();


        makeConversation();

        firebaseUser = getFireBaseAuthInstance().getCurrentUser();
        fireBaseInstance = getFireBaseDataBaseInstance();
        Query getAllrecipesQuery = fireBaseInstance.child("recipes").orderByChild("id");
        getAllrecipesQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Recipe recipe = dataSnapshot.getValue(Recipe.class);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(recipe);
                    }
                });
                Logger.d("recipe added " + recipe.getNaam());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final Recipe recipe = dataSnapshot.getValue(Recipe.class);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(recipe);

                    }
                });
                Logger.d("recipe changed " + recipe.getNaam());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                final Recipe recipe = dataSnapshot.getValue(Recipe.class);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(Recipe.class).equalTo("id", recipe.getId()).findFirst().deleteFromRealm();
                    }
                });
                Logger.d("recipe changed " + recipe.getNaam());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.d("Failed to read value.", databaseError.toException());
            }
        });

        fixSideMenu();
    }

    public void makeConversation() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Question question = new Question(1,false, getString(R.string.thankyou_question), null);
                question.setAuthor(getHypUser());

                Question question2 = new Question(2,false, getString(R.string.thankyou_question), null);
                question2.setAuthor(getLoggedInUser());


                RealmList<Question> questions = new RealmList<Question>();
                questions.add(question);
                questions.add(question2);

                Conversation conversation = new Conversation();
                conversation.setId(1);
                conversation.setFilledIn(false);
                conversation.setQuestion(questions);

                realm.copyToRealmOrUpdate(conversation);
            }
        });
    }


    public void fixSideMenu() {
        PrimaryDrawerItem hypDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_HYP).withName("Praat met Hyp");
        PrimaryDrawerItem shoppingListDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_SHOPPINGLIST).withName("Mijn shoppinglijst");
        PrimaryDrawerItem allergyDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_ALLERGIES).withName("Allergieën");
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_SETTINGS).withName("Instellingen");
        final PrimaryDrawerItem logoutDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_LOGOUT).withName("Uitloggen");
        PrimaryDrawerItem carrefourGoDrawerItem = new PrimaryDrawerItem().withIdentifier(IDENTIFIER_CARREFOURGO).withName("Carrefour Drive");

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
/*
                .withAccountHeader(R.layout.header)
*/
                /*.withHeaderBackground(R.drawable.centerparcs_logo)*/
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(firebaseUser.getDisplayName()).withEmail(firebaseUser.getEmail()).withIcon(firebaseUser.getPhotoUrl())
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        hypDrawerItem,
                        shoppingListDrawerItem,
                        allergyDrawerItem,
                        settingsDrawerItem,
                        logoutDrawerItem,
                        carrefourGoDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == IDENTIFIER_HYP) {
                            if (realm.where(Conversation.class).equalTo("filledIn", false).findFirst() != null) {
                                Intent intent=new Intent(MainActivity.this,MessageActivity.class);
                                startActivity(intent);
                            } else {
                                if (recipieFragment == null)
                                    recipieFragment = RecipieFragment.newInstance("", "");
                                Helper_Fragments.replaceFragment(MainActivity.this, recipieFragment, false, TAG);
                            }
                        }

                        if (drawerItem.getIdentifier() == IDENTIFIER_ALLERGIES) {
                            if (allergyFragment == null)
                                allergyFragment = AllergiesFragment.newInstance("", "");

                            Helper_Fragments.replaceFragment(MainActivity.this, allergyFragment, false, TAG);

                        }
                        if (drawerItem.getIdentifier() == IDENTIFIER_CARREFOURGO) {

                        } else if (drawerItem.getIdentifier() == IDENTIFIER_SETTINGS) {

                        } else if (drawerItem.getIdentifier() == IDENTIFIER_SHOPPINGLIST) {

                        } else if (drawerItem.getIdentifier() == IDENTIFIER_LOGOUT) {
                            //uitloggen
                           /* realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.where(AccessToken.class).findAll().deleteAllFromRealm();
                                    realm.where(Card.class).findAll().deleteAllFromRealm();
                                    realm.where(UploadCard.class).findAll().deleteAllFromRealm();
                                    realm.where(UploadQuestion.class).findAll().deleteAllFromRealm();
                                }
                            });*/

                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {

                                    realm.delete(Recipe.class);
                                }
                            });
                            getFireBaseAuthInstance().signOut();
                            Prefs.clear();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return false;
                    }
                })
                .build();

        drawer.setSelection(IDENTIFIER_HYP);
    }
}
