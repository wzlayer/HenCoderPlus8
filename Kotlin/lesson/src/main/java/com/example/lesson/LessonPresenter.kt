package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.Utils
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LessonPresenter(val activity: LessonActivity) {

    private var lessons: List<Lesson> = ArrayList()

    private val type = object : TypeToken<List<Lesson>>() {}.type

    companion object {
        const val LESSON_PATH = "lessons"
    }

    fun fetchData() {
        HttpClient.get(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(entity: List<Lesson>) {
                lessons = entity
                activity.runOnUiThread(object : Runnable {
                    override fun run() {
                        activity.showResult(lessons)
                    }
                })
            }

            override fun onFailure(message: String?) {
                activity.runOnUiThread(object : Runnable {
                    override fun run() {
                        Utils.toast(message)
                    }
                })
            }
        })
    }

    fun showPlayback() {
        val playbackLessons = ArrayList<Lesson>()
        playbackLessons.forEach { lesson ->
            if (lesson.state == Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        activity.showResult(playbackLessons)
    }
}