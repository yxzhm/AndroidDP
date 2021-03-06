package com.mot.AndroidDP;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

/**
 * Created by bkmr38 on 5/16/2016.
 */
public class FragementStatus extends Fragment {
    ListView statusList;
    SQLHelper sqlHelper;
    Button clearBut;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.status, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clearBut=(Button)getView().findViewById(R.id.clearBut);
        sqlHelper=new SQLHelper(this.getActivity().getApplicationContext());
        statusList=(ListView)getView().findViewById(R.id.listView);
        LoadData();

        clearBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ClearLocalData();
            }
        });
    }

    public void LoadData(){
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        List<StatusData> dataList =sqlHelper.query();
        for(StatusData data : dataList)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", data.Status);
            map.put("ItemText", data.DPName+":"+data.Time);
            mylist.add(map);
        }
        SimpleAdapter mSchedule = new SimpleAdapter(getView().getContext(), mylist, R.layout.my_listitem, new String[] {"ItemTitle", "ItemText"}, new int[] {R.id.ItemTitle,R.id.ItemText});
        statusList.setAdapter(mSchedule);
    }

    private  void ClearLocalData(){
        sqlHelper.clearLocalDB();
        LoadData();
    }
}
