package de.baumann.quitsmoking.about

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import de.baumann.quitsmoking.R
import de.baumann.quitsmoking.helper.ActivityIntro

internal object AboutContent {
    @JvmStatic
    fun createMaterialAboutList(c: Context): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()

        // Add items to card
        appCardBuilder.addItem(MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build())
        try {
            appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(c,
                    ContextCompat.getDrawable(c, R.drawable.earth2),
                    c.getString(R.string.about_version),
                    false))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_changelog)
                .subText(R.string.about_changelog_summary)
                .icon(R.drawable.format_list_bulleted)
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://github.com/NicholasWong13/5009CEM-QuitSmokingApp")))
                .build())
        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_license)
                .subText(R.string.about_license_summary)
                .icon(R.drawable.copyright)
                .setOnClickAction {
                    val s: SpannableString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        SpannableString(Html.fromHtml(c.getString(R.string.about_text), Html.FROM_HTML_MODE_LEGACY))
                    } else {
                        SpannableString(Html.fromHtml(c.getString(R.string.about_text)))
                    }
                    Linkify.addLinks(s, Linkify.WEB_URLS)
                    val d = AlertDialog.Builder(c)
                            .setTitle(R.string.about_title)
                            .setMessage(s)
                            .setPositiveButton(c.getString(R.string.yes)
                            ) { dialog, id -> dialog.cancel() }.show()
                    d.show()
                    (d.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
                }
                .build())
        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_intro)
                .subText(R.string.about_intro_summary)
                .icon(R.drawable.information_outline_dark)
                .setOnClickAction {
                    val intent = Intent(c, ActivityIntro::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    c.startActivity(intent)
                }
                .build())
        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title(R.string.about_title_dev)
        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text(R.string.about_dev)
                .subText(R.string.about_dev_summary)
                .icon(R.drawable.gaukler_faun)
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(c, Uri.parse("https://github.com/NicholasWong13/5009CEM-QuitSmokingApp")))
                .build())

        val convenienceCardBuilder = MaterialAboutCard.Builder()
        convenienceCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text("Material Date Time Picker")
                .subText(R.string.about_license_2)
                .icon(R.drawable.github_circle)
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(c, "Material Date Time Picker", "https://github.com/wdullaer/MaterialDateTimePicker", true, false))
                .build())
        convenienceCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text("Material Design Icons")
                .subText(R.string.about_license_8)
                .icon(R.drawable.github_circle)
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(c, "Material Design Icons", "https://github.com/Templarian/MaterialDesign", true, false))
                .build())
        return MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), convenienceCardBuilder.build())
    }
}