package com.example.todolistapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistapp.MainActivity
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentAddNoteBinding
import com.example.todolistapp.databinding.FragmentEditNoteBinding
import com.example.todolistapp.model.Note
import com.example.todolistapp.viewmodel.NoteViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {
    private var editNoteBinding: FragmentEditNoteBinding?=null
    private val binding get()=editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote:Note

    private val args: EditNoteFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding=FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel=(activity as MainActivity).noteViewModel
        currentNote=args.note!!
        val year = binding.editNoteDate.year
        val month = binding.editNoteDate.month
        val day = binding.editNoteDate.dayOfMonth

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)
        binding.editNotePriority.setText(currentNote.priority)
        binding.editNoteDate.init(year, month, day, null)


        binding.editNoteFab.setOnClickListener{
            val noteTitle=binding.editNoteTitle.text.toString().trim()
            val noteDesc=binding.editNoteDesc.text.toString().trim()
            val priority=binding.editNotePriority.text.toString().trim()
            val day = binding.editNoteDate.dayOfMonth
            val month = binding.editNoteDate.month + 1 // Month is zero-based, so add 1
            val year = binding.editNoteDate.year

            val date = "$year-$month-$day"

            if (noteTitle.isNotEmpty()){
                val note=Note(currentNote.id,noteTitle,noteDesc,priority,date)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context,"Please enter note title", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context,"Note deleted successfully", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            }else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }


}