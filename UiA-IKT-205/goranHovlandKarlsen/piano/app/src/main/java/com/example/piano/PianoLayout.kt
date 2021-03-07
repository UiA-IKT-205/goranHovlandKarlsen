package com.example.piano

import android.R.attr.data
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


class PianoLayout : Fragment() {

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2")
    private val halfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#", "D#", "F#", "G#", "A#")

    private var score:MutableList<Note> = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullTones.forEach{
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            var startPlay:Long = 0

            fullTonePianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                println("$note down")
            }

            fullTonePianoKey.onKeyUp = {
                val endPlay = System.nanoTime()
                val note = Note(it, startPlay, endPlay)
                score.add(note)
                println("Key up: $note")
            }

            ft.add(view.pianoKeys.id, fullTonePianoKey, "note_$it")
        }

        halfTones.forEach{
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)

            var startPlay:Long = 0

            halfTonePianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                println("$note down")
            }

            halfTonePianoKey.onKeyUp = {
                val endPlay = System.nanoTime()
                val note = Note(it, startPlay, endPlay)
                score.add(note)
                println("Key up: $note")
            }

            ft.add(view.pianoKeys.id, halfTonePianoKey, "note_$it")
        }

        ft.commit()


        view.saveScore.setOnClickListener {
            var fileName = view.saveLocation.text.toString()
            // fileName = "Testfile"
            val path = this.activity?.getExternalFilesDir(null)

            if (fileName.isNotEmpty() && score.count() > 0) {
                fileName = "$fileName.musikk"
                val saveFile = File(path, fileName)

                if (saveFile.exists()) {
                    print("error message")
                }
                else {
                FileOutputStream(File(path, fileName), true).bufferedWriter().use {
                    // bufferedWriter lever kun her
                    score.forEach {
                        val writer = OutputStreamWriter(
                            context!!.openFileOutput("$fileName.musikk", Context.MODE_PRIVATE)
                        )
                        writer.write("${it.toString()}\n")
                        writer.close()
                        }
                    }
                }
            }

            else {
                print("File needs a name and notes")
                // TODO @ Make a Toast.makeText instead of print
                // Toast.makeText(this@PianoLayout,"Filen trenger noter og/eller navn", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}