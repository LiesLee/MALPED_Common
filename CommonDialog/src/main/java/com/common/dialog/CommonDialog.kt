package com.common.dialog

import android.app.Activity
import android.app.Dialog
import android.content.res.TypedArray
import android.view.View
import android.widget.TextView

/**
 * Created by LiesLee on 2017/10/18.
 * Email: LiesLee@foxmail.com
 */
class CommonDialog {
    companion object {
        /**
         * 提示小弹窗
         *
         * @param context
         * @param callback
         * @return
         */
        fun showTipsDialog(context: Activity, tips: String, btnText: String = "确定", callback: (dialog: Dialog) -> Unit?) : Dialog{
            val dialog = Dialog(context, R.style.custom_dialog)
            dialog.setContentView(R.layout.dialog_tips)
            val tv_tips = dialog.findViewById(R.id.tv_tips) as TextView
            val tv_ok = dialog.findViewById(R.id.tv_ok) as TextView
            tv_tips.text = tips
            tv_ok.text = btnText
            dialog.setCanceledOnTouchOutside(false)
            tv_ok.setOnClickListener {
                callback(dialog)
                dialog.dismiss()
            }
            val dialogWindow = dialog.window
            val m = context.windowManager
            val d = m.defaultDisplay
            val p = dialogWindow!!.attributes
            //p.height = (int) (d.getHeight()*0.6);
            p.width = (d.width * 0.80).toInt()
            dialogWindow.attributes = p
            if (!dialog.isShowing) {
                dialog.show()
            }
            return dialog
        }


        /**
         * 显示两个按钮的dialog
         *
         * @param context
         * @param tips
         * @param leftText
         * @param rightText
         * @param isLeftColor   是否是右边的文字有颜色,别的灰色(通常右手习惯,确认键在右边)
         * @param leftCallback
         * @param rightCallback
         * @return
         */
        fun show2btnDialog(context: Activity, tips: String, leftText: String, rightText: String, isLeftColor: Boolean,
                           leftCallback: (dialog: Dialog) -> Unit?, rightCallback: (dialog: Dialog) -> Unit): Dialog {

            val dialog = Dialog(context, R.style.custom_dialog)
            dialog.setContentView(R.layout.dialog_2_button)
            val tv_tips = dialog.findViewById(R.id.tv_tips) as TextView
            val tv_left = dialog.findViewById(R.id.tv_left) as TextView
            val tv_right = dialog.findViewById(R.id.tv_right) as TextView
            tv_tips.text = tips
            tv_left.text = leftText
            tv_right.text = rightText

            tv_left.setTextColor(if (isLeftColor) context.getResources().getColor(R.color.colorAccent) else context.getResources().getColor(R.color.dialog_grey))
            tv_right.setTextColor(if (isLeftColor) context.getResources().getColor(R.color.dialog_grey) else context.getResources().getColor(R.color.colorAccent))
            dialog.setCanceledOnTouchOutside(false)
            tv_left.setOnClickListener {
               leftCallback(dialog)
                dialog.dismiss()
            }

            tv_right.setOnClickListener {
                rightCallback(dialog)
                dialog.dismiss()
            }

            val dialogWindow = dialog.window
            val m = context.getWindowManager()
            val d = m.getDefaultDisplay()
            val p = dialogWindow!!.attributes
            //p.height = (int) (d.getHeight()*0.6);
            p.width = (d.getWidth() * 0.80).toInt()
            dialogWindow.attributes = p

            if (!dialog.isShowing) {
                dialog.show()
            }
            return dialog
        }


    }
}