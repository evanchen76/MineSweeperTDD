package evan.chen.tutorial.tdd.minesweeper

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import evan.chen.tutorial.tdd.minesweeper.MainActivityTest.EspressoTestsMatchers.withDrawable
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.StringContains
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loadCellTest() {
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(81)))
    }

    @Test
    fun clickShowNextNextMines() {
        clickCellAt(4, 8)
        checkNumber(3, 8, 1)
        checkNumber(3, 7, 1)
        checkNumber(3, 6, 1)
        checkNumber(4, 6, 1)
        checkNumber(5, 6, 1)
        checkNumber(5, 7, 1)
        checkNumber(5, 8, 1)
    }

    @Test
    fun longClickFlag() {
        longClickCellAt(3, 5)
        checkIsFlag(3, 5)
    }

    @Test
    fun winGameTest() {
        clickCellAt(0, 1)
        clickCellAt(0, 3)
        clickCellAt(3, 7)
        clickCellAt(4, 7)
        clickCellAt(6, 8)

        onView(withId(R.id.gameStatus)).check(matches(withText("你贏了")))
    }

    @Test
    fun lostGameTest() {
        clickCellAt(3, 5)
        onView(withId(R.id.gameStatus)).check(matches(withText("你輸了")))
    }

    private fun longClickCellAt(x: Int, y: Int) {
        val position = y * 9 + x
        val frameLayout = onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.recyclerView),
                        childAtPosition(
                            IsInstanceOf.instanceOf(LinearLayout::class.java),
                            1
                        )
                    ),
                    position
                ),
                isDisplayed()
            )
        )
        frameLayout.perform().perform(longClick())
    }

    private fun checkIsFlag(x: Int, y: Int) {
        val position = y * 9 + x
        val imageView = onView(
            Matchers.allOf(
                withId(R.id.imageView),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        position
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(withDrawable(R.mipmap.flag)))
    }

    private fun clickCellAt(x: Int, y: Int) {
        val position = y * 9 + x
        val frameLayout = onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.recyclerView),
                        childAtPosition(
                            IsInstanceOf.instanceOf(LinearLayout::class.java),
                            1
                        )
                    ),
                    position
                ),
                ViewMatchers.isDisplayed()
            )
        )
        frameLayout.perform().perform(ViewActions.click())
    }

    private fun checkNumber(x: Int, y: Int, number: Int) {
        val position = y * 9 + x
        val textView = onView(
            Matchers.allOf(
                withId(R.id.textView),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        position
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(matches(ViewMatchers.withText(StringContains.containsString(number.toString()))))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    object EspressoTestsMatchers {
        fun withDrawable(resourceId: Int): Matcher<View> {
            return DrawableMatcher(resourceId)
        }
    }
}

