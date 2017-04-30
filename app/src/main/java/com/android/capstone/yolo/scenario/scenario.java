package com.android.capstone.yolo.scenario;

import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.CommunityList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class scenario {

    public static List<CommunityList> getCommunityList(){

        ArrayList<CommunityList> lists = new ArrayList<>();

        CommunityList item1 = new CommunityList();
        item1.setTitle("Ultra Music Festival 2017");
        item1.setSub("sub title");
        item1.setPath("https://lh4.ggpht.com/VT5IuliOBlW7SC_Qkfetu2RZH-JWJEWTg3dZCTXMEHNlPPLFlPMTTQry8LrbNYfCNg=w300");
        item1.setId(1);

        CommunityList item2 = new CommunityList();
        item2.setTitle("World DJ Festival 2017");
        item2.setSub("sub title");
        item2.setPath("https://lh3.ggpht.com/tPgZCJTEREZ8KtJq1ETVqdoXLaTXETJT6D7JsQRGMjXRjP27idjcj2-nTu5P5Ue_hA8=w300");
        item2.setId(2);

        lists.add(item1);
        lists.add(item2);

        return lists;
    }

    public static List<BoardList> getBoardList(int id){

        ArrayList<BoardList> lists = new ArrayList<>();

        BoardList item1 = new BoardList();
        item1.setId(1);
        item1.setTitle("hello world");
        item1.setWriter("John");
        item1.setType("type3");
        item1.setTimestamp(new Date());

        BoardList item2 = new BoardList();
        item2.setId(2);
        item2.setTitle("good bye");
        item2.setWriter("Mike");
        item2.setType("type1");
        item2.setTimestamp(new Date());

        BoardList item3 = new BoardList();
        item3.setId(3);
        item3.setTitle("How are you?");
        item3.setWriter("Alice");
        item3.setType("type2");
        item3.setTimestamp(new Date());

        BoardList item4 = new BoardList();
        item4.setId(4);
        item4.setTitle("I'm fine, and you?");
        item4.setWriter("Tiger");
        item4.setType("type1");
        item4.setTimestamp(new Date());

        BoardList item5 = new BoardList();
        item5.setId(5);
        item5.setTitle("Baaaaaammmmm");
        item5.setWriter("Joooon");
        item5.setType("type2");
        item5.setTimestamp(new Date());

        BoardList item6 = new BoardList();
        item6.setId(6);
        item6.setTitle("!@#$!%!^!");
        item6.setWriter("Hyo Jong");
        item6.setType("type3");
        item6.setTimestamp(new Date());

        lists.add(item1);
        lists.add(item2);
        lists.add(item3);
        lists.add(item4);
        lists.add(item5);
        lists.add(item6);

        return lists;
    }
}
