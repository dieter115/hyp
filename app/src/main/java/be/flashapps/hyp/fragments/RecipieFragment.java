package be.flashapps.hyp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import be.flashapps.hyp.App;
import be.flashapps.hyp.R;
import be.flashapps.hyp.activities.BaseActivity;
import be.flashapps.hyp.adapters.RecipeRealmAdapter;
import be.flashapps.hyp.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


public class RecipieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mActivity;
    private Realm realm;
    @BindView(R.id.discretescrollingview)
    DiscreteScrollView discreteScrollView;
    private RecipeRealmAdapter recipeAdapter;

    public RecipieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity=getActivity();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipieFragment newInstance(String param1, String param2) {
        RecipieFragment fragment = new RecipieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_recipie, container, false);
        ButterKnife.bind(this,root);
        realm=((BaseActivity)mActivity).getRealm();



       /* realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Recipe recipe=new Recipe(1,"pizza","https://simplyyou.carrefour.eu/sites/simplyyou.carrefour.eu/files/styles/10_column_1_2/public/recipes/_pizza_aux_epinards_ricotta_et_parmesan.jpg?itok=CREaLUBC&timestamp=1468499964");
                Recipe recipe1=new Recipe(2,"spaghetti","https://simplyyou.carrefour.eu/sites/simplyyou.carrefour.eu/files/styles/10_column_1_2/public/recipes/_pizza_aux_epinards_ricotta_et_parmesan.jpg?itok=CREaLUBC&timestamp=1468499964");
                Recipe recipe2=new Recipe(3,"worst met stoemp","jdjdjdjdjdj");


                realm.copyToRealmOrUpdate(recipe);
                realm.copyToRealmOrUpdate(recipe1);
                realm.copyToRealmOrUpdate(recipe2);
            }
        });*/

        recipeAdapter=new RecipeRealmAdapter(App.getContext(),realm.where(Recipe.class).findAll(),true,mActivity);
        discreteScrollView.setAdapter(recipeAdapter);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.9f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());

        return root;
    }
}
