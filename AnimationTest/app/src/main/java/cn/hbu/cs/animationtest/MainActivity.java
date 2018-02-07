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
    private ImageView imageView;
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
                R.id.rotationX, R.id.rotationY, R.id.rotationZ,
                R.id.scaleX,    R.id.scaleY,
                R.id.translationX,  R.id.translationY,
                R.id.set
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
                objectAnimationAlpha();
                break;
            case R.id.rotationZ:                //旋转动画
                objectAnimationRotationZ();
                break;
            case R.id.rotationX:                //旋转动画
                objectAnimationRotationX();
                break;
            case R.id.rotationY:                //旋转动画
                objectAnimationRotationY();
                break;
            case R.id.translationX:                //移动动画
                objectAnimationTranslationX();
                break;
            case R.id.translationY:                //移动动画
                objectAnimationTranslationY();
                break;
            case R.id.scaleX:                //缩放动画
                objectAnimationScaleX();
                break;
            case R.id.scaleY:                //缩放动画
                objectAnimationScaleY();
                break;
            case R.id.set:                //组合动画
                objectAnimationSet();
                break;

        }
    }

    /*** 透明度     */
    private void objectAnimationAlpha() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);
        animator.setDuration(3000);
        animator.start();
        //animator.setRepeatCount(-1);
    }

    /*** 旋转     */
    private void objectAnimationRotationZ() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f, 0f);
        animator.setDuration(3000);
        animator.start();
    }
    private void objectAnimationRotationX() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationX", 0f, 360f, 0f);
        animator.setDuration(3000);
        animator.start();
    }
    private void objectAnimationRotationY() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f, 0f);
        animator.setDuration(3000);
        animator.start();
    }


    /*** 移动     */
    private void objectAnimationTranslationX() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 300f,-300f, 0f);
        animator.setDuration(3000);
        animator.start();
    }
    private void objectAnimationTranslationY() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 300f,-300f, 0f);
        animator.setDuration(3000);
        animator.start();
    }

    /*** 缩放     */
    private void objectAnimationScaleX() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
        animator.setDuration(3000);
        animator.start();
    }
    private void objectAnimationScaleY() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 2f, 1f);
        animator.setDuration(3000);
        animator.start();
    }

    /*** 组合动画
     * AnimatorSet这个类，这个类提供了一个play()方法，调用后将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
     * after(Animator anim) 将现有动画插入到传入的动画之后执行
     * after(long delay) 将现有动画延迟指定毫秒后执行
     * before(Animator anim) 将现有动画插入到传入的动画之前执行
     * with(Animator anim) 将现有动画和传入的动画同时执行
     */
    private void objectAnimationSet() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);//沿x轴放大
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 2f, 1f);//沿y轴放大
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f, -200f,0f);//移动
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);//透明动画
        AnimatorSet set = new AnimatorSet();
        //同时沿X,Y轴放大，且改变透明度，然后移动        //注意：after和before不能同时使用，只能选其一
        set.play(scaleXAnimator).with(scaleYAnimator).with(animator).before(translationXAnimator);
        //都设置5s
        set.setDuration(3000);
        //添加监听事件
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始的时候调用
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //画结束的时候调用
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                //动画被取消的时候调用
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                //动画重复执行的时候调用
            }
        });

        //另一种设置监听的方式，里面的监听方法可以选择性重写
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
        });
        set.start();
    }
}
