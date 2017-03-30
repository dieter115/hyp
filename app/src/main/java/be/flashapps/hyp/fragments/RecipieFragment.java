package be.flashapps.hyp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class RecipieFragment extends Fragment implements DiscreteScrollView.CurrentItemChangeListener {
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

    @BindView(R.id.recipe_name)
    TextView currentItemName;

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



        recipeAdapter=new RecipeRealmAdapter(App.getContext(),realm.where(Recipe.class).findAll(),true,mActivity);
        discreteScrollView.setCurrentItemChangeListener(this);
        discreteScrollView.setAdapter(recipeAdapter);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.9f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build());

        return root;
    }


   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_btn_rate:
                Item current = data.get(itemPicker.getCurrentItem());
                shop.setRated(current.getId(), !shop.isRated(current.getId()));
                changeRateButtonState(current);
                break;
            case R.id.home:
                finish();
                break;
            case R.id.btn_transition_time:
                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
                break;
            case R.id.btn_smooth_scroll:
                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }*/

    private void onItemChanged(Recipe recipe) {
        currentItemName.setText(recipe.getNaam());
        /*currentItemPrice.setText(item.getPrice());*/
        /*changeRateButtonState(item);*/
    }

    /*private void changeRateButtonState(Recipe recipe) {
        if (recipe.isRated(recipe.getId())) {
            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
        } else {
            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
        }
    }*/

    @Override
    public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof RecipeRealmAdapter.RecipeViewHolder)
        onItemChanged(recipeAdapter.getData().get(position));
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(discreteScrollView, "Unsupported click", Snackbar.LENGTH_SHORT).show();
    }
}
