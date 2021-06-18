package com.example.twitchstream.view.videos_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchstream.R
import com.example.twitchstream.util.GAME_NAME
import com.example.twitchstream.view.games_list.StreamListFragment
import com.example.twitchstream.viewmodel.StreamListViewModel
import com.example.twitchstream.viewmodel.VideoListViewModel

private const val TAG = "VideosListFragment"
class VideosListFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(VideoListViewModel::class.java)
    }

    private var adapter: VideosListAdapter? = null
    private var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_videos_list, container, false)
        rootView.let {
            rv = it.findViewById(R.id.videos_list_rv)
        }

        viewModel.topVideos.observe(viewLifecycleOwner, {
            it?.let { list ->
                adapter?.setList(list)
            }
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = VideosListAdapter(emptyList())
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.setHasFixedSize(true)
        rv?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val game = it.getString(GAME_NAME,"")
            viewModel.getTopVideos(game)
        }
    }
}