package com.example.firebasegroupapp10.activity

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasegroupapp10.Adapter.ColorAdapter
import com.example.firebasegroupapp10.Adapter.SizeAdapter
import com.example.firebasegroupapp10.Adapter.SliderAdapter
import com.example.firebasegroupapp10.Helper.ManagmentCart
import com.example.firebasegroupapp10.Model.ItemsModel
import com.example.firebasegroupapp10.Model.SliderModel
import com.example.firebasegroupapp10.R
import com.example.firebasegroupapp10.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding:ActivityDetailBinding
    private lateinit var item:ItemsModel
    private var numberOder=1
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart=ManagmentCart(this)

        getBundle()
        banners()
        initLists()
    }

    private fun initLists() {
        val sizeList=ArrayList<String>()
        for (size in item.size){
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter=SizeAdapter(sizeList)
        binding.sizeList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val colorList=ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        binding.colorList.adapter=ColorAdapter(colorList)
        binding.colorList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun banners() {
        var sliderItems=ArrayList<SliderModel>()
        for (imageUrl in item.picUrl){
            sliderItems.add(SliderModel(imageUrl))
        }
        binding.slider.adapter=SliderAdapter(sliderItems,binding.slider)
        binding.slider.clipToPadding=true
        binding.slider.clipChildren=true
        binding.slider.offscreenPageLimit=1


        if (sliderItems.size>1){
            binding.dotIndicator.visibility= View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        item=intent.getParcelableExtra("object")!!

            binding.titleTxt.text=item.title
            binding.descriptionTxt.text=item.description
            binding.priceTxt.text="$"+item.price
            binding.ratingTxt.text="${item.rating} Rating"
            binding.addToCartBtn.setOnClickListener {
                item.numberInCart=numberOder
                managmentCart.insertFood(item)
            }
            binding.backBtn.setOnClickListener {finish()}
            binding.cartBtn.setOnClickListener {
                startActivity(Intent(this@DetailActivity, CartActivity::class.java))
            }
    }
}