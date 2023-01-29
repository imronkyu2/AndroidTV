package com.example.androidtv.ui.view

import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.example.androidtv.data.model.CouponModel

/** Handles video playback with media controls. */
class PlaybackVideoFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (_, title, description, _, _, videoUrl) =
            activity?.intent?.getSerializableExtra(DetailsActivity.MOVIE) as CouponModel

        val playerAdapter = MediaPlayerAdapter(activity)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }
}