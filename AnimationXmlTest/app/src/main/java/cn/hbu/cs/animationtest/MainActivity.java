package cn.hbu.cs.animationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.io.InputStream;

/** * 属性动画测试类 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView; /**     * 图片view     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    /*** 初始化View */
    private void initView() {
        imageView = (ImageView) findViewById(R.id.iamgeview);
        setListener(
                R.id.alpha,
                R.id.rotation,
                R.id.translation,
                R.id.scale,
                R.id.set_xml

        );
    }
    /*** 设置点击事件     * @param i 视图id     */
    private void setListener(@IdRes int... i) {
        for (int item : i) {
            View v = findViewById(item);
            if (v == null) return;
            v.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha:                //透明度动画
                objectAnimationTest1();
                break;
            case R.id.rotation:                //旋转动画
                objectAnimationTest2();
                break;
            case R.id.translation:                //移动动画
                objectAnimationTest3();
                break;
            case R.id.scale:                //缩放动画
                objectAnimationTest4();
                break;
            case R.id.set_xml:                //组合动画
                objectAnimationTest6();
                break;

        }
    }
    /*** 透明度     */
    private void objectAnimationTest1() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.alpha);//从xml加载动画
        animator.setTarget(imageView);
        animator.start();
    }

    /*** 旋转     */
    private void objectAnimationTest2() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.rotation); //从xml加载动画
        animator.setTarget(imageView);
        animator.start();
    }

    /*** 移动     */
    private void objectAnimationTest3() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.translation_x);//从xml文件加载
        animator.setTarget(imageView);
        animator.start();
    }

    /*** 缩放     */
    private void objectAnimationTest4() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scale_x);//从xml加载
        animator.setTarget(imageView);
        animator.start();
    }


    /*** 组合动画xml     */
    private void objectAnimationTest6() {
        //向左移动并旋转，然后回到原来的位置
        //接着向右移动并旋转，然后回到原来的位置
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set);//从xml加载
        animator.setTarget(imageView);
        animator.setInterpolator(new LinearInterpolator());//匀速进行
        animator.setStartDelay(1000);
        animator.start();
    }
}
