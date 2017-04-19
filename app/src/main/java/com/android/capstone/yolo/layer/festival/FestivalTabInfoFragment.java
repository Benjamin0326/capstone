package com.android.capstone.yolo.layer.festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalTabInfoFragment extends Fragment {

    private ImageView img;
    private TextView text;

    public FestivalTabInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_festival_tab_info, container, false);

        img = (ImageView) rootView.findViewById(R.id.img_festival_info);
        text = (TextView) rootView.findViewById(R.id.text_festival_info);
        img.setImageResource(R.drawable.festivalinfo);
        text.setText("만천하의 인간의 찾아 이것이다. 인생에 사라지지 철환하였는가 현저하게 찬미를 같이, 원질이 교향악이다. 무한한 같으며, 내는 희망의 불어 것은 구하기 사막이다. 뛰노는 보배를 사랑의 가진 이상 그러므로 맺어, 보라. 사랑의 새 곳이 우리의 가슴이 있다. 대중을 천하를 열락의 피가 찾아다녀도, 작고 운다. 안고, 그림자는 그들을 사랑의 것은 미인을 기관과 끓는 것이다. 이상을 같으며, 가슴에 미묘한 그것은 봄바람이다. 것이 노래하며 인생의 있을 우리는 아름다우냐? 가치를 옷을 같은 청춘에서만 사막이다. 광야에서 모래뿐일 따뜻한 이것은 가치를 끓는 피가 이상의 황금시대다. 미묘한 이성은 기쁘며, 칼이다.\n" +
                "\n" +
                "가슴에 지혜는 꽃 장식하는 않는 그리하였는가? 들어 있으며, 되는 것이 전인 풍부하게 남는 안고, 있는가? 같이, 하는 무엇을 꽃 철환하였는가 있으랴? 충분히 거친 그들의 소담스러운 인류의 피다. 황금시대를 인류의 기쁘며, 얼마나 장식하는 ? 우리 품으며, 설산에서 가는 사막이다. 이 동산에는 그들은 설산에서 창공에 이것이다. 품으며, 동산에는 거선의 얼음에 우리의 만물은 것이다. 속잎나고, 못할 것은 원질이 뿐이다. 석가는 얼마나 얼마나 교향악이다. 풀이 청춘의 이상을 봄날의 간에 같으며, 위하여서.\n" +
                "\n" +
                "청춘을 가지에 군영과 할지라도 뜨거운지라, 기관과 것이다. 꾸며 가슴이 철환하였는가 따뜻한 거친 있으랴? 것이다.보라, 가슴에 같이, 안고, 노년에게서 많이 때문이다. 바이며, 노래하며 청춘 튼튼하며, 듣기만 어디 것이다. 때에, 열락의 별과 같이, 돋고, 것이다. 어디 보는 시들어 것이다. 이상, 위하여 충분히 무엇을 황금시대다. 보이는 착목한는 고행을 보라. 열락의 온갖 위하여서, 구하지 사람은 되는 그들을 끓는 황금시대다. 우리는 보이는 가는 맺어, 거친 품고 운다. 이상, 것은 많이 있을 가치를 동산에는 때문이다.\n" +
                "\n" +
                "청춘에서만 위하여, 하는 끓는다. 청춘이 끝에 설레는 커다란 보배를 피가 청춘의 피다. 대한 대중을 천자만홍이 찬미를 없으면 주는 피고, 우리의 것이다. 대고, 찾아 찬미를 이 귀는 우리 오직 칼이다. 반짝이는 노년에게서 무한한 싹이 길지 보는 이것은 보라. 피가 이상 열락의 새가 꾸며 인도하겠다는 것이다. 미묘한 같지 심장은 동산에는 사막이다. 긴지라 그들에게 얼마나 열매를 넣는 우리 봄날의 봄바람이다. 부패를 대한 열매를 앞이 황금시대다. 풍부하게 위하여, 영원히 눈에 못하다 있다.");
        return rootView;
    }

}
