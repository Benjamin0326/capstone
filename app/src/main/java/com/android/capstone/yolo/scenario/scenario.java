package com.android.capstone.yolo.scenario;

import com.android.capstone.yolo.model.CommunityList;

import java.util.ArrayList;
import java.util.List;

public class scenario {

    public static List<CommunityList> getCommunityList(){

        ArrayList<CommunityList> lists = new ArrayList<>();

        CommunityList item1 = new CommunityList();
        item1.setTitle("Ultra Music Festival 2017");
        item1.setSub("sub title");
        item1.setPath("https://lh4.ggpht.com/VT5IuliOBlW7SC_Qkfetu2RZH-JWJEWTg3dZCTXMEHNlPPLFlPMTTQry8LrbNYfCNg=w300");

        CommunityList item2 = new CommunityList();
        item2.setTitle("World DJ Festival 2017");
        item2.setSub("sub title");
        item2.setPath("https://lh3.ggpht.com/tPgZCJTEREZ8KtJq1ETVqdoXLaTXETJT6D7JsQRGMjXRjP27idjcj2-nTu5P5Ue_hA8=w300");

        lists.add(item1);
        lists.add(item2);

        return lists;
    }
}
