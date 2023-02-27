package com.example.testingrecyclerview.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testingrecyclerview.R



class DirectorsFragment : Fragment(){

    private val directors: ArrayList<String> = ArrayList()
    lateinit var directors_text:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            directors.addAll(args.get("args_directors") as List<String>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directors, container, false)
        directors_text = view.findViewById(R.id.directors_text)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDirectors()
    }

    private fun setDirectors(){
        directors_text.text = stringBuilderForDirectors(directors)
    }

    companion object{
        fun stringBuilderForDirectors(directors: ArrayList<String>): String{
            val sb = StringBuilder()
            for(director in directors){
                sb.append(director + "\n")
            }
            return sb.toString()
        }
    }

}



