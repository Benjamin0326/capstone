package com.android.capstone.yolo.scenario;

import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.model.Post;

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

    public static Post getBoardDetali(long id){

        Post post = new Post();
        post.setId(id);
        post.setTitle("Hello world!");
        post.setType("type1");
        post.setWriter("HyoJong");
        post.setContent("북미와 아시아 유럽 등에서 열고 있는 일렉트로니카 페스티벌. 약칭은 UMF.\n" +
                "메인인 미국에서는 1999년 부터 마이애미에서 열리고 있으며 Winter Music Conference라는 기간과 비슷하게 맞춰서 보통 3월 쯤에 열린다. 2016년 기준 18주년을 달성했다. 미국 뿐만 아니라 세계 여러 나라 등지에서 열리고 있는 점이 특징으로 꼽힌다. 이비자를 포함해 대한민국, 일본, 상파울루, 부에노스 아이레스, 산티아고, 크로아티아, 태국, 홍콩, 남아공등에서 열리고 있고 2016년 올해 브라질 리우데자네이루에서도 열릴 예정이다.\n" +
                "이와 비슷한 페스티벌로는 유럽에서 열리는 Tomorrowland가 꼽힌다. \n" +
                "첫 일본 페스티벌에는 한국에 로드투 울트라 2012를 통하여 내한한 Hardwell이 헤드라이너로 선정되었다.");
        post.setDate(new Date());

        String image1 = "https://ultramusicfestival.com/wp-content/uploads/2016/04/miami-2016-og-1.jpg";
        String image2 = "http://edmchicago.com/wp-content/uploads/2015/03/ultra_mainstage2014_design.jpg";
        ArrayList<String> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        post.setImage(images);

        return post;
    }

    public static List<BoardList> getBoardList(long id){

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
