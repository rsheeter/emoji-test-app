package com.google.fonts.colrv1

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.fonts.colrv1.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets.UTF_8


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bungeespice = Typeface.createFromAsset(assets, "BungeeSpice.ttf")

        val ordering = Gson().fromJson(
            InputStreamReader(resources.openRawResource(R.raw.emoji_14_0_ordering), UTF_8),
            Array<EmojiGroup>::class.java
        )

        val layout = findViewById<LinearLayout>(R.id.emoji_list)
        ordering.forEach { group ->
            val heading = textView(R.dimen.heading_size, group.group)
            heading.typeface = bungeespice
            layout.addView(heading)

            val sb = StringBuilder()
            group.emoji.forEach { emoji ->
                emoji.base.forEach(sb::appendCodePoint)
            }
            Log.i("EmojiSequence", sb.toString())
            layout.addView(textView(R.dimen.emoji_size, sb.toString()))
        }
    }

    fun textView(size: Int, text: String): TextView {
        val tv = TextView(applicationContext)
        tv.setTextSize(COMPLEX_UNIT_PX, resources.getDimension(size))
        tv.setText(text)
        return tv
    }

    class EmojiGroup {
        val group: String = "";
        val emoji: Array<Emoji> = emptyArray();
    }

    class Emoji {
        val base: Array<Int> = emptyArray();
    }
}