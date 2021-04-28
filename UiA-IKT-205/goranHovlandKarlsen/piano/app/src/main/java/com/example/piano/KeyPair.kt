package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piano.FullTonePianoKeyFragment.Companion.newInstance
import com.example.piano.KeyPair
import com.example.piano.databinding.FragmentKeyPairBinding
import kotlinx.android.synthetic.main.fragment_key_pair.*
import kotlinx.android.synthetic.main.fragment_key_pair.view.*

class KeyPair : Fragment() {

    private var _binding:FragmentKeyPairBinding? = null
    private val binding get() = _binding!!
    private lateinit var fullNote:String
    private lateinit var halfNote:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fullNote = it.getString("fullNote")?: "???"
            halfNote = it.getString("halfNote")?: "???"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKeyPairBinding.inflate(layoutInflater)
        val view = binding.root
        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        val fullToneKey = FullTonePianoKeyFragment.newInstance(fullNote)

        fullToneKey.onKeyDown = {
            println("$it key down")
        }

        fullToneKey.onKeyUp = {
            println("$it key up")
        }
        ft.add(view.KeyPair.id, fullToneKey, "note_$fullNote")


        val halfToneKey = HalfTonePianoKeyFragment.newInstance(halfNote)

        halfToneKey.onKeyDown = {
            println("$it key down")
        }

        halfToneKey.onKeyUp = {
            println("$it key up")
        }
        ft.add(view.KeyPair.id, halfToneKey, "note_$halfNote")


        ft.commit()
        return inflater.inflate(R.layout.fragment_key_pair, container, false)
    }

    companion object {
        fun newInstance(fullNote: String, halfNote: String) =
            KeyPair().apply {
                arguments = Bundle().apply {
                    putString("fullNote", fullNote)
                    putString("halfNote", halfNote)
                }
            }
    }
}