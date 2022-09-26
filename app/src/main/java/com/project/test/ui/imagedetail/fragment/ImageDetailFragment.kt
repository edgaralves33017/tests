package com.project.test.ui.imagedetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.project.test.MainActivity
import com.project.test.R
import com.project.test.data.models.Entry
import com.project.test.databinding.FragmentImagedetailBinding
import com.project.test.util.InstanceRegistry

class ImageDetailFragment : Fragment() {

    private var _binding: FragmentImagedetailBinding? = null
    private val binding get() = _binding!!

    private val args: ImageDetailFragmentArgs by navArgs()
    private lateinit var entry: Entry

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImagedetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entry = args.entry
        InstanceRegistry.get(MainActivity::class).setToolbarTitle(entry.title)
        binding.image.transitionName = args.entry.media.m
        loadImage()
        setupDialog(entry)
    }

    /**
     * Loads the image on glide
     */
    private fun loadImage() {
        Glide.with(this)
            .load(entry.media.m)
            .into(binding.image)
    }

    /**
     * Setup the dialog to be shown when the info button is pressed on the top right corner of the fragment.
     * Display some data of the image (title, author, date of publish and tags)
     */
    private fun setupDialog(entry: Entry) {
        val message: String = resources.getString(
            R.string.dialog_image_details,
            entry.title,
            entry.author,
            entry.published,
            entry.tags
        )

        val builder: AlertDialog.Builder? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNegativeButton(
                    R.string.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
        }

        builder?.setMessage(message)
        val dialog = builder?.create()

        binding.infoBtn.setOnClickListener {
            dialog?.show()
        }
    }
}