package com.example.core.http

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

object HttpClient: OkHttpClient() {
    private val gson = Gson()

    private fun <T> convert(json: String?, type: Type): T {
        return gson.fromJson(json, type)
    }

    fun <T> get(path:String, type: Type, entityCallback: EntityCallback<T>) {
        val request: Request = Request.Builder().url("https://api.hencoder.com/lessons").build()
        val call = newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("")
            }

            //@Override
            //public void onResponse(Call call, Response response) {
            //    final int code = response.code();
            //    if (code >= 200 && code < 300) {
            //        final ResponseBody body = response.body();
            //        String json = null;
            //        try {
            //            json = body.string();
            //        } catch (IOException e) {
            //            e.printStackTrace();
            //        }
            //        entityCallback.onSuccess((T) convert(json, type));
            //    } else if (code >= 400 && code < 500) {
            //        entityCallback.onFailure("客户端错误");
            //    } else if (code > 500 && code < 600) {
            //        entityCallback.onFailure("服务器错误");
            //    } else {
            //        entityCallback.onFailure("未知错误");
            //    }
            //}

            // TODO 范围写错了
//            override fun onResponse(call: Call, response: Response) {
//                when(response.code())
//                {
//                    in 200 until 300 -> {
//                        val body = response.body()
//                        val json = body?.string()
//                        entityCallback.onSuccess(convert(json, type))
//                    }
//                    in 400 until 500 -> {
//                        entityCallback.onFailure("客户端错误")
//                    }
//                    in  500 until 600 -> {
//                        entityCallback.onFailure("服务器错误")
//                    }
//                    else -> {
//                        entityCallback.onFailure("未知错误")
//                    }
//                }
//            }

            override fun onResponse(call: Call?, response: Response) {
                when (response.code()) {
                    in 200..299 -> {
                        val body = response.body()
                        var json: String? = null
                        try {
                            json = body!!.string()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        entityCallback.onSuccess(convert<Any>(json, type) as T)
                    }
                    in 400..499 -> {
                        entityCallback.onFailure("客户端错误")
                    }
                    in 501..599 -> {
                        entityCallback.onFailure("服务器错误")
                    }
                    else -> {
                        entityCallback.onFailure("未知错误")
                    }
                }
            }
        })
    }
}