package com.smile.studio.recipe.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.esafirm.imagepicker.features.ImagePicker
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.nekoloop.base64image.Base64Image
import com.nekoloop.base64image.RequestEncode
import com.smile.studio.recipe.R
import com.smile.studio.recipe.activity.MainActivity
import com.smile.studio.recipe.adapter.TypeAdapter
import com.smile.studio.recipe.model.ExifUtil
import com.smile.studio.recipe.model.GlobalApp
import com.smile.studio.recipe.model.greendao.Pecipe
import com.smile.studio.recipe.model.greendao.Type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_create.*
import me.echodev.resizer.Resizer
import java.io.File
import java.util.*

class CreateFragment : Fragment(), View.OnClickListener {

    var adapter: TypeAdapter? = null
    var resizedImage: File? = null
    var endcodeBase64: String? = null
    var id: Long? = 0L
    var pecipe: Pecipe? = null

    companion object {
        @JvmStatic
        fun newInstance() = CreateFragment()

        fun newInstance(pecipe: Pecipe): CreateFragment {
            val bundle = Bundle()
            bundle.putParcelable(Pecipe::class.java.simpleName, pecipe)
            val fragment = CreateFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TypeAdapter(activity!!, GlobalApp.getInstance().categories)
        spinner.adapter = adapter
        arguments?.let {
            pecipe = it.getParcelable<Pecipe>(Pecipe::class.java.simpleName)!!
            if (pecipe != null) {
                id = pecipe?.id!!
                endcodeBase64 = pecipe?.image
                val decodeBase64 = Base64.getMimeDecoder().decode(endcodeBase64)
                val bitmap = BitmapFactory.decodeByteArray(decodeBase64, 0, decodeBase64.size)
                Glide.with(activity!!)
                        .load(bitmap)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                .format(DecodeFormat.PREFER_ARGB_8888).error(R.drawable.ic_image_blank)
                                .placeholder(R.drawable.ic_image_blank).dontAnimate())
                        .thumbnail(0.5f)
                        .into(thumb)
                edt_title.setText(pecipe?.title)
                edt_description.setText(pecipe?.description)
                edt_content.setText(pecipe?.content)
                btn_save.visibility = View.GONE
                btn_delete.visibility = View.VISIBLE
                btn_update.visibility = View.VISIBLE
                var selected = 0
                GlobalApp.getInstance().categories.forEachIndexed { index, type ->
                    if (type.pid == pecipe?.type) {
                        selected = index
                    }
                }
                spinner.setSelection(selected, true)
            } else {
                btn_save.visibility = View.VISIBLE
                btn_delete.visibility = View.GONE
                btn_update.visibility = View.GONE
            }
        }
        thumb.setOnClickListener(this)
        btn_save.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
        btn_update.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.thumb -> {
                TedPermission.with(activity)
                        .setPermissionListener(object : PermissionListener {
                            override fun onPermissionGranted() {
                                ImagePicker.create(activity).single()
                                        .toolbarImageTitle(getString(R.string.choose_file_image))
                                        .toolbarDoneButtonText(getString(R.string.done))
                                        .start(100)
                            }

                            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                            }
                        })
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check()
            }
            R.id.btn_save -> {
                saveData(getString(R.string.message_save_data_successful))
            }
            R.id.btn_delete -> {
                try {
                    GlobalApp.getInstance().daoSession?.pecipeDao?.deleteInTx(pecipe).let {
                        val size = GlobalApp.getInstance().daoSession?.pecipeDao?.loadAll()?.size!!
                        if (size == 0) {
                            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container, CreateFragment.newInstance()).commit()
                        } else {
                            (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance()).commit()
                        }
                        Toast.makeText(activity!!, getString(R.string.message_delete_success), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("Tag", "Error: ${e.message}")
                }
            }
            R.id.btn_update -> {
                saveData(getString(R.string.message_update_data_successful))
            }
        }
    }

    private fun saveData(message: String) {
        var flag = false
        if (pecipe == null) {
            pecipe = Pecipe()
        }
        pecipe?.pid = if (id == 0L) System.currentTimeMillis() else id!!
        pecipe?.title = edt_title.text.toString()
        pecipe?.description = edt_description.text.toString()
        pecipe?.content = edt_content.text.toString()
        pecipe?.image = endcodeBase64
        pecipe?.type = (spinner.selectedItem as Type).pid
        if (TextUtils.isEmpty(pecipe?.title)) {
            edt_title.error = getString(R.string.message_error_empty_input_title)
            flag = true
        }
        if (TextUtils.isEmpty(pecipe?.description)) {
            edt_description.error = getString(R.string.message_error_empty_input_description)
            flag = true
        }
        if (TextUtils.isEmpty(pecipe?.content)) {
            edt_content.error = getString(R.string.message_error_empty_input_content)
            flag = true
        }
        if (pecipe?.type == 1) {
            Toast.makeText(activity!!, R.string.message_please_select_category, Toast.LENGTH_SHORT).show()
            flag = true
        }
        if (!flag) {
            GlobalApp.getInstance().daoSession?.pecipeDao?.insertOrReplaceInTx(pecipe).let {
                Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NewApi", "CheckResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val images = ImagePicker.getImages(data)
            if (images != null && !images.isEmpty()) {
                val image = images.get(0).path
                val originalImage = File(image)
                val storagePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + getString(R.string.app_name)
                Resizer(activity).setTargetLength(720).setQuality(70).setOutputFormat("JPEG").setOutputFilename("resized_image")
                        .setOutputDirPath(storagePath).setSourceImage(originalImage)
                        .resizedFileAsFlowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doFinally {
                            Log.e("Tag", "--- image: ${image}")
                            Glide.with(this).load(image)
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                            .format(DecodeFormat.PREFER_RGB_565)
                                            .error(R.drawable.ic_image_blank)
                                            .placeholder(R.drawable.ic_image_blank)
                                            .dontAnimate())
                                    .thumbnail(0.5f)
                                    .into(thumb)
                        }.subscribe({
                            resizedImage = it
                            var bitmap = BitmapFactory.decodeFile(resizedImage?.absolutePath)
                            bitmap = ExifUtil.rotateBitmap(originalImage.absolutePath, bitmap)
                            Base64Image.with(activity)
                                    .encode(bitmap)
                                    .into(object : RequestEncode.Encode {
                                        override fun onSuccess(base64: String?) {
                                            endcodeBase64 = base64
                                            Log.e("Tag", "--- Base64: ${endcodeBase64}")
                                        }

                                        override fun onFailure() {
                                            Log.e("Tag", "--- Error Encode Base64")
                                        }

                                    })
                        }, {
                            Log.e("Tag", "--- Can't reszie image: ${it.message}")
                        })
            }
        } catch (e: Exception) {
            Log.e("Tag", "--- Error: ${e.message}")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
