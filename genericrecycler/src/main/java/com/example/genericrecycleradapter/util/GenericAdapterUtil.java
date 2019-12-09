package com.example.genericrecycleradapter.util;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.lang.reflect.InvocationTargetException;

import static android.view.View.OnApplyWindowInsetsListener;
import static android.view.View.OnAttachStateChangeListener;
import static android.view.View.OnClickListener;
import static android.view.View.OnCreateContextMenuListener;
import static android.view.View.OnDragListener;
import static android.view.View.OnFocusChangeListener;
import static android.view.View.OnGenericMotionListener;
import static android.view.View.OnHoverListener;
import static android.view.View.OnKeyListener;
import static android.view.View.OnLayoutChangeListener;
import static android.view.View.OnLongClickListener;
import static android.view.View.OnSystemUiVisibilityChangeListener;
import static android.view.View.OnTouchListener;


public abstract class GenericAdapterUtil {

    private static final String SPLIT_REGEX = ":";
    public static final CharSequence CHECK_SUFFIX = "contentMethod";

    @Nullable
    public static Object obtainContentFromTag(View view, Object object) {
        Object returnObject = null;

        try {
            returnObject = object
                    .getClass()
                    .getMethod(obtainMethodName(view.getTag()))
                    .invoke(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return returnObject;
    }

    public static void checkTypeAndSetContent(View view, Object content) {
        if (view instanceof TextView) {
            ((TextView) view)
                    .setText(
                            ((String) content)
                    );
        } else if (view instanceof ImageView) {
            view.setTag(null);
            Glide
                    .with(view.getContext())
                    .load(content)
                    .into((ImageView) view);
        }
    }

    public static boolean checkTag(Object tag) {
        return (tag instanceof String) && ((String) tag).contains(CHECK_SUFFIX);
    }

    private static String obtainMethodName(Object tag) {
        return ((String) tag).split(SPLIT_REGEX)[1];
    }

    public static void IdentifyAndSetListener(View view, Object listener) {
        if (listener instanceof OnClickListener) {
            view.setOnClickListener((OnClickListener) listener);
        } else if (listener instanceof OnLongClickListener) {
            view.setOnLongClickListener((OnLongClickListener) listener);
        } else if (listener instanceof OnTouchListener) {
            view.setOnTouchListener((OnTouchListener) listener);
        } else if (listener instanceof OnDragListener) {
            view.setOnDragListener((OnDragListener) listener);
        } else if (listener instanceof OnSystemUiVisibilityChangeListener) {
            view.setOnSystemUiVisibilityChangeListener((OnSystemUiVisibilityChangeListener) listener);
        } else if (listener instanceof OnGenericMotionListener) {
            view.setOnGenericMotionListener((OnGenericMotionListener) listener);
        } else if (listener instanceof OnFocusChangeListener) {
            view.setOnFocusChangeListener((OnFocusChangeListener) listener);
        } else if (listener instanceof OnCreateContextMenuListener) {
            view.setOnCreateContextMenuListener((OnCreateContextMenuListener) listener);
        } else if (listener instanceof OnLayoutChangeListener) {
            view.addOnLayoutChangeListener((OnLayoutChangeListener) listener);
        } else if (listener instanceof OnAttachStateChangeListener) {
            view.addOnAttachStateChangeListener((OnAttachStateChangeListener) listener);
        } else if (listener instanceof OnKeyListener) {
            view.setOnKeyListener((OnKeyListener) listener);
        } else if (listener instanceof OnHoverListener) {
            view.setOnHoverListener((OnHoverListener) listener);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (listener instanceof OnApplyWindowInsetsListener) {
                view.setOnApplyWindowInsetsListener((OnApplyWindowInsetsListener) listener);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (listener instanceof View.OnScrollChangeListener) {
                    view.setOnScrollChangeListener((View.OnScrollChangeListener) listener);
                } else if (listener instanceof View.OnContextClickListener) {
                    view.setOnContextClickListener((View.OnContextClickListener) listener);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (listener instanceof View.OnUnhandledKeyEventListener) {
                        view.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) listener);
                    }
                }
            }
        }
    }
}
