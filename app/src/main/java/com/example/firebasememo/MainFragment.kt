package com.example.firebasememo

import MemoAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasememo.MemoDialogFragment.Companion.TAG
import com.example.firebasememo.databinding.FragmentMainBinding

import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment(), MemoListener {

    private var userEmail: String? = null

    private lateinit var firestore :FirebaseFirestore

    private var query: Query? = null

    private lateinit var binding: FragmentMainBinding
    private var adapter: MemoAdapter? = null

    private var registration:ListenerRegistration? = null

    companion object {
        fun newInstance(userEmail: String): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putString("userEmail", userEmail)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        userEmail = arguments?.getString("userEmail")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = arguments?.getString("userEmail")

        binding.recyclerMemos.layoutManager = LinearLayoutManager(context)

        initFirestore()

//        (query as CollectionReference).whereEqualTo("documentId",userEmail).get()
//            .addOnSuccessListener {
//                val documents = it.documents
//                adapter = createAdapter(documents)
//                binding.recyclerMemos.adapter = adapter
//            }.addOnFailureListener{
//                Toast.makeText(context,"取得に失敗しました",Toast.LENGTH_SHORT).show()
//            }
        refreshMemosList()

        binding.fabAddMemo.setOnClickListener { showMemoDialog() }
    }

    private fun initFirestore() {
        firestore = Firebase.firestore
        updateFireStoreQuery()
    }

    private fun updateFireStoreQuery(){

        query = firestore.collection("memos")
        registration = (query as CollectionReference)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
                val documents = querySnapshot?.documents
                if(documents != null){
                    adapter = createAdapter(documents)
                    binding.recyclerMemos.adapter = adapter
                }
            }
    }

    private fun addMemo(memo: Memo): Task<Void>? =
        userEmail?.let { email ->
            firestore.collection("memos")
                .document()
                .set(memo.apply { documentId = userEmail })
        }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, "エラー: $message", Snackbar.LENGTH_LONG).show()
    }

    private fun showMemoDialog() {
        val memoDialog = MemoDialogFragment()
        memoDialog.show(childFragmentManager, MemoDialogFragment.TAG)
    }

    private fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

//    override fun onCreateMemo(memo: Memo) {
//        addMemo(memo)?.addOnSuccessListener {
//            Toast.makeText(context,"メモの追加に成功しました",Toast.LENGTH_SHORT).show()
//        }
//            ?.addOnFailureListener{
//                Toast.makeText(context,"メモの追加に失敗しました",Toast.LENGTH_SHORT).show()
//            }
//
//        updateUIAfterMemoCreation()
//    }

    //    override fun onCreateMemo(memo: Memo) {
//        addMemo(memo)?.addOnSuccessListener {
//            Toast.makeText(context,"メモの追加に成功しました",Toast.LENGTH_SHORT).show()
//            // Sau khi thêm memo thành công, làm sạch danh sách và cập nhật giao diện
//            adapter = null // Đặt adapter là null để làm sạch danh sách
//            updateUIAfterMemoCreation()
//        }
//            ?.addOnFailureListener{
//                Toast.makeText(context,"メモの追加に失敗しました",Toast.LENGTH_SHORT).show()
//            }
//    }
    override fun onCreateMemo(memo: Memo) {
        addMemo(memo)?.addOnSuccessListener {
            Toast.makeText(context,"メモの追加に成功しました",Toast.LENGTH_SHORT).show()
            // Sau khi thêm memo thành công, làm sạch danh sách và cập nhật giao diện
            refreshMemosList()
        }
            ?.addOnFailureListener{
                Toast.makeText(context,"メモの追加に失敗しました",Toast.LENGTH_SHORT).show()
            }
    }


    override fun onUpdateMemo(memo: Memo) {
        memo.documentId?.let { id ->
            userEmail?.let { email ->
                firestore.collection("memos").document(id)
                    .update("text", memo.text, "priority", memo.priority)
                    .addOnSuccessListener {
                        Toast.makeText(context, "更新に成功しました", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "更新に失敗しました", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        refreshMemosList()
    }

    override fun onMemoDeleted() {
        refreshMemosList()
    }

//    // Trong phương thức onCreateMemo()
//    private fun updateUIAfterMemoCreation() {
//        if (firestore != null) {
//
//            refreshMemosList()
//            // Thay vì thêm Fragment mới vào back stack, ta sẽ thay thế Fragment hiện tại bằng Fragment mới
//            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//        } else {
//            Log.e(TAG, "Firestore is not initialized.")
//        }
//    }

//    private fun updateUIAfterMemoCreation() {
//        if (firestore != null) {
//            // Thêm hoặc thay thế Fragment hiện tại bằng Fragment mới
//            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//            // Đây là nơi bạn thêm hoặc thay thế Fragment mới
//            fragmentTransaction.replace(R.id.nav_host_fragment, MainFragment())
//            fragmentTransaction.commit()
//        } else {
//            Log.e(TAG, "Firestore is not initialized.")
//        }


    // Trong phương thức onCreateMemo()
    private fun updateUIAfterMemoCreation() {
        if (firestore != null) {

            refreshMemosList()
            // Thay vì thêm Fragment mới vào back stack, ta sẽ thay thế Fragment hiện tại bằng Fragment mới
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        } else {
            Log.e(TAG, "Firestore is not initialized.")
        }
    }


    private fun refreshMemosList() {
        val memosCollection: CollectionReference = firestore.collection("memos")
        memosCollection
            .whereEqualTo("documentId",userEmail)
            .get()
            .addOnSuccessListener { result ->
                val documents = result.documents
                val memoAdapter = createAdapter(documents)
                binding.recyclerMemos.adapter = memoAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                showErrorSnackbar("メモの取得に失敗しました")
            }
    }

    private fun createAdapter(documents: List<DocumentSnapshot>): MemoAdapter {
        return MemoAdapter(
            documents,
            onMemoSelected = { snapshot ->
                val memoData = snapshot.toObject(Memo::class.java)?.copy(documentId = snapshot.id)
                    ?: return@MemoAdapter
                val bundle = Bundle().apply { putSerializable("selectedMemo", memoData) }
                val memoEditDialog = MemoEditDialogFragment().apply { arguments = bundle }
                memoEditDialog.show(childFragmentManager, MemoDialogFragment.TAG)
            },
            memoListener = this
        )
    }

}
