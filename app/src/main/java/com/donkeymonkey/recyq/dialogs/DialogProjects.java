package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.Projects;
import com.donkeymonkey.recyq.model.User;

import java.util.List;

public class DialogProjects extends DialogFragment{

    private User mUser;
    private Projects mProjects;
    private List<Projects> mProjectsList;

    private RecyclerView mRecyclerView;
    private ProjectsAdapter mProjectsAdapter;

    private TextView mProjectsText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_projects, null);

        mProjects = Projects.getInstance();
        mProjectsList = mProjects.getProjectsList();

        mProjectsText = (TextView) view.findViewById(R.id.projects_text);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_projects);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");

        mProjectsText.setTypeface(pt_mono_font);

        TextView mTitle = new TextView(getActivity());
        mTitle.setText(R.string.projects_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        mProjectsText.setTextSize(10);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectsAdapter = new ProjectsAdapter(mProjects);
        mRecyclerView.setAdapter(mProjectsAdapter);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(R.string.ok, null)
                .create();
    }


    // MARK - Recycler view methods

    public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsHolder> {


        public ProjectsAdapter(Projects projects) {
            mProjectsList = projects.getProjectsList();
        }

        @Override
        public ProjectsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.viewholder_projects, parent, false);
            return new ProjectsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ProjectsHolder holder, int position) {
            Projects project = mProjectsList.get(position);
            holder.setProject(project);
            holder.itemView.setOnClickListener(holder);

            holder.mName.setText(project.getProjectName());
            holder.mDescription.setText(project.getProjectText());

            if (position == 0) holder.mName.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreenDark));
            if (position == 1) holder.mName.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorYellow));
            if (position == 2) holder.mName.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
            if (position == 3) holder.mName.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRedDark));

            int id = getActivity().getResources().getIdentifier(project.getProjectImage(), "drawable", getActivity().getPackageName());
            //holder.mImageView.setImageResource(id);

            Glide.with(getActivity())
                    .load(id)
                    .asBitmap()
                    .into(holder.mImageView);

        }

        @Override
        public int getItemCount() {
            return mProjectsList.size();
        }
    }

    public class ProjectsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Projects mProject;


        private TextView mName;
        private ImageView mImageView;
        private TextView mDescription;

        public ProjectsHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.projects_textview_name);
            mImageView = (ImageView) itemView.findViewById(R.id.projects_imageview);
            mDescription = (TextView) itemView.findViewById(R.id.projects_textView_description);

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

            mName.setTypeface(volvobroad_font);
            mDescription.setTypeface(pt_mono_font);

            mName.setTextSize(20);
            mDescription.setTextSize(12);

        }

        public void setProject(Projects project) {
            mProject = project;
        }

        @Override
        public void onClick(View view) {

        }
    }

}
