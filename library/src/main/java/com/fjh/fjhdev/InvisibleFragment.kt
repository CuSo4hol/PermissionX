package com.fjh.fjhdev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit//typealias关键字可以用于给任意类型指定一个别名

class InvisibleFragment : Fragment() {
    private var callback: PermissionCallback? = null
    fun requestNow(cb: PermissionCallback, vararg permissions: String) {
        callback = cb
        requestPermissions(permissions, 1)
    }

    //处理运行时权限的申请结果
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = ArrayList<String>()//记录所有被用户拒绝的权限
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()//标识是否所有权限均已授权
            //将结果回调
            callback?.let {
                it(allGranted, deniedList)
            }
        }
    }
}