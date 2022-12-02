package com.ubt.unittestsample

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.ext.truth.content.IntentSubject.assertThat

import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ubt.unittestsample.espresso.EspressoTestActivity
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * 测试目标
 * 1、通过viewId查找目标控件，并执行点击操作
 * 2、通过viewId查找目标控件，并修改控件内容
 * 3、通过viewId查目标控件，执行点击操作，跳转到另一个Activity
 *
 * 问题：
 * 1、测试用例失败了没有错误日志，Logs窗口显示：No logcat output for this device.
 * 解决方式：增加espresso-contrib依赖，如下：
 * androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.0") {
        exclude module: "protobuf-lite"
    }
 *
 * 2、java.lang.NoClassDefFoundError: Failed resolution of: Lorg/hamcrest/Matchers;
 * 解决方式：将espresso-contrib库的版本降为3.3.0
 *
 * 3、在测试Intent时报错：java.lang.NullPointerException: Intents not initialized. Did you forget to call init()?
 * 解决方式：在@Before方法中调用：Intents.init() 方法
 *
 * 4、intent断言
 * 解决方式：引入 androidx.test.ext:truth:1.5.0 库
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoTestActivityTest {
    @get:Rule
    val espressoTestActivityRule = ActivityTestRule(EspressoTestActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun release() {
        Intents.release()
    }

    @Test
    fun typeEditText() {
        onView(withId(R.id.et_main)).check(matches(withText("你好 editText")))
    }

    /**
     * 测试按钮点击后Toast正常显示
     *
     * 一直测试不成功，但是没有报错日志。
     * 怀疑：检测Toast是否已显示时太耗时，Toast消失一会儿后测试还在执行中
     */
    @Test
    fun clickButton_ShowToast() {
        val activity = espressoTestActivityRule.activity

        onView(withId(R.id.et_main)).perform(clearText(),  typeText("hello editText"))

        onView(withId(R.id.btn_main)).perform(click())
//        onView(withText(R.string.hello_toast_test))
//            .inRoot(withDecorView(not(`is`(activity.window.decorView))))
//            .check(matches(isDisplayed()))

        //检测Toast是否已显示
        onView(withText(R.string.hello_toast_test)).inRoot(
            withDecorView(not(`is`(activity.window.decorView)))
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

    /**
     * 测试Intent跳转
     */
    @Test
    fun clickButton_JumpToActivity() {
        onView(withId(R.id.btn_main_jump)).perform(click())
        val receivedIntent: Intent = Iterables.getOnlyElement(Intents.getIntents()) as Intent
        assertThat(receivedIntent).extras().containsKey("name")
        assertThat(receivedIntent).extras().string("name").isEqualTo("lzh")
    }
}