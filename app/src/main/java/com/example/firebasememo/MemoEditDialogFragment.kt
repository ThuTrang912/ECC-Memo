package com.example.firebasememo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.firebasememo.databinding.DialogMemoBinding

interface EditMemoListener {
    fun onEditMemo(documentId: String, editedMemo: Memo)   // メモ作成が選択された時の処理
}
class MemoEditDialogFragment : DialogFragment() {

    // メモのイベントをハンドルするリスナー
    private var editMemoListener: EditMemoListener? = null


    // Properties
    private lateinit var binding: DialogMemoBinding
    private var ratingListener: MemoListener? = null

    // Constants
    companion object {
        const val TAG = "EditMemoDialog"
    }

    // Fragment lifecycle methods
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMemoBinding.inflate(inflater, container, false)
        initializeUIElements()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupRatingListener()
    }

    override fun onResume() {
        super.onResume()
        adjustDialogSize()
    }

    // UI setup and event handlers
    private fun initializeUIElements() {
        val selectedMemo = arguments?.getSerializable("selectedMemo") as? Memo
        binding.memoFormText.setText(selectedMemo?.text) // Set existing text
        binding.memoFromPiority.setOnRatingChangeListener { ratingBar, rating ->
            // Convert the 'rating' value to a Double and update the 'priority' property
            val priority = rating.toDouble()

            // Create a new Memo instance with the updated priority
            val updatedMemo = selectedMemo?.copy(priority = priority)

            // Now 'updatedMemo' has the updated priority value
            // You can use 'updatedMemo' in your logic or save it to the database, etc.
        }

        binding.memoFormButton.setOnClickListener { onUpdateClicked() }
        binding.memoFormCancel.setOnClickListener { onCancelClicked() }
    }

    private fun adjustDialogSize() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupRatingListener() {
        if (parentFragment is MemoListener) {
            ratingListener = parentFragment as MemoListener
        } else {
            Log.e(TAG, "Parent fragment does not implement PiorityListener!")
        }
    }

    private fun onUpdateClicked() {
        val selectedMemo = arguments?.getSerializable("selectedMemo") as? Memo
            ?: run {
                Log.e(TAG, "Memo not available for update!")
                dismiss()
                return
            }

        selectedMemo.text = binding.memoFormText.text.toString()
        selectedMemo.priority = binding.memoFromPiority.rating.toDouble()
//        editMemoListener?.onEditMemo(selectedMemo.documentId.toString(), selectedMemo)
        ratingListener?.onUpdateMemo(selectedMemo)
        dismiss()
    }


//    private fun onUpdateClicked() {
//        // Extract the selectedMemo from arguments
//        val selectedMemo = arguments?.getSerializable("selectedMemo") as? Memo
//
//        // Check if selectedMemo is null
//        if (selectedMemo == null) {
//            Log.e(TAG, "Memo not available for update!")
//            dismiss()
//            return
//        }
//
//        // Update the text property of the memo
//        selectedMemo.text = binding.memoFormText.text.toString()
//
//        // Handle the logic for updating the priority if needed
//        val updatedPriority = binding.memoFromPiority.rating.toDouble()
//        selectedMemo.priority = updatedPriority
//
//        // Perform any other update logic if necessary
//
//        // Create a new Memo object with the memo text and priority
//        val editedMemo = Memo(text = selectedMemo.text, priority = selectedMemo.priority)
//        // Notify the listener about the new memo
//        editMemoListener?.onEditMemo(selectedMemo.documentId.toString(),selectedMemo)
//
//        // Dismiss the dialog
//        dismiss()
//    }


    private fun onCancelClicked() {
        dismiss()
    }
}
