package com.futureinst.home.forecast;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.push.PushWebActivity;
import com.futureinst.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BannerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BannerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private QueryEventDAO queryEvent;
    private ImageView iv_banner;

    public static BannerFragment newInstance(QueryEventDAO queryEvent) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,queryEvent);
        fragment.setArguments(args);
        return fragment;
    }

    public BannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryEvent = (QueryEventDAO)getArguments().getSerializable(ARG_PARAM1);
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_banner, container, false);
                iv_banner = (ImageView)root.findViewById(R.id.iv_banner);
        ImageLoader.getInstance().displayImage(queryEvent.getImgsrc(),iv_banner, ImageLoadOptions.getOptions(R.drawable.image_top_default));
        iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(queryEvent.getLead())) return;
                Intent intent = new Intent(getActivity(), PushWebActivity.class);
                intent.putExtra("url", queryEvent.getLead());
                intent.putExtra("title", queryEvent.getTitle());
                startActivity(intent);
            }
        });
        return root;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
