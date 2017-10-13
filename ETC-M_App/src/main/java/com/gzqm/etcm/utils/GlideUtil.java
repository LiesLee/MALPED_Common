package com.gzqm.etcm.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gzqm.etcm.R;

/**
 * glide图片加载工具类
 * Created by wuchaowen on 2016/4/13.
 */
public class GlideUtil {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Context context, int id, ImageView imageView) {
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.img_default)
                .dontAnimate()
                .error(R.mipmap.img_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImage(Activity context, String url, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Activity context, int id, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Activity context, String url, final ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed()) return;
        }
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImage(Fragment context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    public static void loadImage(Fragment context, int id, ImageView imageView) {
        Glide.with(context)
                .load(id)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }


    public static void loadLocalImage(Fragment context, String url, final ImageView imageView) {
        Glide.with(context)
                .load("file://" + url)
                .placeholder(R.mipmap.img_default)
                .error(R.mipmap.img_default)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }



    public static void loadBitmap(final Activity context, final String url, final LoadBitMapCallBack callBack) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    callBack.successed(resource);
                                }
                            });
                } catch (Exception e) {
                    callBack.faild();
                }
            }
        });

    }

    public static void loadImageViewTarget(final Activity context, final String url, final ImageView imageView, final LoadImageCallBack callBack) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(context).load(url)
                            .placeholder(R.mipmap.img_default)
                            .error(R.mipmap.img_default)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(new ImageViewTarget<GlideDrawable>(imageView) {
                                @Override
                                protected void setResource(GlideDrawable resource) {
                                    if(callBack!=null){
                                        callBack.successed(resource);
                                    }

                                    imageView.setImageDrawable(resource);
                                }
                            });
                } catch (Exception e) {
                    if(callBack!=null) callBack.faild();
                }
            }
        });

    }

    public interface LoadBitMapCallBack {
        void successed(Bitmap bitmap);

        void faild();
    }

    public interface LoadImageCallBack {
        void successed(GlideDrawable resource);

        void faild();
    }


}
