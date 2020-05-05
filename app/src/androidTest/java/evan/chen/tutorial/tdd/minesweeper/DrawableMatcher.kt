package evan.chen.tutorial.tdd.minesweeper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import org.hamcrest.TypeSafeMatcher

class DrawableMatcher internal constructor(private val expectedResource: Int) :
    TypeSafeMatcher<View>(View::class.java) {

    private var resourceName: String? = null

    override fun matchesSafely(target: View): Boolean {
        if (target !is ImageView) {
            return false
        }

        val resources = target.getContext().resources
        val expectedDrawable = ContextCompat.getDrawable(target.context, expectedResource)
        resourceName = resources.getResourceEntryName(expectedResource)

        if (expectedDrawable == null) {
            //傳進null代表則比對target是不是沒有圖片
            return target.drawable == null
        }

        val actualBitmap = getBitmap(target.drawable)
        val expectedBitmap = getBitmap(expectedDrawable)

        return actualBitmap.sameAs(expectedBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: org.hamcrest.Description?) {
        description?.appendText("resource id: ")
        description?.appendValue(expectedResource)
        if (resourceName != null) {
            description?.appendText(resourceName)
        }
    }

}