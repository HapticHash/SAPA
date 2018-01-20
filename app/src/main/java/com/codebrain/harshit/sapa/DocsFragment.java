package com.codebrain.harshit.sapa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocsFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    protected RecyclerView mRecyclerView3;
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    //int placeImage[]= {R.drawable.five, R.drawable.six, R.drawable.bg3, R.drawable.seven, R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4};
    Context context;
    ArrayList<String> placeImage = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DocsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocsFragment newInstance(String param1, String param2) {
        DocsFragment fragment = new DocsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_docs, container, false);
        rootView.setTag(TAG);
        mRecyclerView3 = (RecyclerView) rootView.findViewById(R.id.favDocs);
        setupRecycleview(mRecyclerView3);

        context = getContext();

        database  = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }
        catch (Exception e){}

        mRef = database.getReference().child("docsUrl");
        placeImage.clear();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot p : dataSnapshot.getChildren()) {
                    String url = (String) p.child("url").getValue();
                    placeImage.add(url);
                    Log.d("xyz",url+"");

                }
                mRecyclerView3.setAdapter(new StaggeredAdapter(context,placeImage));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    private void setupRecycleview(RecyclerView rv)
    {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(new StaggeredAdapter(rv.getContext(),placeImage));

    }

    public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {



        ArrayList<String> placeList;
        // Provide a reference to the views for each data item
        public class ViewHolder extends RecyclerView.ViewHolder {


            public ImageView placePic;

            public ViewHolder(View itemView) {
                super(itemView);

                placePic = (ImageView) itemView.findViewById(R.id.placePic);
            }
        }

        public StaggeredAdapter(Context c,ArrayList<String> placeList){
            context = c;
            this.placeList = placeList;
        }


        // Create new views (invoked by the layout manager)
        @Override
        public StaggeredAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.staggered_layout, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new StaggeredAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Glide.with(context).load(placeList.get(position)).apply(new RequestOptions().placeholder(R.drawable.loading_docs)).into(holder.placePic);
            Log.d("xyz","hello Docs "+placeList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item click
                    Intent intent = new Intent(getContext(), ViewDocsActivity.class);
                    intent.putExtra("id", position+""); // put image data in Intent
                    intent.putExtra("docs", placeList.get(position)); // put image data in Intent
                    startActivity(intent); // start Intent

                }
            });
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return placeList.size();
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //Toast.makeText(context, "Docs fragment attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
