package com.example.piano

import android.R.attr.data
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


class PianoLayout : Fragment() {

    var onSave:((file:Uri) -> Unit)? = null

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

        view.saveFileButton.setOnClickListener {
            //var fileName = view.fileNameTextEdit.text.toString()
            var fileName = "FirstMelody"
            if (score.count() > 0 && fileName.isNotEmpty()){
                fileName = "$fileName.mp3"
                val content:String = score.map {
                    it.toString()
                }.reduce { acc, s -> acc + s + "\n"}
                    saveFile(fileName, content)
                }
            else {
                print("todo, fix later")
            }
        }

        return view
    }

    private fun saveFile(filename:String, content:String){
        val path = this.activity?.getExternalFilesDir(null)
        if (path != null){
            val file = File(path, filename)
            FileOutputStream(file, true).bufferedWriter().use { writer ->
                writer.write(content)
            }

            this.onSave?.invoke(file.toUri())
        }
        else{
            print("Todo")
        }
    }
}