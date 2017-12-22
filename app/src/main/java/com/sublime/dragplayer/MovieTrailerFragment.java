package com.sublime.dragplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.sublime.dragplayer.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by goonerdroid
 * on 22/12/17.
 */

public class MovieTrailerFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView movieTitle;
    @BindView(R.id.tv_genre)
    TextView movieGenre;
    @BindView(R.id.tv_year)
    TextView movieYear;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.iv_media_control)
    ImageView ivMediaControl;
    @BindView(R.id.fl_media_control_container)
    FrameLayout mediaControlContainer;


    private Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_trailer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void showFragment(Movie movie){
        movieTitle.setText(movie.getMovieName());
        movieGenre.setText(movie.getMovieGenre());
        movieYear.setText(movie.getMovieYear());
        initializeVideoView(movie.getMovieTrailer());
        mediaControlContainer.setVisibility(View.VISIBLE);
    }

    private void initializeVideoView(String movieTrailer) {
        Uri path = Uri.parse(movieTrailer);
        videoView.setVideoURI(path);
        videoView.start();

        mediaControlContainer.postDelayed(new Runnable() {
            public void run() {
                mediaControlContainer.setVisibility(View.INVISIBLE);
            }
        }, 2800);
    }

    /**
     * Pause the VideoView content.
     */
    public void pauseVideo() {
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    /**
     * Resume the VideoView content.
     */
    public void startVideo() {
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    @OnClick(R.id.iv_media_control)
    public void onMediaClick(){
        if (videoView.isPlaying()) {
            ivMediaControl.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pause_white_36dp));
            videoView.pause();
            return;
        }
        ivMediaControl.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play_arrow_white_36dp));
        videoView.start();
    }

    @OnClick(R.id.rl_video_container)
    public void onVideoContainerClickTest(){
        if (mediaControlContainer.getVisibility() == View.INVISIBLE){
            mediaControlContainer.setVisibility(View.VISIBLE);
            mediaControlContainer.postDelayed(new Runnable() {
                public void run() {
                    mediaControlContainer.setVisibility(View.INVISIBLE);
                }
            }, 1800);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        pauseVideo();
    }
}
