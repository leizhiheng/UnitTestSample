package com.ubt.unittestsample.roboletric.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.ubt.unittestsample.R
import com.ubt.unittestsample.UnitApp
import com.ubt.unittestsample.robolectric.activities.LoginActivity
import com.ubt.unittestsample.robolectric.activities.SampleActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.Robolectric.setupActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowApplication
import org.robolectric.shadows.ShadowLooper
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class SampleActivityTest {
    lateinit var context: Context
    lateinit var app: UnitApp

    val FAKE_STRING = "Utest"

    @Before
    fun setup(){
        app = ApplicationProvider.getApplicationContext() as UnitApp
        context = ApplicationProvider.getApplicationContext<UnitApp>()
        println("setup application ")
    }


    private lateinit var sampleActivity: SampleActivity
    private lateinit var forwardBtn: Button
    private lateinit var dialogBtn: Button
    private lateinit var toastBtn: Button

    @Before
    fun setUp() {
        var controller: ActivityController<SampleActivity> = Robolectric.buildActivity(SampleActivity::class.java)
        controller.setup()//将activity设置为RESUMED状态
        //创建activity实例
        sampleActivity = controller.get()

        forwardBtn = sampleActivity.findViewById(R.id.btn_forward)
        dialogBtn = sampleActivity.findViewById(R.id.btn_dialog)
        toastBtn = sampleActivity.findViewById(R.id.btn_toast)
    }

    /**
     * 最基本的Activity测试
     */
    @Test
    fun testActivity() {
        assertNotNull(sampleActivity)
        assertEquals(sampleActivity.title, "SimpleActivity")
    }

    /**
     * Activity生命周期测试
     */
    @Test
    fun testLifecycle() {
        val activityController = Robolectric.buildActivity(
            SampleActivity::class.java
        ).create().start()
        val activity: Activity = activityController.get()
        val textview = activity.findViewById<View>(R.id.tv_lifecycle_value) as TextView
        assertEquals("onCreat2", textview.text.toString())
        activityController.resume()
        assertEquals("onResume", textview.text.toString())
        activityController.destroy()
        assertEquals("onDestroy", textview.text.toString())
    }

    /**
     * Activity跳转测试
     */
    @Test
    fun testStartActivity() {
        //按钮点击后跳转到下一个Activity
        forwardBtn.performClick()
        val expectedIntent = Intent(sampleActivity, LoginActivity::class.java)
        val actualIntent = ShadowApplication.getInstance().nextStartedActivity
        assertEquals(expectedIntent, actualIntent)
    }

    /**
     * Toast的测试
     */
    @Test
    fun testToast() {
        //点击按钮，出现吐司
        toastBtn.performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(), "we love UT")
    }

    /**
     * Dialog的测试
     */
    @Test
    fun testDialog() {
        //点击按钮，出现对话框
        dialogBtn.performClick()
        val latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog()
        assertNotNull(latestAlertDialog)
    }

    /**
     * 测试控件状态
     */
    @Test
    fun testViewState() {
        val checkBox = sampleActivity.findViewById<View>(R.id.checkbox) as CheckBox
        val inverseBtn = sampleActivity.findViewById<View>(R.id.btn_inverse) as Button
        assertTrue(inverseBtn.isEnabled)
        checkBox.isChecked = true
        //点击按钮，CheckBox反选
        inverseBtn.performClick()
        assertTrue(!checkBox.isChecked)
        inverseBtn.performClick()
        assertTrue(checkBox.isChecked)
    }

    /**
     * 资源文件访问测试
     */
    @Test
    fun testResources() {
        val application = RuntimeEnvironment.application
        val appName = application.getString(R.string.app_name)
        val activityTitle = application.getString(R.string.title_activity_simple)
        assertEquals("LoveUT", appName)
        assertEquals("SimpleActivity", activityTitle)
    }

    /**
     * 测试广播
     */
//    @Test
//    fun testBoradcast() {
//        val shadowApplication = ShadowApplication.getInstance()
//        val action = "com.geniusmart.loveut.login"
//        val intent = Intent(action)
//        intent.putExtra("EXTRA_USERNAME", "geniusmart")
//
//        //测试是否注册广播接收者
//        assertTrue(shadowApplication.hasReceiverForIntent(intent))
//
//        //以下测试广播接受者的处理逻辑是否正确
//        val myReceiver = MyReceiver()
//        myReceiver.onReceive(RuntimeEnvironment.application, intent)
//        val preferences: SharedPreferences =
//            shadowApplication.getSharedPreferences("account", Context.MODE_PRIVATE)
//        assertEquals("geniusmart", preferences.getString("USERNAME", ""))
//    }

    /**
     * 测试Fragment
     */
//    @Test
//    fun testFragment() {
//        val sampleFragment = SampleFragment()
//        //此api可以主动添加Fragment到Activity中，因此会触发Fragment的onCreateView()
//        SupportFragmentTestUtil.startFragment(sampleFragment)
//        assertNotNull(sampleFragment.view)
//    }

    /**
     * 测试DelayedTask
     */
    @Test
    fun testDelayedTask() {
        val delayedTaskBtn = sampleActivity.findViewById<View>(R.id.btn_delay_task) as Button
        assertFalse(sampleActivity.isTaskFinish)
        delayedTaskBtn.performClick()
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
        assertTrue(sampleActivity.isTaskFinish)
    }
}