package com.vishal.chipsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnChipsRemovedListener {


    private static final int RC_CHIPS = 11;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.chips_editext)
    EditText chipsEditext;
    @BindView(R.id.btn_add_chips)
    Button btnAddChips;
    private ArrayList<String> itemsList;
    private ChipsAdapter chipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        populateListData();
    }

    @Override
    public void onChipsRemoved(ArrayList<String> updatedList, int requestCode) {
        switch (requestCode) {
            case RC_CHIPS:
                itemsList.clear();

                itemsList.addAll(updatedList);
        }
    }

    /**
     * method to init all data and collections
     */
    private void populateListData() {

        itemsList = new ArrayList<>();

        itemsList.add("ABC");
        itemsList.add("XYZ");
        itemsList.add("PQR");
        itemsList.add("EFG");
        itemsList.add("HIJ");


        if (itemsList.size() == 0) {
            itemsList.clear();
            recyclerview.removeAllViews();
        } else {

            addChips(recyclerview, itemsList, RC_CHIPS);
        }
    }


    /**
     * add Chips view in recyclerview
     *
     * @param recyclerView recycler view for chips
     * @param items        user added items list
     * @param requestCode  to identify request
     */
    private void addChips(RecyclerView recyclerView, ArrayList<String> items, int requestCode) {
//        recyclerView.removeAllViews();

//
//        RecyclerView recyclerView = new RecyclerView(context);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        flowLayoutManager.setAlignment(Alignment.LEFT);
        recyclerView.setLayoutManager(flowLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        chipsAdapter = new ChipsAdapter(MainActivity.this, items, this, requestCode);
        recyclerView.setAdapter(chipsAdapter);
//        recyclerView.addView(recyclerView, 0);


    }

    @OnClick(R.id.btn_add_chips)
    public void onViewClicked() {

        if (!TextUtils.isEmpty(chipsEditext.getText().toString().trim())) {

            if(chipsAdapter != null)
            {
                itemsList.add(chipsEditext.getText().toString().trim());
                chipsEditext.setText("");
                chipsAdapter.notifyDataSetChanged();
            }
        }
    }
}


