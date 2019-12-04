package xyz.kishorenarang.zotes.ui.create

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import xyz.kishorenarang.zotes.R


class CreateFragment : Fragment() , ActionBottomDialogFragment.ItemClickListener {
    override fun onItemClick(item: String?) {
        Toast.makeText(context,"Clicked on Action Sheet",Toast.LENGTH_SHORT).show()
        showBottomSheet(view!!)
    }

    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        createViewModel =
            ViewModelProviders.of(this).get(CreateViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.createTitle)
        createViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.for_open_context_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        showBottomSheet(view!!)
        return super.onOptionsItemSelected(item)
    }

    fun showBottomSheet(view: View) {
        val addPhotoBottomDialogFragment = ActionBottomDialogFragment.newInstance()
        addPhotoBottomDialogFragment.show(
            getFragmentManager(),
            ActionBottomDialogFragment.TAG
        )
    }
}