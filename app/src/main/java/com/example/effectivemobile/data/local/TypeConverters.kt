package com.example.effectivemobile.data.local

import androidx.room.TypeConverter
import com.example.effectivemobile.domain.model.FeedbackDomain
import com.example.effectivemobile.domain.model.InfoDomain
import com.example.effectivemobile.domain.model.PriceDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductTypeConverters {
    private var gson = Gson()

    class FeedbackTypeConverter {
        @TypeConverter
        fun fromFeedback(feedbackDomain: FeedbackDomain): String =
            gson.toJson(feedbackDomain)

        @TypeConverter
        fun toFeedback(feedback: String): FeedbackDomain =
            gson.fromJson(feedback, FeedbackDomain::class.java)
    }

    class InfoTypeConverter {
        @TypeConverter
        fun fromInfo(infoDomain: List<InfoDomain>): String =
            gson.toJson(infoDomain)

        @TypeConverter
        fun toFeedback(infoDomain: String): List<InfoDomain> {
            val listType = object : TypeToken<List<InfoDomain>>() {}.type
            return gson.fromJson(infoDomain, listType)
        }
    }

    class PriceTypeConverter {
        @TypeConverter
        fun fromPrice(priceDomain: PriceDomain): String =
            gson.toJson(priceDomain)

        @TypeConverter
        fun toPrice(priceDomain: String): PriceDomain {
            return gson.fromJson(priceDomain, PriceDomain::class.java)
        }
    }

    class TagsTypeConverter {
        @TypeConverter
        fun fromTag(tags: List<String>): String =
            gson.toJson(tags)

        @TypeConverter
        fun toPrice(tags: String): List<String> {
            val listType = object : TypeToken<List<String>>() {}.type
            return gson.fromJson(tags, listType)
        }
    }

    class ImagesTypeConverter {
        @TypeConverter
        fun fromImages(images: List<Int>): String =
            gson.toJson(images)

        @TypeConverter
        fun toImages(images: String): List<Int> {
            val listType = object : TypeToken<List<Int>>() {}.type
            return gson.fromJson(images, listType)
        }
    }
}