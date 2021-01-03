package com.example.memokotlin

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.memokotlin.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var db : MemoDatabase
    lateinit var binding : ActivityMainBinding
    var memoList : List<MemoEntity> = listOf<MemoEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MemoDatabase.getInstance(this)!!
    }

    fun insertMemo(memo : MemoEntity) {
        val insertTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }
        insertTask.execute()
    }

    fun getAllMemos() {
        val getTask = object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }
        getTask.execute()
    }

    fun deleteMemo() {

    }

    fun setRecyclerView(memoList: List<MemoEntity>) {

    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.buttonAdd -> {
                val memo = MemoEntity(null, binding.editTextMemo.text.toString())
                insertMemo(memo)
            }
        }
    }
}