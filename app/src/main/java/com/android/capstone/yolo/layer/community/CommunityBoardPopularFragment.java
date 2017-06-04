package com.android.capstone.yolo.layer.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.capstone.yolo.R;

public class CommunityBoardPopularFragment extends Fragment{
    ListView boardList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board_popular, container, false);
        boardList = (ListView) view.findViewById(R.id.board_list);

        return view;
    }
}
