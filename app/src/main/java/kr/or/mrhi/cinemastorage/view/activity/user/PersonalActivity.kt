package kr.or.mrhi.cinemastorage.view.activity.user

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kr.or.mrhi.cinemastorage.R
import kr.or.mrhi.cinemastorage.data.Review
import kr.or.mrhi.cinemastorage.data.User
import kr.or.mrhi.cinemastorage.databinding.ActivityPersonalBinding
import kr.or.mrhi.cinemastorage.view.adapter.UserReviewAdapter

class PersonalActivity : AppCompatActivity() {

    private var _binding: ActivityPersonalBinding? = null
    private val binding get() = _binding!!
    private lateinit var userReviewAdapter: UserReviewAdapter
    private var reviewList: ArrayList<Review>? = arrayListOf()
    private lateinit var user: User

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> Log.d("cinamestorage", "PersonalActivity omOptionsItemSelected()")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.review_search, menu)
        val searchMenu = menu?.findItem(R.id.menu_search)
        val searchView = searchMenu?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                reviewList?.clear()

                /*firebase에서 review데이터 검색해서 불러오기*/


                userReviewAdapter.notifyDataSetChanged()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(applicationContext)
            .load(user.profileImage)
            .into(binding.ivProfile)
        binding.tvNickname.text = user.nickname
        /*해당 유저가 작성한 리뷰 갯수만 가져와야 함
         binding.tvRvCountNo.text = reviewList?.size.toString()*/

        userReviewAdapter = UserReviewAdapter(reviewList as ArrayList<Review>)
        binding.recyclerView.apply {
            adapter = userReviewAdapter
            setHasFixedSize(true)
        }
    }
}
